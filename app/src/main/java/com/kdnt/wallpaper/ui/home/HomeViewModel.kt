package com.kdnt.wallpaper.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kdnt.wallpaper.core.base.BaseViewModel
import com.kdnt.wallpaper.data.model.CuratedPhotosModel
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.data.repository.WallPaperRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class HomeViewModel(private val wallPaperRepository: WallPaperRepository) : BaseViewModel() {
    init {
        getListPhoto(1)
    }

    private val mListPhotoData: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoLiveData() = mListPhotoData
    private val mLoadMoreData: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoLoadMoreLiveData() = mLoadMoreData

     private fun getListPhoto(page : Int) {
        GlobalScope.launch {
            wallPaperRepository.getListPhoto(page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoData.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }

     fun loadMoreData(page : Int){
        GlobalScope.launch {
            wallPaperRepository.getListPhoto(page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoData.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }


    fun searchListPhoto(name: String) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val loadMorePhoto = it.photos
                    mLoadMoreData.postValue(loadMorePhoto)
                    Log.d("----", it.photos.toString())
                }
        }
    }
}