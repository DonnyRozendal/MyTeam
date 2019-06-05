package nl.hva.myteam.features.domain.usecases

import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.data.repositories.PokemonRepository

class GetPokemonDetailsUseCase(private val pokemonRepository: PokemonRepository) :
    UseCase<PokemonDetails, GetPokemonDetailsUseCase.Params>() {

    override fun invoke(params: Params): PokemonDetails {
        return pokemonRepository.getPokemonDetails(params.name)
    }

    data class Params(val name: String)

}