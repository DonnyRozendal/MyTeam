package nl.hva.myteam.features.domain.usecases

import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.repositories.PokemonRepository

class DeletePokemonUseCase(private val pokemonRepository: PokemonRepository) :
    UseCase<Boolean, DeletePokemonUseCase.Params>() {

    override fun invoke(params: Params) = pokemonRepository.deletePokemon(params.pokemon)

    data class Params(val pokemon: Pokemon)

}