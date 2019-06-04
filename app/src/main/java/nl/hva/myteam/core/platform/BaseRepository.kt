package nl.hva.myteam.core.platform

import nl.hva.myteam.core.exception.Failure
import retrofit2.Call

abstract class BaseRepository(private val networkHandler: NetworkHandler) {

    fun <T, R> request(
        call: Call<T>,
        transform: (T) -> R,
        default: T
    ): R {
        if (networkHandler.isConnected == false) throw Failure.NetworkConnection()
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> transform((response.body() ?: default))
                false -> throw Failure.GenericError(response.errorBody()?.string() ?: "")
            }
        } catch (exception: Throwable) {
            throw Failure.ServerError(exception)
        }

    }

//    fun <T, R> requestList(
//        call: Call<BaseResponse<List<T>>>,
//        transform: (List<T>) -> List<R>,
//        default: List<T>
//    ): List<R> {
//        if (networkHandler.isConnected == false) throw Failure.NetworkConnection()
//        return try {
//            val response = call.execute()
//            when (response.isSuccessful) {
//                true -> transform((response.body()?.results ?: default))
//                false -> throw Failure.GenericError(response.errorBody()?.string() ?: "")
//            }
//        } catch (exception: Throwable) {
//            throw Failure.ServerError(exception)
//        }
//
//    }

}