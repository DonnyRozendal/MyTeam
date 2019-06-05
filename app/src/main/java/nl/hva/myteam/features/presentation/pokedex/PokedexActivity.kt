package nl.hva.myteam.features.presentation.pokedex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pokedex.*
import nl.hva.myteam.R
import nl.hva.myteam.core.extension.observe
import nl.hva.myteam.core.navigation.Navigator
import nl.hva.myteam.features.data.models.Pokemon
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) = Intent(context, PokedexActivity::class.java)
    }

    private val viewModel by viewModel<PokedexViewModel>()
    private val navigator by inject<Navigator>()
    private val adapter = PokedexAdapter {
        navigator.showDetailActivity(this, it)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex)
        supportActionBar?.title = getString(R.string.pokedex_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}