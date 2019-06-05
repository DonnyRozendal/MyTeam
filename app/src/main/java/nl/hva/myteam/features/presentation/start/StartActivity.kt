package nl.hva.myteam.features.presentation.start

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import nl.hva.myteam.R
import nl.hva.myteam.core.navigation.Navigator
import org.koin.android.ext.android.inject

class StartActivity : AppCompatActivity() {

    private val navigator by inject<Navigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initViews()
    }

    private fun initViews() {
        buttonMyTeam.setOnClickListener {
            navigator.showMyTeamActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        buttonPokedex.setOnClickListener {
            navigator.showPokedexActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

}