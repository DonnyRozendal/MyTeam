package nl.hva.myteam.features.presentation.detail

import androidx.lifecycle.MutableLiveData
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.domain.usecases.GetPokemonDetailsUseCase
import nl.hva.myteam.features.domain.usecases.StorePokemonUseCase

class DetailViewModel(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val storePokemonUseCase: StorePokemonUseCase
) : BaseViewModel() {

    val pokemonDetails = MutableLiveData<PokemonDetails>()
    val storedPokemon = MutableLiveData<Boolean>()

    fun getPokemonDetails(name: String) {
        run {
            val params = GetPokemonDetailsUseCase.Params(name)
            pokemonDetails.postValue(getPokemonDetailsUseCase(params))
        }
    }

    fun storePokemon(pokemon: Pokemon) {
        run {
            val params = StorePokemonUseCase.Params(pokemon)
            storedPokemon.postValue(storePokemonUseCase(params))
        }
    }

}