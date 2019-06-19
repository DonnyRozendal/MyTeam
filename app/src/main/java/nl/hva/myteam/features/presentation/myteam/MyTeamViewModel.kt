package nl.hva.myteam.features.presentation.myteam

import androidx.lifecycle.MutableLiveData
import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.domain.usecases.DeletePokemonUseCase
import nl.hva.myteam.features.domain.usecases.GetTeamFromFirestoreUseCase
import nl.hva.myteam.features.domain.usecases.GetTeamUseCase
import nl.hva.myteam.features.domain.usecases.UpdatePokemonUseCase

class MyTeamViewModel(
    private val getTeamUseCase: GetTeamUseCase,
    private val getTeamFromFirestoreUseCase: GetTeamFromFirestoreUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase
) : BaseViewModel() {

    val team = MutableLiveData<List<Pokemon>>()
    val teamFromFirestore = MutableLiveData<List<Pokemon>>()
    val pokemonUpdated = MutableLiveData<Boolean>()
    val pokemonDeleted = MutableLiveData<Boolean>()

    fun getTeam() {
        run {
            team.postValue(getTeamUseCase(UseCase.None()))
        }
    }

    fun getTeamFromFirestore() {
        run {
            teamFromFirestore.postValue(getTeamFromFirestoreUseCase(UseCase.None()))
        }
    }

    fun updatePokemon(pokemon: Pokemon) {
        run {
            val params = UpdatePokemonUseCase.Params(pokemon)
            pokemonUpdated.postValue(updatePokemonUseCase(params))
        }
    }

    fun deletePokemon(pokemon: Pokemon) {
        run {
            val params = DeletePokemonUseCase.Params(pokemon)
            pokemonDeleted.postValue(deletePokemonUseCase(params))
        }
    }

}