package nl.hva.myteam.features.data.repositories

import nl.hva.myteam.core.platform.BaseRepository
import nl.hva.myteam.core.platform.NetworkHandler
import nl.hva.myteam.features.data.datasource.Api
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonResponse

class PokemonRepository(networkHandler: NetworkHandler, private val api: Api) :
    BaseRepository(networkHandler) {

    fun getAllPokemon(): List<Pokemon> {
        val amountOfPokemon = "807"
        return request(
            api.getAllPokemon(amountOfPokemon),
            { it.results },
            PokemonResponse.empty()
        )
    }

}