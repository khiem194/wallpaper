package com.kdnt.wallpaper.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseFragment
import com.kdnt.wallpaper.data.model.CategoryModel
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.FragmentCategoryBinding
import com.kdnt.wallpaper.databinding.FragmentHomeBinding
import com.kdnt.wallpaper.ui.category.CategoryActivity
import com.kdnt.wallpaper.ui.home.HomeViewModel
import com.kdnt.wallpaper.ui.home.adapter.CategoryAdapter
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : BaseFragment<HomeViewModel, FragmentCategoryBinding>() {
    private lateinit var mAdapter: CategoryAdapter
    private var mListCategory = mutableListOf<CategoryModel>()
    override fun getLayoutResId(): Int = R.layout.fragment_category
    override val mViewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNameCategory()
        mAdapter = CategoryAdapter(mListCategory)
        mViewBinding.rcvCategory.layoutManager = LinearLayoutManager(activity)
        mViewBinding.rcvCategory.adapter = mAdapter
        mAdapter.onClickItemCategoryModel = this::gotoCategoryActivity


    }

    private fun getNameCategory() {
        mListCategory.add(CategoryModel("Animal"))
        mListCategory.add(CategoryModel("Sport"))
        mListCategory.add(CategoryModel("Space"))
        mListCategory.add(CategoryModel("Nature"))
        mListCategory.add(CategoryModel("Flag"))
        mListCategory.add(CategoryModel("Quotes"))
    }

    private fun gotoCategoryActivity(categoryModel: CategoryModel) {
        val intent = Intent(context, CategoryActivity::class.java)
        intent.putExtra(WallpaperUtils.KEY_CATEGORY, categoryModel)
        startActivity(intent)
    }


}