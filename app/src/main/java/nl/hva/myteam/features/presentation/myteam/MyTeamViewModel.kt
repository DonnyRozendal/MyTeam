package nl.hva.myteam.features.presentation.myteam

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import nl.hva.myteam.core.exception.Failure
import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.domain.usecases.DeletePokemonUseCase
import nl.hva.myteam.features.domain.usecases.GetTeamUseCase
import nl.hva.myteam.features.domain.usecases.UpdatePokemonUseCase

class MyTeamViewModel(
    private val getTeamUseCase: GetTeamUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase,
    private val deletePokemonUseCase: DeletePokemonUseCase
) : BaseViewModel() {

    val team = MutableLiveData<List<Pokemon>>()
    val pokemonUpdated = MutableLiveData<Int>()
    val pokemonDeleted = MutableLiveData<Int>()

    fun getTeam() {
        run {
            team.postValue(getTeamUseCase(UseCase.None()))
        }
    }

    fun updatePokemon(pokemon: Pokemon) {
        FirebaseFirestore.getInstance()
            .collection("team")
            .document("${pokemon.teamSpot}")
            .set(
                hashMapOf(
                    "name" to pokemon.name,
                    "nickname" to pokemon.nickname,
                    "url" to pokemon.url
                )
            )
            .addOnSuccessListener {
                run {
                    val params = UpdatePokemonUseCase.Params(pokemon)
                    pokemonUpdated.postValue(updatePokemonUseCase(params))
                }
            }
            .addOnFailureListener {
                run { throw Failure.FirebaseError(it) }
            }
    }

    fun deletePokemon(pokemon: Pokemon) {
        run {
            deletePokemonFromFirestore(pokemon, pokemon.teamSpot)
        }
    }

    private fun deletePokemonFromFirestore(pokemon: Pokemon, teamSpot: Int?) {
        if (teamSpot == null) throw Failure.NoTeamSpotError()
        if (teamSpot <= Pokemon.MAX_TEAM_SIZE) {
            FirebaseFirestore.getInstance()
                .collection("team")
                .document("$teamSpot")
                .delete()
                .addOnSuccessListener {
                    deletePokemonFromFirestore(pokemon, teamSpot + 1)
                }
        } else {
            run {
                val localTeam = getTeamUseCase(UseCase.None())
                pokemon.teamSpot?.let {
                    if (it == localTeam.size) {
                        val params = DeletePokemonUseCase.Params(pokemon)
                        pokemonDeleted.postValue(deletePokemonUseCase(params))
                    } else {
                        for (i in (it + 1)..localTeam.size) {
                            for (item in localTeam) {
                                if (item.teamSpot == i) {
                                    item.teamSpot = i - 1
                                    updatePokemonUseCase(UpdatePokemonUseCase.Params(item))
                                    FirebaseFirestore.getInstance()
                                        .collection("team")
                                        .document("${item.teamSpot}")
                                        .set(
                                            hashMapOf(
                                                "name" to item.name,
                                                "nickname" to item.nickname,
                                                "url" to item.url
                                            )
                                        )
                                        .addOnSuccessListener {
                                            if (i == localTeam.size) {
                                                run {
                                                    val params = DeletePokemonUseCase.Params(pokemon)
                                                    pokemonDeleted.postValue(deletePokemonUseCase(params))
                                                }
                                            }
                                        }
                                        .addOnFailureListener {
                                            run { throw Failure.FirebaseError(it) }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}