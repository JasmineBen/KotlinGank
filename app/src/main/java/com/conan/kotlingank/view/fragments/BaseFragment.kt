package com.conan.kotlingank.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment: Fragment(){

    private var mFragmentView: View? = null

    abstract fun getLayoutId(): Int

    abstract fun initViews(fragmentView: View?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(mFragmentView == null){
            mFragmentView = inflater.inflate(getLayoutId(),container,false)
            initViews(mFragmentView)
        }
        return mFragmentView
    }

}