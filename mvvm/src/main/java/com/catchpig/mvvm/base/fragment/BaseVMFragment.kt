package com.catchpig.mvvm.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.catchpig.mvvm.apt.KotlinMvvmCompiler
import com.catchpig.mvvm.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/**
 * @author catchpig
 * @date 2019/4/6 11:25
 */
abstract class BaseVMFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFragment<VB>() {
    protected val viewModel: VM by lazy {
        var type = javaClass.genericSuperclass
        var modelClass: Class<VM> = (type as ParameterizedType).actualTypeArguments[1] as Class<VM>
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(modelClass)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParam()
        lifecycle.addObserver(viewModel)
        initView()
        initFlow()
    }

    protected abstract fun initParam()

    protected abstract fun initView()

    protected abstract fun initFlow()

    fun <T> lifecycleLoadingView(flow: Flow<T>, callback: T.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).onStart {
                loadingView()
            }.onCompletion {
                hideLoadingView()
            }.catch { t: Throwable ->
                KotlinMvvmCompiler.onError(this@BaseVMFragment, t)
            }.collect {
                callback(it)
            }
        }

    }

    fun <T> lifecycleLoadingDialog(flow: Flow<T>, callback: T.() -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            flow.flowOn(Dispatchers.IO).onStart {
                loadingDialog()
            }.onCompletion {
                hideLoadingView()
            }.catch { t: Throwable ->
                KotlinMvvmCompiler.onError(this@BaseVMFragment, t)
            }.collect {
                callback(it)
            }
        }

    }
}
