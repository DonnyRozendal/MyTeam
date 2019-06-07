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
    @SerializedName("base_experience")
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
    @SerializedName("version_group_details")
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
    @SerializedName("level_learned_at")
    val levelLearnedAt: String,
    @SerializedName("move_learn_method")
    val moveLearnMethod: MoveLearnMethod,
    @SerializedName("version_group")
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