package com.conan.kotlingank.view.activities

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        requestNecessaryPermission()
    }

    abstract fun getLayoutId(): Int


    private fun requestNecessaryPermission() {
        var rxPermissions = RxPermissions(this)
        var observer = object : Observer<Boolean> {
            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
                finish()
            }

            override fun onNext(t: Boolean) {
                if (!t) {
                    finish()
                } else {
                    onNecessaryPermissionGranted()
                }
            }

            override fun onSubscribe(d: Disposable) {
            }
        }
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer)
    }

    abstract fun onNecessaryPermissionGranted()


}