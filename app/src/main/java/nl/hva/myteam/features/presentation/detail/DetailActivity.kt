package nl.hva.myteam.features.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.hva.myteam.features.data.models.Pokemon

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val POKEMON = "POKEMON"

        fun getIntent(context: Context, pokemon: Pokemon): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(POKEMON, pokemon)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val pokemon = intent.getParcelableExtra<Pokemon>(POKEMON)
    }

}