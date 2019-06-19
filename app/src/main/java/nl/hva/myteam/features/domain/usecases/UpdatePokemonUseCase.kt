package nl.hva.myteam.features.domain.usecases

import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.repositories.PokemonRepository

class UpdatePokemonUseCase(private val pokemonRepository: PokemonRepository) :
    UseCase<Boolean, UpdatePokemonUseCase.Params>() {

    override fun invoke(params: Params) = pokemonRepository.updatePokemon(params.pokemon)

    data class Params(val pokemon: Pokemon)

}