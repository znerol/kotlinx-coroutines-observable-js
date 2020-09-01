package ch.znerol.kotlinx.coroutines.observable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Turns a flow into a TC39 [SubscriberFunction] suitable for consumption by compliant reactive frameworks such as rxjs.
 *
 * https://github.com/tc39/proposal-observable
 *
 * Example (Kotlin):
 *     @JsExport
 *     val testSubscriber = flowOf(1,2,3,4).asSubscriber()
 *
 * Example (JavaScript):
 *     const obs = new Observer(testSubscriber);
 *     obs.subscribe(x => console.log(x));
 */
fun <T> Flow<T>.asSubscriber(scope: CoroutineScope = GlobalScope): SubscriberFunction<T> = { observer: SubscriptionObserver<T> ->
    val job = scope.launch {
        try {
            collect { value ->
                if (!observer.closed) {
                    observer.next(value)
                }
            }
        } catch (e: Exception) {
            observer.error(e)
        } finally {
            observer.complete()
        }
    }

    object : Subscription {
        override fun unsubscribe() {
            job.cancel()
        }
        override val closed = job.isCompleted
    }
}

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

external interface Subscription {
    // Cancels the subscription
    fun unsubscribe(): Unit

    // A boolean value indicating whether the subscription is closed
    val closed: Boolean
}

typealias SubscriberFunction<T> = (observer: SubscriptionObserver<T>) -> Subscription
