package com.kdnt.wallpaper.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kdnt.wallpaper.core.base.BaseViewModel
import com.kdnt.wallpaper.data.model.CategoryModel
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.data.repository.WallPaperRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val wallPaperRepository: WallPaperRepository) : BaseViewModel() {
    init {
        getListPhoto(1, 40)
        getListPhotoPopular("Popular", 1)
        getListPhotoTrending("trending", 1)
    }

    private val mListPhotoData: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoLiveData() = mListPhotoData

    private val mListPhotoPopular: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoPopularLiveData() = mListPhotoPopular

    private val mListPhotoTrending: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoTrendingLiveData() = mListPhotoTrending

    private val mListCategory: MutableLiveData<MutableList<CategoryModel>> = MutableLiveData()
    fun getListCategoryLiveData() = mListCategory

    private fun getListCategory(){

    }

    private fun getListPhoto(page: Int, per_page: Int) {
        GlobalScope.launch {
            wallPaperRepository.getListPhoto(page, per_page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoData.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }

    fun loadMoreData(page: Int, per_page: Int) {
        GlobalScope.launch {
            wallPaperRepository.getListPhoto(page, per_page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoData.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }


    private fun getListPhotoPopular(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val loadMorePhoto = it.photos
                    mListPhotoPopular.postValue(loadMorePhoto)
                    Log.d("----", it.photos.toString())
                }
        }
    }

    fun loadMoreDataPopular(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoPopular.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }


    private fun getListPhotoTrending(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val loadMorePhoto = it.photos
                    mListPhotoTrending.postValue(loadMorePhoto)
                    Log.d("----", it.photos.toString())
                }
        }
    }

    fun loadMoreDataTrending(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoTrending.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }

}