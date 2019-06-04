package nl.hva.myteam.features.domain.usecases

import nl.hva.myteam.core.interactor.UseCase
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.repositories.PokemonRepository

class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) :
    UseCase<List<Pokemon>, UseCase.None>() {

    override fun invoke(params: None) = pokemonRepository.getAllPokemon()

}