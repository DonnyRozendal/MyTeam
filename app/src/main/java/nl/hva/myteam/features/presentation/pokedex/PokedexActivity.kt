package nl.hva.myteam.features.presentation.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pokedex.*
import kotlinx.android.synthetic.main.view_pokedex_item.*
import nl.hva.myteam.R
import nl.hva.myteam.core.extension.observe
import nl.hva.myteam.core.navigation.Navigator
import nl.hva.myteam.features.data.models.Pokemon
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

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
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.getAllPokemon()
    }

    private fun handlePokedex(pokedex: List<Pokemon>) {
        adapter.submitList(pokedex)
    }

}