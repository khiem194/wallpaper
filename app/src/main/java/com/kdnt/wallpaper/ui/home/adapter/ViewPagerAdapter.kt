package com.kdnt.wallpaper.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kdnt.wallpaper.ui.home.fragment.CategoryFragment
import com.kdnt.wallpaper.ui.home.fragment.HomeFragment
import com.kdnt.wallpaper.ui.home.fragment.PopularFragment
import com.kdnt.wallpaper.ui.home.fragment.TrendingFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: ArrayList<Fragment> = arrayListOf(
        HomeFragment(),
        PopularFragment(),
        TrendingFragment(),
        CategoryFragment()
    )
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}