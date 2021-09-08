package com.kdnt.wallpaper.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {

    companion object {
        fun openActivity(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun getLayoutResId(): Int = R.layout.activity_home

    override val mViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}