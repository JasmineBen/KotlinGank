package com.conan.kotlingank.presenter

import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.Contants
import com.conan.kotlingank.bean.GankContainer
import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.contract.WelfareContract
import com.conan.kotlingank.data.network.GankApi
import com.conan.kotlingank.data.repository.IRepository
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class WelfarePresenter(repository: IRepository): WelfareContract.IWelfarePresenter {

    private var mRepository: IRepository = repository
    lateinit var mView: WelfareContract.IWelfareView
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mGankContainer: GankContainer = GankContainer()

    override fun attachView(view: WelfareContract.IWelfareView) {
        mView = view
    }

    override fun detachView() {
        AppUtils.cancelRxDisposables(mCompositeDisposable)
    }

    override fun fetchWelfareList(pageIndex: Int, pageSize: Int) {
        val observer = object : Observer<GankList> {
            override fun onSubscribe(@NonNull disposable: Disposable) {
                mCompositeDisposable.add(disposable)
            }

            override fun onNext(@NonNull gankList: GankList) {
                gankList.type = GankApi.GankDataType.DATA_TYPE_WELFARE.dataType
                val added: Int = if (pageIndex == 1) {
                    mGankContainer.onRefreshNewData(gankList)
                } else {
                    mGankContainer.onLoadMoreData(gankList)
                }
                mView.fetchWelfareListSuccess(mGankContainer.getGankList(GankApi.GankDataType.DATA_TYPE_WELFARE), added >= Contants.PAGE_SIZE)
            }

            override fun onError(@NonNull throwable: Throwable) {
                mView.fetchWelfareListFail(null)
            }

            override fun onComplete() {
            }
        }
        mRepository.getRemoteGankList(observer, GankApi.GankDataType.DATA_TYPE_WELFARE.dataType, pageIndex, pageSize)
    }

}