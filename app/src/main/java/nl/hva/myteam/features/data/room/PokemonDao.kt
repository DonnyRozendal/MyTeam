package nl.hva.myteam.features.data.room

import androidx.room.*
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonResponse

@Dao
interface PokemonDao {

    @Delete
    fun delete(pokemon: Pokemon): Int

    @Query("SELECT * FROM pokemonTable")
    fun getTeam(): List<Pokemon>

    @Insert
    fun insert(pokemon: Pokemon): Long

    @Update
    fun update(pokemon: Pokemon): Int

    @Query("SELECT * FROM pokedexTable")
    fun getPokedex(): List<PokemonResponse>

    @Insert
    fun insert(pokemonResponse: PokemonResponse)

}