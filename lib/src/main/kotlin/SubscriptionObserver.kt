package ch.znerol.kotlinx.coroutines.observable

@JsExport
external interface SubscriptionObserver<T> {
    // Sends the next value in the sequence
    fun next(value: T)

    // Sends the sequence error
    fun error(errorValue: dynamic)

    // Sends the completion notification
    fun complete()

    // A boolean value indicating whether the subscription is closed
    val closed: Boolean
}