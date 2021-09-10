package com.kdnt.wallpaper.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivityHomeBinding
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    private lateinit var mAdapter: PhotosAdapter

    companion object {
        fun openActivity(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun getLayoutResId(): Int = R.layout.activity_home

    override val mViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.rcvPhotos.layoutManager = GridLayoutManager(this, 3)
        mAdapter = PhotosAdapter()
        mViewBinding.rcvPhotos.adapter = mAdapter
        mViewModel.getListPhotoLiveData().observe(this, {
            mAdapter.setData(it)
            Log.d("---a-", it.toString())
        })
        mAdapter.onClickItemPhotoModel = this::gotoSetupWallpaperActivity

        mViewBinding.btnNature.setOnClickListener {
            searchListPhoto(getString(R.string.nature))
        }

        mViewBinding.btnTrending.setOnClickListener {
            searchListPhoto(getString(R.string.trending))
        }
    }

    private fun searchListPhoto(name: String) {
        mViewModel.searchListPhoto(name)
        mViewModel.getListPhotoLiveData().observe(this,{
            mAdapter.setData(it)
        })
    }

    private fun gotoSetupWallpaperActivity(photoModel: PhotoModel) {
        val intent = Intent(this, SetWallpaperActivity::class.java)
        intent.putExtra(WallpaperUtils.KEY_PHOTOS, photoModel)
        startActivity(intent)
    }
}