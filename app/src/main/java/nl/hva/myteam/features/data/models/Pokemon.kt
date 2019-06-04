package nl.hva.myteam.features.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    val url: String
) : Parcelable