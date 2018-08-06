package com.conan.kotlingank.view.activities

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.StaggeredGridLayoutManager
import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.Contants
import com.conan.kotlingank.R
import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.contract.WelfareContract
import com.conan.kotlingank.data.repository.GankRepository
import com.conan.kotlingank.presenter.WelfarePresenter
import com.conan.kotlingank.view.adapters.WelfareAdapter
import com.conan.kotlingank.widget.GankRecyclerView
import com.conan.kotlingank.widget.WelfareItemDecoration
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.welfare_layout.*

class WelfareActivity: BaseActivity(),WelfareContract.IWelfareView,SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener {

    private lateinit var mAdapter: WelfareAdapter
    private lateinit var mPresenter: WelfarePresenter

    override fun getLayoutId(): Int {
        return R.layout.welfare_layout
    }

    override fun onNecessaryPermissionGranted() {
        initViews()
    }

    private fun initViews(){
        mPresenter = WelfarePresenter(GankRepository())
        mPresenter.attachView(this)
        customToolbar()
        initSwipeView()
        intRecyclerView()
        initData()
    }

    private fun customToolbar(){
        toolbar.setTitle(R.string.welfare)
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back)
        toolbar.setNavigationOnClickListener({
            finish()
        })
    }

    private fun initSwipeView() {
        swipe_layout.setProgressBackgroundColorSchemeResource(android.R.color.white)
        swipe_layout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)
        swipe_layout.setOnRefreshListener(this)
    }

    private fun intRecyclerView() {
        mAdapter = WelfareAdapter(this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        var listView = recyclerview as GankRecyclerView
        listView.layoutManager = layoutManager
        listView.setHasFixedSize(true)
        listView.itemAnimator = DefaultItemAnimator()
        listView.adapter = mAdapter
        listView.moreListener = this
        listView.addItemDecoration(WelfareItemDecoration(16, 2))
    }

    private fun initData() {
        swipe_layout.post({
            onRefresh()
        })
    }

    override fun onRefresh() {
        swipe_layout.isRefreshing = true
        mPresenter.fetchWelfareList(1, Contants.PAGE_SIZE)
    }

    override fun onLoadMore() {
        if (!swipe_layout.isRefreshing) {
            val pageIndex = AppUtils.getPageIndex(mAdapter.itemCount, Contants.PAGE_SIZE)
            mPresenter.fetchWelfareList(pageIndex, Contants.PAGE_SIZE)
        }
    }

    override fun fetchWelfareListSuccess(gankList: GankList, hasMoreData: Boolean) {
        mAdapter.setData(gankList)
        swipe_layout.isRefreshing = false
        var listView = recyclerview as GankRecyclerView
        listView.bLoadingMore = false
    }

    override fun fetchWelfareListFail(reason: String?) {
        swipe_layout.isRefreshing = false
        var listView = recyclerview as GankRecyclerView
        listView.bLoadingMore = false
    }
}