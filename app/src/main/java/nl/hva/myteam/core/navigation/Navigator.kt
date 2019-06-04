package nl.hva.myteam.core.navigation

import android.content.Context
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.presentation.detail.DetailActivity

class Navigator {

    fun showDetailActivity(context: Context, pokemon: Pokemon) {
        context.startActivity(DetailActivity.getIntent(context, pokemon))
    }

}