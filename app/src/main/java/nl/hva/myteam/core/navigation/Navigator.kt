package nl.hva.myteam.core.navigation

import android.content.Context
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.presentation.detail.DetailActivity
import nl.hva.myteam.features.presentation.myteam.MyTeamActivity
import nl.hva.myteam.features.presentation.pokedex.PokedexActivity

class Navigator {

    fun showMyTeamActivity(context: Context) {
        context.startActivity(MyTeamActivity.getIntent(context))
    }

    fun showPokedexActivity(context: Context) {
        context.startActivity(PokedexActivity.getIntent(context))
    }

    fun showDetailActivity(context: Context, pokemon: Pokemon) {
        context.startActivity(DetailActivity.getIntent(context, pokemon))
    }

}