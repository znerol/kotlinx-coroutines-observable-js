package ch.znerol.kotlinx.coroutines.observable

@JsExport
external interface Observable<T> {
    // Subscribes to the sequence with an observer
    fun subscribe(observer: Observer<T>): Subscription
}
