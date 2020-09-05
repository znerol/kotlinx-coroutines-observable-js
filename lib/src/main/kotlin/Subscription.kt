package ch.znerol.kotlinx.coroutines.observable

@JsExport
external interface Subscription {
    // Cancels the subscription
    fun unsubscribe(): Unit

    // A boolean value indicating whether the subscription is closed
    val closed: Boolean
}