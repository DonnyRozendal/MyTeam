package nl.hva.myteam.features.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PokemonDetails(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("baseExperience")
    val baseExperience: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("moves")
    val moves: List<MoveVersion>
) : Parcelable {
    companion object {
        fun empty(): PokemonDetails {
            return PokemonDetails("", "", "", "", "", "", emptyList())
        }
    }
}

@Parcelize
class MoveVersion(
    @SerializedName("move")
    val move: MoveInfo,
    @SerializedName("versionGroupDetails")
    val versionGroupDetails: List<MoveVersionGroupDetail>
) : Parcelable

@Parcelize
class MoveInfo(
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    val url: String
) : Parcelable

@Parcelize
class MoveVersionGroupDetail(
    @SerializedName("levelLearnedAt")
    val levelLearnedAt: String,
    @SerializedName("moveLearnMethod")
    val moveLearnMethod: MoveLearnMethod,
    @SerializedName("versionGroup")
    val versionGroup: MoveVersionGroup
) : Parcelable

@Parcelize
class MoveLearnMethod(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Parcelable

@Parcelize
class MoveVersionGroup(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Parcelable