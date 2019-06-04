package nl.hva.myteam.features.data.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    val count: String,
    @SerializedName("results")
    val results: List<Pokemon>
) {

    companion object {
        fun empty() = PokemonResponse(
            "",
            emptyList()
        )
    }

}