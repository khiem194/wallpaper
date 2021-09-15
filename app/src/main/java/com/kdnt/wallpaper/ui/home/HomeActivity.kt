package com.kdnt.wallpaper.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var gridLayoutManager: GridLayoutManager
    private var mListPhoto = mutableListOf<PhotoModel>()
    private var currentItems = 0
    private var scrollOutItem = 0
    private var totalItemCount = 0
    private var loading = false

    companion object {
        fun openActivity(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun getLayoutResId(): Int = R.layout.activity_home

    override val mViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridLayoutManager = GridLayoutManager(this, 3)
        mViewBinding.rcvPhotos.layoutManager = gridLayoutManager
        mAdapter = PhotosAdapter()
        mViewBinding.rcvPhotos.adapter = mAdapter
        mViewModel.getListPhotoLiveData().observe(this, {
            mAdapter.setData(it)
            Log.d("---a-", it.toString())
        })
        mAdapter.onClickItemPhotoModel = this::gotoSetupWallpaperActivity
        initScroll()
        mViewBinding.btnNature.setOnClickListener {
            searchListPhoto(getString(R.string.nature))
        }

        mViewBinding.btnTrending.setOnClickListener {
            searchListPhoto(getString(R.string.trending))
        }
    }

    private fun initScroll() {
        mViewBinding.rcvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                loading = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = gridLayoutManager.childCount
                totalItemCount = gridLayoutManager.itemCount
                scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition()
                if (loading && (currentItems + scrollOutItem == totalItemCount)) {
                    loading = false
                    mViewModel.searchListPhoto("sport")
                    mViewModel.getListPhotoLiveData().observe(this@HomeActivity, {
                        mAdapter.updateData(it)
                    })
                }
            }
        })
    }


    private fun searchListPhoto(name: String) {
        mViewModel.searchListPhoto(name)
        mViewModel.getListPhotoLiveData().observe(this, {
            mAdapter.setData(it)
        })
    }

    private fun gotoSetupWallpaperActivity(photoModel: PhotoModel) {
        val intent = Intent(this, SetWallpaperActivity::class.java)
        intent.putExtra(WallpaperUtils.KEY_PHOTOS, photoModel)
        startActivity(intent)
    }
}