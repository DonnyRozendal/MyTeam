package nl.hva.myteam.features.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "pokemonTable")
data class Pokemon(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var nickname: String?,
    @SerializedName("name")
    var name: String,
    @SerializedName("url")
    val url: String
) : Parcelable {

    companion object {
        fun getEntry(pokemon: Pokemon): String? {
            return Regex("""(?<!v)\d+""").find(pokemon.url)?.value
        }
    }

}