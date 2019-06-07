package nl.hva.myteam.features.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pokedexTable")
data class PokemonResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("count")
    val count: String,
    @SerializedName("results")
    val results: List<Pokemon>
) {

    companion object {
        fun empty() = PokemonResponse(
            0,
            "",
            emptyList()
        )
    }

}