package nl.hva.myteam.features.presentation.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import nl.hva.myteam.R
import nl.hva.myteam.core.extension.observe
import nl.hva.myteam.core.navigation.Navigator
import nl.hva.myteam.features.data.models.Pokemon
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : AppCompatActivity() {

    private val viewModel by viewModel<PokedexViewModel>()
    private val navigator by inject<Navigator>()
    private val adapter = PokedexAdapter {
        navigator.showDetailActivity(this, it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)

        viewModel.apply {
            observe(pokedex, ::handlePokedex)
        }
        initViews()
    }

    private fun initViews() {
        viewModel.getAllPokemon()
        assets
    }

    private fun handlePokedex(pokedex: List<Pokemon>) {

    }

}