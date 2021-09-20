package com.kdnt.wallpaper.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseFragment
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.FragmentTrendingBinding
import com.kdnt.wallpaper.ui.home.HomeViewModel
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : BaseFragment<HomeViewModel, FragmentTrendingBinding>() {
    private lateinit var mAdapter: PhotosAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var currentItems = 0
    private var scrollOutItem = 0
    private var totalItemCount = 0
    private var page = 1
    private var loading = false

    override fun getLayoutResId(): Int = R.layout.fragment_trending
    override val mViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(context, 3)
        mViewBinding.rcvPhotos.layoutManager = gridLayoutManager
        mAdapter = PhotosAdapter()
        mViewBinding.rcvPhotos.adapter = mAdapter
        mViewBinding.rcvPhotos.setHasFixedSize(true)
        mViewModel.getListPhotoTrendingLiveData().observe(viewLifecycleOwner, {
            mAdapter.setData(it)
            Log.d("---a-", it.toString())
        })
        mAdapter.onClickItemPhotoModel = this::gotoSetupWallpaperActivity
        initScroll()
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
                    page += 1
                    mViewModel.loadMoreDataTrending("trending", page)
                    Log.d("---b", (page).toString())
                }
            }
        })
    }

    private fun gotoSetupWallpaperActivity(photoModel: PhotoModel) {
        val intent = Intent(context, SetWallpaperActivity::class.java)
        intent.putExtra(WallpaperUtils.KEY_PHOTOS, photoModel)
        startActivity(intent)
    }

}