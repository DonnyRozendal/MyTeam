package nl.hva.myteam.features.presentation.myteam

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_team.*
import kotlinx.android.synthetic.main.view_edit_nickname.view.*
import nl.hva.myteam.R
import nl.hva.myteam.core.extension.observe
import nl.hva.myteam.features.data.models.Pokemon
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyTeamActivity : AppCompatActivity() {

    private val adapter = MyTeamAdapter(::clickItem, ::clickDelete)
    private val viewModel by viewModel<MyTeamViewModel>()

    companion object {
        fun getIntent(context: Context) = Intent(context, MyTeamActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        supportActionBar?.title = getString(R.string.my_team_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.apply {
            observe(team, ::handleTeam)
            observe(pokemonUpdated, ::handlePokemonUpdatedOrDeleted)
            observe(pokemonDeleted, ::handlePokemonUpdatedOrDeleted)
        }
        initViews()
    }

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.getTeam()
    }

    private fun showEditDialog(pokemon: Pokemon, onPositiveClicked: (pokemon: Pokemon) -> Unit) {
        val view = View.inflate(this, R.layout.view_edit_nickname, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("Change nickname")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Confirm") { dialog, _ ->
                val newPokemon = Pokemon(pokemon.id, pokemon.teamSpot, view.editText.text.toString().decapitalize(), pokemon.name, pokemon.url)
                onPositiveClicked(newPokemon)
                dialog.dismiss()
            }
            .create()
        view.editText.setText(pokemon.nickname?.capitalize())
        alertDialog.show()
    }

    private fun clickItem(pokemon: Pokemon) {
        showEditDialog(pokemon) {
            viewModel.updatePokemon(it)
        }
    }

    private fun clickDelete(pokemon: Pokemon) {
        viewModel.deletePokemon(pokemon)
    }

    private fun handleTeam(team: List<Pokemon>) {
        adapter.submitList(team)
        if (team.isEmpty()) {
            textViewNoTeam.visibility = View.VISIBLE
        }
    }

    private fun handlePokemonUpdatedOrDeleted(rowsAffected: Int) {
        if (rowsAffected >= 1) {
            viewModel.getTeam()
        }
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