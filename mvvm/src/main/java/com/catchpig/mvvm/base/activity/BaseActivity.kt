package com.catchpig.mvvm.base.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.catchpig.mvvm.R
import com.catchpig.mvvm.apt.KotlinMvvmCompiler
import com.catchpig.mvvm.base.view.BaseView
import com.catchpig.mvvm.controller.LoadingViewController
import com.catchpig.mvvm.controller.StatusBarController
import com.catchpig.mvvm.databinding.ViewRootBinding
import com.catchpig.utils.ext.showSnackBar
import java.lang.reflect.ParameterizedType

/**
 * --------------状态栏----------------
 * 请使用注解[com.catchpig.annotation.StatusBar]
 * 想让注解不可用,请设置[com.catchpig.annotation.StatusBar.enabled]为true
 * --------------状态栏----------------
 *
 * --------------标题栏----------------
 * 请使用注解[com.catchpig.annotation.Title]
 * --------------标题栏----------------
 *
 * --------------标题栏右边按钮点击事件---------------
 * 第一个文字按钮点击事件,请方法上实现以下注解
 * @[com.catchpig.annotation.OnClickFirstText]
 *
 * 第一个图标按钮的点击事件,请方法上实现以下注解
 * @[com.catchpig.annotation.OnClickFirstDrawable]
 *
 * 第二个文字按钮的点击事件,请方法上实现以下注解
 * @[com.catchpig.annotation.OnClickSecondText]
 *
 * 第二个图标按钮的点击事件,请方法上实现以下注解
 * @[com.catchpig.annotation.OnClickSecondDrawable]
 * --------------标题栏右边按钮点击事件---------------
 *
 * @author catchpig
 * @date 2019/4/4 00:09
 */
open class BaseActivity<VB : ViewBinding> : AppCompatActivity(), BaseView {
    protected val bodyBinding: VB by lazy {
        var type = javaClass.genericSuperclass
        var vbClass: Class<VB> = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(this, layoutInflater) as VB
    }
    private val rootBinding: ViewRootBinding by lazy {
        ViewRootBinding.inflate(layoutInflater)
    }

    private lateinit var loadingViewController: LoadingViewController

    private lateinit var statusBarController: StatusBarController

    private var failedBinding: ViewBinding? = null

    fun initLoadingViewController(loadingViewController: LoadingViewController) {
        this.loadingViewController = loadingViewController
    }

    fun initStatusBarController(statusBarController: StatusBarController) {
        this.statusBarController = statusBarController
    }

    fun resetStatusBar() {
        statusBarController.checkStatusBar()
    }

    fun getRootBanding(): ViewRootBinding {
        return rootBinding
    }

    inline fun <reified FVB : ViewBinding> failedBinding(block: FVB.() -> Unit) {
        getFailedBinding()?.let {
            (it as FVB).run(block)
        }
    }

    fun getFailedBinding(): ViewBinding? {
        if (failedBinding == null) {
            failedBinding = KotlinMvvmCompiler.globalConfig().getFailedBinding(layoutInflater, this)
        }
        return failedBinding
    }

    fun onFailedReload(block: View.() -> Unit) {
        val failedBinding = getFailedBinding()
        failedBinding?.let { viewBinding ->
            val failedRootView = viewBinding.root
            val clickView = failedRootView.findViewById<View>(
                KotlinMvvmCompiler.globalConfig().onFailedReloadClickId()
            )
            clickView.setOnClickListener {
                it.run(block)
            }
        }
    }

    override fun scope(): LifecycleCoroutineScope {
        return lifecycleScope
    }

    override fun showFailedView() {
        getFailedBinding()?.let {
            rootBinding {
                layoutBody.addView(it.root)
            }
        }
    }

    override fun removeFailedView() {
        failedBinding?.let {
            rootBinding.layoutBody.removeView(it.root)
        }
    }

    fun rootBinding(block: ViewRootBinding.() -> Unit) {
        rootBinding.run(block)
    }

    fun bodyBinding(block: VB.() -> Unit) {
        bodyBinding.run(block)
    }

    @CallSuper
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.setContentView(rootBinding.root)
        super.onCreate(savedInstanceState)
        setContentView(bodyBinding.root)
        KotlinMvvmCompiler.inject(this)
    }

    override fun setContentView(view: View?) {
        rootBinding.layoutBody.let {
            it.addView(
                view, 0, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    /**
     * 改变title文字
     */
    fun updateTitle(title: String) {
        var titleText = rootBinding.root.findViewById<TextView>(R.id.title_text)
        titleText.text = title
    }

    /**
     * 改变title文字
     */
    fun updateTitle(@StringRes title: Int) {
        var titleText = rootBinding.root.findViewById<TextView>(R.id.title_text)
        titleText.setText(title)
    }

    fun snackBar(text: CharSequence, gravity: Int = Gravity.BOTTOM) {
        bodyBinding.root.showSnackBar(text, R.drawable.snackbar_bg, gravity)
    }

    fun snackBar(@StringRes textRes: Int, gravity: Int = Gravity.BOTTOM) {
        bodyBinding.root.showSnackBar(textRes, R.drawable.snackbar_bg, gravity)
    }

    override fun loadingDialog() {
        loadingViewController?.let {
            it.loadingDialog()
        }
    }

    override fun loadingView() {
        loadingViewController?.let {
            it.loadingView()
        }
    }

    override fun hideLoading() {
        loadingViewController?.let {
            it.hideLoading()
        }
    }

    fun closeActivity() {
        finish()
    }
}
