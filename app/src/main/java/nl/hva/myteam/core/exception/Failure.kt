package nl.hva.myteam.core.exception

import java.lang.Exception

class Failure {

    class GenericError(message: String) : Throwable(message)
    class ServerError(throwable: Throwable) : Throwable(throwable)
    class NetworkConnection : Throwable()
    class FullTeamError : Throwable()
    class NoTeamFoundOnFirestoreError : Throwable()
    class FirestoreAddError(throwable: Throwable) : Throwable(throwable)
    class FirestoreDeleteError(throwable: Throwable) : Throwable(throwable)
    class NoTeamSpotError : Throwable()

}