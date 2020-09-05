import ch.znerol.kotlinx.coroutines.observable.Observable
import ch.znerol.kotlinx.coroutines.observable.asFlow
import ch.znerol.kotlinx.coroutines.observable.asSubscriber
import kotlin.js.Promise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.promise

@ExperimentalJsExport
@JsExport
val intFlow = flowOf(1, 2, 3).asSubscriber()

@ExperimentalJsExport
@JsExport
val throwEvenFlow = flowOf(1, 2, 3).map { value ->
    if (value.rem(2) == 0) {
        throw IllegalStateException("Only odd values allowed")
    } else {
        value
    }
}.asSubscriber()

@ExperimentalJsExport
@JsExport
fun <T> collectInKotlin(observable: Observable<T>): Promise<Array<T>> {
    return GlobalScope.promise {
        observable.asFlow().toList().toTypedArray()
    }
}
