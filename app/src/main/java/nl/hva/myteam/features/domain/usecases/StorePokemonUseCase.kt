package nl.hva.myteam.features.domain.usecases

import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.repositories.PokemonRepository

class StorePokemonUseCase(private val pokemonRepository: PokemonRepository) :
    UseCase<Long, StorePokemonUseCase.Params>() {

    override fun invoke(params: Params): Long {
        return pokemonRepository.storePokemon(params.pokemon)
    }

    data class Params(val pokemon: Pokemon)

}