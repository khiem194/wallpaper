package com.kdnt.wallpaper.ui.splash

import android.os.Bundle
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.ui.home.HomeActivity
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.databinding.ActivitySplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_splash
    override val mViewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gotoHome()

        try {
            val versionName: String = packageManager.getPackageInfo(packageName, 0).versionName
            "v$versionName".also { mViewBinding.appVersionName.text = it }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun gotoHome() {
        startActivity(HomeActivity.openActivity(this))
    }

    override fun onBackPressed() {}


}