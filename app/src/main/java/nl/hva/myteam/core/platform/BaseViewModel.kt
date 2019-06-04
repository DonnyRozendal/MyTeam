package nl.hva.myteam.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    val failure = MutableLiveData<Throwable>()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        failure.value = throwable
    }

    fun run(function: () -> Unit) {
        viewModelScope.launch(errorHandler) {
            withContext(Dispatchers.IO) {
                function()
            }
        }
    }

}