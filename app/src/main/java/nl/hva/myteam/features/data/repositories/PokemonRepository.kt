package nl.hva.myteam.features.data.repositories

import nl.hva.myteam.core.exception.Failure
import nl.hva.myteam.core.platform.BaseRepository
import nl.hva.myteam.core.platform.NetworkHandler
import nl.hva.myteam.features.data.datasource.Api
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.data.models.PokemonResponse
import nl.hva.myteam.features.data.room.PokemonDao

class PokemonRepository(
    networkHandler: NetworkHandler,
    private val api: Api,
    private val pokemonDao: PokemonDao
) :
    BaseRepository(networkHandler) {

    fun getAllPokemon(): PokemonResponse {
        val localPokedex = pokemonDao.getPokedex()
        println("stop")
        return if (localPokedex.isNotEmpty()) {
            println("Pokedex retrieved from local storage")
            localPokedex[0]
        } else {
            println("Pokedex retrieved from remote data source")
            val amountOfPokemon = "807"
            val result = request(
                api.getAllPokemon(amountOfPokemon),
                { it },
                PokemonResponse.empty()
            )
            pokemonDao.insert(result)
            result
        }
    }

    fun getPokemonDetails(name: String): PokemonDetails {
        return request(api.getPokemonDetails(name), { it }, PokemonDetails.empty())
    }

    fun storePokemon(pokemon: Pokemon): Long {
        val team = pokemonDao.getTeam()
        val maxTeamSize = 6
        if (team.size >= maxTeamSize) {
            throw Failure.FullTeamError()
        } else {
            pokemon.nickname = pokemon.name
            return pokemonDao.insert(pokemon)
        }
    }

    fun deletePokemon(pokemon: Pokemon) = pokemonDao.delete(pokemon)

    fun getTeam() = pokemonDao.getTeam()

    fun updatePokemon(pokemon: Pokemon) = pokemonDao.update(pokemon)

}