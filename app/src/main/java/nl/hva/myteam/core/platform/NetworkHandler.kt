package nl.hva.myteam.core.platform

import android.content.Context
import nl.hva.myteam.core.extension.networkInfo

class NetworkHandler(private val context: Context) {

    val isConnected get() = context.networkInfo?.isConnected

}