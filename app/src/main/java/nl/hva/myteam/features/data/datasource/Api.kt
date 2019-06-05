package nl.hva.myteam.features.data.datasource

import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.data.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("pokemon")
    fun getAllPokemon(@Query("limit") limit: String): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetails(@Path("name") name: String): Call<PokemonDetails>

//    @GET("move/{id}")
//    fun getMove(@Path("id") id: String): Call<Move>
//
//    @GET("machine/{id}")
//    fun getMachine(@Path("id") id: String): Call<Machine>

}