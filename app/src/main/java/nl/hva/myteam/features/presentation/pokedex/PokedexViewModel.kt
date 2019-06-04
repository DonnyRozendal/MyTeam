package nl.hva.myteam.features.presentation.pokedex

import androidx.lifecycle.MutableLiveData
import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.domain.usecases.GetAllPokemonUseCase

class PokedexViewModel(private val getAllPokemonUseCase: GetAllPokemonUseCase) : BaseViewModel() {

    val pokedex = MutableLiveData<List<Pokemon>>()

    fun getAllPokemon() {
        run {
            pokedex.postValue(getAllPokemonUseCase(UseCase.None()))
        }
    }

}