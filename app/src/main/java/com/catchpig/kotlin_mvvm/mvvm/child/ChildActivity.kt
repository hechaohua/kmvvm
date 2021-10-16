package com.catchpig.kotlin_mvvm.mvvm.child

import android.view.View
import com.catchpig.annotation.OnClickFirstDrawable
import com.catchpig.annotation.OnClickFirstText
import com.catchpig.annotation.StatusBar
import com.catchpig.annotation.Title
import com.catchpig.kotlin_mvvm.R
import com.catchpig.kotlin_mvvm.databinding.ActivityChildBinding
import com.catchpig.mvvm.base.activity.BaseVMActivity
import com.catchpig.mvvm.manager.SnackbarManager

@Title
@StatusBar
class ChildActivity : BaseVMActivity<ActivityChildBinding, ChildViewModel>() {
    @OnClickFirstDrawable(R.drawable.more)
    fun clickFirstDrawable(v: View) {
        SnackbarManager.show(bodyBinding.root, "第一个图标按钮点击生效")
        updateTitle("nihao")
    }

    @OnClickFirstText(R.string.more)
    fun clickFirstText() {
        SnackbarManager.show(bodyBinding.root, "第一个文字按钮点击生效")
        updateTitle("12354")
    }

    override fun initParam() {

    }

    override fun initView() {

    }

    override fun initObserver() {

    }

    /**
     * dialog形式的loading
     */
    fun loadingDialog(v: View) {
        viewModel.loadingDialog()
    }

    /**
     * 标题栏以下的loading
     */
    fun loadingView(v: View) {
        viewModel.loadingView()
    }
}
