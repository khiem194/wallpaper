package com.kdnt.wallpaper.ui.home.fragment

import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.core.base.BaseFragment
import com.kdnt.wallpaper.databinding.FragmentHomeBinding
import com.kdnt.wallpaper.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun getLayoutResId(): Int = R.layout.fragment_category
    override val mViewModel: HomeViewModel by viewModel()

}