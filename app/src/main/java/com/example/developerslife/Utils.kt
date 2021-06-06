package com.example.developerslife

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class GifTypes(val i: Int, val urlName: String) : Parcelable {
    LAST(0, "latest"), BEST(1, "top"), HOT(2, "hot")
}