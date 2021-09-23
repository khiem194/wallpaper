package com.kdnt.wallpaper.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kdnt.wallpaper.core.base.BaseViewModel
import com.kdnt.wallpaper.data.model.PhotoModel
import com.kdnt.wallpaper.data.repository.WallPaperRepository
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel(private val wallPaperRepository: WallPaperRepository) : BaseViewModel() {
    private val mListPhotoByName: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()
    fun searchListPhotoByNameLiveData() = mListPhotoByName

    private val mResult: MutableLiveData<Int> = MutableLiveData()
    fun searchResultListPhotoByNameLiveData() = mResult


    fun searchListPhotoByName(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val result = it.total_results
                    mResult.postValue(result)
                    val loadMorePhoto = it.photos
                    mListPhotoByName.postValue(loadMorePhoto)
                    Log.d("----", it.photos.toString())
                }
        }
    }

    fun loadMoreData(name: String, page: Int) {
        GlobalScope.launch {
            wallPaperRepository.searchListPhoto(name, page)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    val listPhotosModel = it.photos
                    mListPhotoByName.postValue(listPhotosModel)
                    Log.d("----", it.photos.toString())
                }
        }
    }
}
