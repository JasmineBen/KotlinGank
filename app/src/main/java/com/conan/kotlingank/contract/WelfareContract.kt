package com.conan.kotlingank.contract

import com.conan.kotlingank.bean.GankList

interface WelfareContract {

    interface IWelfareView : IContract.IView {
        fun fetchWelfareListSuccess(gankList: GankList, hasMoreData: Boolean)
        fun fetchWelfareListFail(reason: String?)
    }

    interface IWelfarePresenter : IContract.IPresenter<IWelfareView> {
        fun fetchWelfareList(pageIndex: Int, pageSize: Int)
    }
}