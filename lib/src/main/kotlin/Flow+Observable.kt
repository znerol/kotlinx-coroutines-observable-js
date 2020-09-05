package ch.znerol.kotlinx.coroutines.observable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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
fun <T> Flow<T>.asSubscriber(
    scope: CoroutineScope = GlobalScope
): SubscriberFunction<T> = { observer: SubscriptionObserver<T> ->
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

/**
 * Turns a TC39 [Observable] into a kotlin flow.
 *
 * Example (Kotlin):
 *     @JsExport
 *     fun printInKotlin(observable: Observable<Int>) {
 *         GlobalScope.launch {
 *             observable.toFlow().collect { value ->
 *                 print(value)
 *             }
 *         }
 *     }
 *
 * Example (JavaScript):
 *     const obs = Observable.of(1,2,3);
 *     printInKotlin(obs);
 */
fun <T> Observable<T>.asFlow(
    scope: CoroutineScope = GlobalScope
): Flow<T> = flow {
    val channel = Channel<T>()
    val subscription = subscribe(ChannelObserver(channel, scope))
    try {
        for (value in channel) {
            emit(value)
        }
    } finally {
        subscription.unsubscribe()
    }
}
