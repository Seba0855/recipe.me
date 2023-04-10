package pl.smcebi.recipeme.domain.common

sealed class DomainResult<Data, Error> {
    data class Success<Data, Error>(val data: Data) : DomainResult<Data, Error>()
    data class Failure<Data, Error>(val error: Error) : DomainResult<Data, Error>()

    val isSuccess: Boolean
        get() = this is Success
    val isFailure: Boolean
        get() = this is Failure

    val dataOrNull: Data?
        get() = (this as? Success)?.data
    val errorOrNull: Error?
        get() = (this as? Failure)?.error

    inline fun onSuccess(action: (Data) -> Unit): DomainResult<Data, Error> {
        if (this is Success) action(data)
        return this
    }

    inline fun onFailure(action: (Error) -> Unit): DomainResult<Data, Error> {
        if (this is Failure) action(error)
        return this
    }

    inline fun <R> map(onSuccess: (Data) -> R, onError: (Error) -> R): R =
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onError(error)
        }

    companion object {
        fun <Error> success(): DomainResult<Unit, Error> = Success(Unit)
        fun <Data> failure(): DomainResult<Data, Unit> = Failure(Unit)
    }
}
