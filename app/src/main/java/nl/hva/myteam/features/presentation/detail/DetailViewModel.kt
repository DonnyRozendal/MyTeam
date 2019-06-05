package nl.hva.myteam.features.presentation.detail

import androidx.lifecycle.MutableLiveData
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.domain.usecases.GetPokemonDetailsUseCase

class DetailViewModel(private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase) : BaseViewModel() {

    val pokemonDetails = MutableLiveData<PokemonDetails>()

    fun getPokemonDetails(name: String) {
        run {
            val params = GetPokemonDetailsUseCase.Params(name)
            pokemonDetails.postValue(getPokemonDetailsUseCase(params))
        }
    }

}