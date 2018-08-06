package com.conan.kotlingank.view.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.conan.kotlingank.bean.MainTab
import com.conan.kotlingank.view.activities.BaseActivity
import com.conan.kotlingank.view.fragments.MainTabFragment

class MainTabPagerAdapter(activity: BaseActivity,tabs: ArrayList<MainTab>) : FragmentPagerAdapter(activity.supportFragmentManager) {

    private var mTabs: ArrayList<MainTab> = tabs


    override fun getItem(position: Int): Fragment {
        return MainTabFragment.newInstance(mTabs[position].gankType)
    }

    override fun getCount(): Int {
        return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabs[position].title
    }
}