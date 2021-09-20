package com.catchpig.mvvm.provider

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.catchpig.mvvm.lifecycle.ActivityManagerLifeCycleCallbacksImpl
import com.catchpig.mvvm.manager.ContextManager
import com.catchpig.mvvm.manager.KTActivityManager

/**
 * @author catchpig
 * @date 2019/10/21 00:21
 */
class KotlinMvpContentProvider : ContentProvider() {

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {
        val applicationContext = context!!.applicationContext
        ContextManager.context = applicationContext
        val application = applicationContext as Application
        application.registerActivityLifecycleCallbacks(ActivityManagerLifeCycleCallbacksImpl())
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return ""
    }
}