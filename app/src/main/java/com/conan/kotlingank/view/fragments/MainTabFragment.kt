package com.conan.kotlingank.view.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.Contants
import com.conan.kotlingank.R
import com.conan.kotlingank.bean.GankList
import com.conan.kotlingank.contract.MainTabContract
import com.conan.kotlingank.data.network.GankApi
import com.conan.kotlingank.data.repository.GankRepository
import com.conan.kotlingank.presenter.MainTabPresenter
import com.conan.kotlingank.view.adapters.GankListAdapter
import com.conan.kotlingank.widget.GankRecyclerView

class MainTabFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, GankRecyclerView.OnLoadMoreListener, MainTabContract.IMainTabView {

    private lateinit var mDataType: GankApi.GankDataType
    private lateinit var mPresenter: MainTabContract.IMainTabPresenter
    private lateinit var mAdapter: GankListAdapter

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: GankRecyclerView

    companion object {
        fun newInstance(dataType: GankApi.GankDataType): MainTabFragment {
            val fragment = MainTabFragment()
            val data = Bundle()
            data.putString(Contants.GANK_DATA_TYPE, dataType.dataType)
            fragment.arguments = data
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mDataType = AppUtils.parseGankDataType(arguments!!.getString(Contants.GANK_DATA_TYPE))
        }
        mPresenter = MainTabPresenter(GankRepository())
    }

    override fun getLayoutId(): Int {
        return R.layout.gank_list_layout
    }

    override fun initViews(fragmentView: View?) {
        mSwipeRefreshLayout = fragmentView!!.findViewById(R.id.swipe_layout)
        mRecyclerView = fragmentView!!.findViewById(R.id.recyclerview)
        mPresenter.attachView(this)
        initSwipeView()
        initRecyclerView()
        initData()
    }

    private fun initSwipeView() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(resources.getColor(android.R.color.white))
        mSwipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent),
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark))
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {
        mAdapter = GankListAdapter(activity)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.moreListener = this
    }

    private fun initData() {
        mSwipeRefreshLayout.post({
            mSwipeRefreshLayout.isRefreshing = true
            onRefresh()
        }
        )
    }

    override fun onRefresh() {
        Log.i("zpy","onRefresh")
        mPresenter.fetchGankList(mDataType, 1, Contants.PAGE_SIZE)
    }

    override fun onLoadMore() {
        if (!mSwipeRefreshLayout.isRefreshing) {
            Log.i("zpy","onLoadMore")
            mPresenter.fetchGankList(mDataType, AppUtils.getPageIndex(mAdapter.itemCount,
                    Contants.PAGE_SIZE), Contants.PAGE_SIZE)
        }
    }

    override fun fetchGankListSuccess(gankList: GankList, hasMoreData: Boolean) {
        Log.i("zpy","fetchGankListSuccess")
        mAdapter.setData(gankList)
        mSwipeRefreshLayout.isRefreshing = false
        mRecyclerView.bLoadingMore = false
    }

    override fun fetchGankListFail(reason: String?) {
        Log.i("zpy","fetchGankListFail")
        mSwipeRefreshLayout.isRefreshing = false
        mRecyclerView.bLoadingMore = false
    }
}