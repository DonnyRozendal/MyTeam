package nl.hva.myteam.features.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
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
            pokemon.teamSpot = team.size + 1
            return pokemonDao.insert(pokemon)
        }
    }

    fun deletePokemon(pokemon: Pokemon) = pokemonDao.delete(pokemon)

    fun getTeam(): List<Pokemon> {
        val localTeam = pokemonDao.getTeam()
        val onlineTeam = getTeamFromFirestore()


        return pokemonDao.getTeam()
    }

    fun updatePokemon(pokemon: Pokemon) = pokemonDao.update(pokemon)

    private fun getTeamFromFirestore(): List<Pokemon>? {
        val db = Firebase.firestore(Firebase.app("MyApp"))
        val snapshot = db.collection("team").get()
        return snapshot.result?.toObjects()
    }



}