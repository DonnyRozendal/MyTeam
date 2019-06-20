package nl.hva.myteam.features.presentation.detail

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import nl.hva.myteam.core.exception.Failure
import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.core.platform.BaseViewModel
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.domain.usecases.GetPokemonDetailsUseCase
import nl.hva.myteam.features.domain.usecases.GetTeamUseCase
import nl.hva.myteam.features.domain.usecases.StorePokemonUseCase

class DetailViewModel(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase,
    private val storePokemonUseCase: StorePokemonUseCase,
    private val getTeamUseCase: GetTeamUseCase
) : BaseViewModel() {

    val pokemonDetails = MutableLiveData<PokemonDetails>()
    val storedPokemon = MutableLiveData<Long>()

    fun getPokemonDetails(name: String) {
        run {
            val params = GetPokemonDetailsUseCase.Params(name)
            pokemonDetails.postValue(getPokemonDetailsUseCase(params))
        }
    }

    fun storePokemon(pokemon: Pokemon) {
        run {
            val localTeam = getTeamUseCase(UseCase.None())
            if (localTeam.size >= Pokemon.MAX_TEAM_SIZE) {
                throw Failure.FullTeamError()
            } else {
                pokemon.nickname = pokemon.name
                pokemon.teamSpot = localTeam.size + 1

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
                            val params = StorePokemonUseCase.Params(pokemon)
                            storedPokemon.postValue(storePokemonUseCase(params))
                        }
                    }
                    .addOnFailureListener {
                        run { throw Failure.FirebaseError(it) }
                    }
            }
        }
    }

}