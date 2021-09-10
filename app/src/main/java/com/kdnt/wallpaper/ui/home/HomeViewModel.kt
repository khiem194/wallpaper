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

class HomeViewModel(private val wallPaperRepository: WallPaperRepository) : BaseViewModel() {
    init {
        getListPhoto()
    }
    private val mListPhotoData: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun getListPhotoLiveData() = mListPhotoData

    private fun getListPhoto() {
        GlobalScope.launch {
            wallPaperRepository.getListPhoto()
                .subscribeOn(Schedulers.io())
                .subscribe{
                    val listPhotosModel = it.photos
                    mListPhotoData.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }

    fun searchListPhoto(name : String){
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name)
                .subscribeOn(Schedulers.io())
                .subscribe{
                    val searchListPhoto = it.photos
                    mListPhotoData.postValue(searchListPhoto)
                    Log.d("----", it.photos.toString())
                }
        }
    }
}