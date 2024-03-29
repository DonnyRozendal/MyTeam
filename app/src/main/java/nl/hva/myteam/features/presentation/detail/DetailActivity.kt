package nl.hva.myteam.features.presentation.detail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_detail.*
import nl.hva.myteam.R
import nl.hva.myteam.core.exception.Failure
import nl.hva.myteam.core.extension.failure
import nl.hva.myteam.core.extension.observe
import nl.hva.myteam.features.data.models.Pokemon
import nl.hva.myteam.features.data.models.PokemonDetails
import nl.hva.myteam.features.presentation.models.MoveRow
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailViewModel>()
    val moveListLevelUp = mutableListOf<MoveRow>()
    val moveListMachine = mutableListOf<MoveRow>()

    val pokemon: Pokemon by lazy {
        intent.getParcelableExtra<Pokemon>(POKEMON)
    }

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
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pokemon.name.capitalize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.apply {
            observe(pokemonDetails, ::handlePokemonDetails)
            observe(storedPokemon, ::handleStoredPokemon)
            failure(failure, ::handleFailure)
        }
        viewModel.getPokemonDetails(pokemon.name.decapitalize())

        iconAdd.setOnClickListener {
            viewModel.storePokemon(pokemon)
        }
    }

    private fun initTabLayout() {
        viewPagerDetail.adapter = DetailPagerAdapter(supportFragmentManager)
        tabsDetail.setupWithViewPager(viewPagerDetail)
    }

    private fun findMoves(pokemonDetails: PokemonDetails, learnType: LearnType): List<MoveRow> {
        val moveList = mutableListOf<MoveRow>()
        pokemonDetails.moves.map { x ->
            x.versionGroupDetails.map { y ->
                y.apply {
                    if (moveLearnMethod.name == learnType.type && versionGroup.name == "sun-moon") {
                        moveList.add(MoveRow(null, levelLearnedAt, x.move, pokemon.id))
                    }
                }
            }
        }
        return sortMoves(moveList)
    }

    private fun sortMoves(moveList: MutableList<MoveRow>): List<MoveRow> {
        return moveList.sortedWith(Comparator { moveRow1, moveRow2 ->
            if (moveRow1.level.compareTo(moveRow2.level) != 0) {
                moveRow1.level.toInt().compareTo(moveRow2.level.toInt())
            } else {
                moveRow1.moveInfo.name.compareTo(moveRow2.moveInfo.name)
            }
        })
    }

    private fun handlePokemonDetails(pokemonDetails: PokemonDetails) {
        moveListLevelUp.addAll(findMoves(pokemonDetails, LearnType.LEVELUP))
        moveListMachine.addAll(findMoves(pokemonDetails, LearnType.MACHINE))
        initTabLayout()
    }

    private var oldId: Long = 0
    private fun handleStoredPokemon(newId: Long) {
        if (newId != oldId) {
            createNotificationChannel()
            val builder = NotificationCompat.Builder(this, "pokemon")
                .setSmallIcon(R.drawable.ic_pokeball)
                .setContentTitle("Pokemon added!")
                .setContentText("Your team size increased, great job!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            NotificationManagerCompat.from(this).notify(0, builder.build())
            Toast.makeText(this, getString(R.string.detail_success), Toast.LENGTH_SHORT).show()
        }
        oldId = newId
    }

    private fun handleFailure(throwable: Throwable) {
        when (throwable) {
            is Failure.FullTeamError -> {
                Toast.makeText(this, getString(R.string.detail_error_full_team), Toast.LENGTH_SHORT).show()
            }
            is Failure.FirebaseError -> {
                Toast.makeText(this, getString(R.string.detail_error_firebase), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("pokemon", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
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

enum class LearnType(val type: String) {
    LEVELUP("level-up"),
    EGG("egg"),
    TUTOR("tutor"),
    MACHINE("machine"),
}