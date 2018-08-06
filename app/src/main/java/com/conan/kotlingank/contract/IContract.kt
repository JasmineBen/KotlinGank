package com.conan.kotlingank.contract

interface IContract {

    interface  IView{

    }

    interface IPresenter<V : IView>{
        fun attachView(view: V)
        fun detachView()
    }
}