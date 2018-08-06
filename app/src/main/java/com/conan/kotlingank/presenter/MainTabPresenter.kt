package com.conan.kotlingank.presenter

import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.Contants
import com.conan.kotlingank.bean.GankContainer
import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.contract.MainTabContract
import com.conan.kotlingank.data.network.GankApi
import com.conan.kotlingank.data.repository.IRepository
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainTabPresenter(repository: IRepository) : MainTabContract.IMainTabPresenter {

    var mRepository: IRepository = repository
    lateinit var mView: MainTabContract.IMainTabView
    var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    var mGankContainer: GankContainer = GankContainer()

    override fun fetchGankList(type: GankApi.GankDataType, pageIndex: Int, pageSize: Int) {
        val observer = object : Observer<GankList> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                mCompositeDisposable.addAll(d)
            }

            override fun onNext(gankList: GankList) {
                gankList.type = type.dataType
                val added: Int = if (pageIndex == 1) {
                    mGankContainer.onRefreshNewData(gankList)
                } else {
                    mGankContainer.onLoadMoreData(gankList)
                }
                mView.fetchGankListSuccess(mGankContainer.getGankList(type), added >= Contants.PAGE_SIZE)
            }

            override fun onError(e: Throwable) {
                mView.fetchGankListFail(null)
            }

        }

        mRepository.getRemoteGankList(observer, type.dataType, pageIndex, pageSize)
    }

    override fun attachView(view: MainTabContract.IMainTabView) {
        mView = view
    }

    override fun detachView() {
        AppUtils.cancelRxDisposables(mCompositeDisposable)
    }
}