package com.catchpig.kotlin_mvvm.mvp.apk.viewmodel

import com.catchpig.mvvm.base.viewmodel.BaseViewModel

/**
 *
 * @author catchpig
 * @date 2020/11/20 15:51
 */
class InstallApkViewModel : BaseViewModel() {
    override fun onError(t: Throwable) {

    }

    fun download() {
//        val downloadUrl = "https://wanandroid.com/blogimgs/2d120094-e1ee-47fb-a155-6eb4ca49d01f.apk"
//        model.download(downloadUrl, object : DownloadCallback {
//            override fun onStart() {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//            override fun onSuccess(path: String) {
//                view.activity().installApk(path)
//            }
//
//            override fun onProgress(readLength: Long, countLength: Long) {
//                view.setDownloadProgress((readLength * 100 / countLength).toInt())
//            }
//
//            override fun onError(t: Throwable) {
//                println(t.message)
//            }
//        })
//        val imageUrl = "https://tvax4.sinaimg.cn/large/005BYqpgly1g1usrl6vysj31c00u0k6p.jpg"
//        model.download(imageUrl, object : DownloadCallback {
//            override fun onStart() {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//            override fun onSuccess(path: String) {
//
//            }
//
//            override fun onProgress(readLength: Long, countLength: Long) {
//                view.setDownloadProgress1((readLength * 100 / countLength).toInt())
//            }
//
//            override fun onError(t: Throwable) {
//                println(t.message)
//            }
//        })

//        val urls = arrayListOf<String>()
//        for (i in 0..1){
//            urls.add(downloadUrl)
//        }
//        model.multiDownload(urls,object :MultiDownloadCallback{
//
//            override fun onSuccess(paths: MutableList<String>) {
//                paths.toString().logi()
//            }
//
//        })
    }
}