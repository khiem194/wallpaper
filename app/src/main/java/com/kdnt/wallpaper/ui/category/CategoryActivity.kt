package com.kdnt.wallpaper.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.CategoryModel
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivityCategoryBinding
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryActivity : BaseActivity<CategoryViewModel, ActivityCategoryBinding >() {
    private lateinit var mAdapter: PhotosAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var currentItems = 0
    private var scrollOutItem = 0
    private var totalItemCount = 0
    private var page = 1
    private var loading = false
    override fun getLayoutResId(): Int = R.layout.activity_category

    override val mViewModel: CategoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val categoryModel = intent.extras?.get(WallpaperUtils.KEY_CATEGORY) as CategoryModel
        title = categoryModel.nameCategory
        gridLayoutManager = GridLayoutManager(this, 3)
        mViewBinding.rcvPhotosByName.layoutManager = gridLayoutManager
        mAdapter = PhotosAdapter()
        mViewBinding.rcvPhotosByName.adapter = mAdapter
        mViewBinding.rcvPhotosByName.setHasFixedSize(true)
        mViewModel.getListPhotoByName(categoryModel.nameCategory, 1)
        mViewModel.getListPhotoByNameLiveData().observe(this, {
            mAdapter.setData(it)
            Log.d("---a-", it.toString())
        })
        mAdapter.onClickItemPhotoModel = this::gotoSetupWallpaperActivity
        initScroll(categoryModel.nameCategory)
    }

    private fun initScroll(name : String) {
        mViewBinding.rcvPhotosByName.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    mViewModel.loadMoreData(name, page)
                    Log.d("---b", (page).toString())
                }
            }
        })
    }

    private fun gotoSetupWallpaperActivity(photoModel: PhotoModel) {
        val intent = Intent(this, SetWallpaperActivity::class.java)
        intent.putExtra(WallpaperUtils.KEY_PHOTOS, photoModel)
        startActivity(intent)
    }

}