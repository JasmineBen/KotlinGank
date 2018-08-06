package com.conan.kotlingank.view.activities

import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.conan.kotlingank.AppUtils
import com.conan.kotlingank.R
import com.conan.kotlingank.bean.MainTab
import com.conan.kotlingank.view.adapters.MainTabPagerAdapter
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(),NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun onNecessaryPermissionGranted() {
        initViews()
    }

    private fun initViews() {
        initToolbar()
        initNavigationView()
        initDrawerLayout()
        initViewPager()
        initTabLayout()
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    private fun initNavigationView() {
        navigation_view.setNavigationItemSelectedListener(this)
        var headImage = navigation_view.getHeaderView(0).findViewById<ImageView>(R.id.header)
        Glide.with(this).load(R.mipmap.navigation_header).error(R.mipmap.default_loadfail_pic).placeholder(R.mipmap.default_loadfail_pic).into(headImage)
    }

    private fun initDrawerLayout() {
        val toogle = ActionBarDrawerToggle(this, drawer_left, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_left.addDrawerListener(toogle)
        toogle.syncState()
    }

    private fun initViewPager() {

        var tabs = resources.getStringArray(R.array.study_tab)
        val mainTabs = ArrayList<MainTab>()
        for (index in tabs.indices) {
            mainTabs.add(MainTab(tabs[index], index, AppUtils.parseGankDataType(tabs[index])))
        }
        viewpager.adapter = MainTabPagerAdapter(this, mainTabs)
    }

    private fun initTabLayout() {
        tabs.setupWithViewPager(viewpager)
        tabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_left.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.welfare -> startActivity(Intent(this, WelfareActivity::class.java))
        }
        return true
    }

}