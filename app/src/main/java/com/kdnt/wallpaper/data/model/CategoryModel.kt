package com.kdnt.wallpaper.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val nameCategory: String,
) : Parcelable
