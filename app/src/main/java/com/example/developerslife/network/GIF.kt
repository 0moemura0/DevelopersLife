package com.example.developerslife.network
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GIF (
    @SerializedName("gifURL") val url: String,
    @SerializedName("description") val description: String ) : Parcelable {
}
