package nl.hva.myteam.core.interactor

abstract class UseCase<out Result, in Params> {

    abstract operator fun invoke(params: Params): Result

    class None

}