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
) : BaseRepository(networkHandler) {

    private val db = Firebase.firestore(Firebase.app("MyApp"))
    private val maxTeamSize = 6

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

    fun storePokemon(pokemon: Pokemon): Boolean {
        val onlineTeam = getTeamFromFirestore()
        pokemonDao.clearTable()
        for (item in onlineTeam) {
            pokemonDao.insert(item)
        }
        if (onlineTeam.size >= maxTeamSize) {
            throw Failure.FullTeamError()
        } else {
            pokemon.nickname = pokemon.name
            pokemon.teamSpot = onlineTeam.size + 1

            storePokemonOnFirestore(pokemon)
            pokemonDao.insert(pokemon)
            return true
        }
    }

    fun deletePokemon(pokemon: Pokemon): Boolean {
        val currentTeam = getTeamFromFirestore()
        val pokemonToReAdd = mutableListOf<Pokemon>()

        val pokemonToDelete = pokemon.teamSpot ?: throw Failure.NoTeamSpotError()
        deletePokemonFromFirestore(pokemonToDelete)
        for (teamSpot in (pokemonToDelete + 1)..maxTeamSize) {
            deletePokemonFromFirestore(teamSpot)
            pokemonToReAdd.add(currentTeam[teamSpot])
        }
        for (item in pokemonToReAdd) {
            item.teamSpot?.let {
                item.teamSpot = it - 1
            }
            storePokemonOnFirestore(item)
        }
        pokemonDao.clearTable()
        val updatedTeam = getTeamFromFirestore()
        for (item in updatedTeam) {
            pokemonDao.insert(item)
        }
        return true
    }

    fun getTeam() = pokemonDao.getTeam()

    fun updatePokemon(pokemon: Pokemon): Boolean {
        storePokemonOnFirestore(pokemon)
        pokemonDao.update(pokemon)
        return true
    }

    fun getTeamFromFirestore(): List<Pokemon> {
        val snapshot = db.collection("team").get()
        val result = snapshot.result ?: throw Failure.NoTeamFoundOnFirestoreError()
        return result.toObjects()
    }

    private fun storePokemonOnFirestore(pokemon: Pokemon) {
        db.collection("team")
            .document("${pokemon.teamSpot}")
            .set(
                hashMapOf(
                    "id" to pokemon.id,
                    "name" to pokemon.name,
                    "nickname" to pokemon.nickname,
                    "url" to pokemon.url
                )
            )
            .addOnFailureListener {
                throw Failure.FirestoreAddError(it)
            }
    }

    private fun deletePokemonFromFirestore(id: Int) {
        db.collection("team")
            .document("$id")
            .delete()
            .addOnFailureListener {
                throw Failure.FirestoreDeleteError(it)
            }
    }

}