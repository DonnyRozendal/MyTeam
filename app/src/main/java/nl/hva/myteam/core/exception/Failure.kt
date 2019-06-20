package nl.hva.myteam.core.exception

class Failure {

    class GenericError(message: String) : Throwable(message)
    class ServerError(throwable: Throwable) : Throwable(throwable)
    class NetworkConnection : Throwable()
    class FullTeamError : Throwable()
    class FirebaseError(throwable: Throwable) : Throwable(throwable)
    class NoTeamSpotError : Throwable()

}