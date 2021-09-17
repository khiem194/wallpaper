package com.kdnt.wallpaper.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivityHomeBinding
import com.kdnt.wallpaper.ui.download.DownloadActivity
import com.kdnt.wallpaper.ui.favourites.FavouritesActivity
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    private lateinit var mAdapter: PhotosAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var currentItems = 0
    private var scrollOutItem = 0
    private var totalItemCount = 0
    private var page = 1
    private var loading = false
    private lateinit var toggle : ActionBarDrawerToggle

    companion object {
        fun openActivity(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun getLayoutResId(): Int = R.layout.activity_home

    override val mViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDrawerLayout()
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
    }

    private fun setupDrawerLayout() {
        toggle = ActionBarDrawerToggle(this, mViewBinding.drawerLayout, R.string.Open, R.string.Close)
        mViewBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mViewBinding.navDrawer.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.favourites -> startActivity(FavouritesActivity.openActivity(this))
                R.id.download -> startActivity(DownloadActivity.openActivity(this))
                R.id.theme -> Toast.makeText(this, R.string.theme, Toast.LENGTH_SHORT).show()
                R.id.setting -> Toast.makeText(this, R.string.setting, Toast.LENGTH_SHORT).show()
                R.id.rate_us -> Toast.makeText(this, R.string.rate_us, Toast.LENGTH_SHORT).show()
                R.id.share -> Toast.makeText(this, R.string.share, Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
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
                    mViewModel.loadMoreData(page)
                    Log.d("---b", (page).toString())
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