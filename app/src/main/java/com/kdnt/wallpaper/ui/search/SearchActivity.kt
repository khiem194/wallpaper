package com.kdnt.wallpaper.ui.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdnt.wallpaper.R
import com.kdnt.wallpaper.WallpaperUtils
import com.kdnt.wallpaper.core.base.BaseActivity
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.databinding.ActivitySearchBinding
import com.kdnt.wallpaper.ui.home.adapter.PhotosAdapter
import com.kdnt.wallpaper.ui.search.adpater.SearchPhotoAdapter
import com.kdnt.wallpaper.ui.setwallpaper.SetWallpaperActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {
    companion object {
        fun openActivity(context: Context): Intent = Intent(context, SearchActivity::class.java)
    }

    private lateinit var mAdapter: SearchPhotoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var mListPhoto = mutableListOf<PhotoModel>()
    private var currentItems = 0
    private var scrollOutItem = 0
    private var totalItemCount = 0
    private var page = 1
    private var loading = false
    override fun getLayoutResId(): Int = R.layout.activity_search

    override val mViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showKeyBoard()
        gridLayoutManager = GridLayoutManager(this, 3)
        mViewBinding.rcvSearchPhoto.layoutManager = gridLayoutManager
        mAdapter = SearchPhotoAdapter(mListPhoto)
        mViewBinding.rcvSearchPhoto.adapter = mAdapter
        mViewBinding.rcvSearchPhoto.setHasFixedSize(true)
        handleSearchData()
        mAdapter.onClickItemPhotoModel = this::gotoSetupWallpaperActivity
    }

    private fun handleSearchData() {
        mViewBinding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (mViewBinding.edtSearch.text.toString() == ""){
                Toast.makeText(this, "Vui lòng nhập từ khoá!", Toast.LENGTH_SHORT).show()
            }
            else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mViewBinding.edtSearch.clearFocus()
                hideKeyboard()
                val query = mViewBinding.edtSearch.text.toString().trim()
                mViewModel.searchListPhotoByName(query, 1)
                mViewModel.searchResultListPhotoByNameLiveData().observe(this, {
                    mViewBinding.tvResult.text = "Có $it kết quả trả về"
                })
                mViewModel.searchListPhotoByNameLiveData().observe(this@SearchActivity, {
                    mListPhoto.addAll(it)
                    mAdapter.notifyDataSetChanged()
                    Log.d("---aa-", mListPhoto.size.toString())
                })
                mListPhoto.clear()
                initScroll(query)
            }
            true
        }
        mViewBinding.ivClear.setOnClickListener {
            mViewBinding.edtSearch.setText("")
            showKeyBoard()
        }

        mViewBinding.ivBack.setOnClickListener {
            onBackPressed()
            hideKeyboard()
        }
    }

    private fun initScroll(name: String) {
        mViewBinding.rcvSearchPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun showKeyBoard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1)
        mViewBinding.edtSearch.requestFocus()
    }

    private fun hideKeyboard() {
        val input = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(mViewBinding.edtSearch.windowToken, 0)
    }
}