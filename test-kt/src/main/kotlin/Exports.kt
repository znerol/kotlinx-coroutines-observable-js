import ch.znerol.kotlinx.coroutines.observable.asSubscriber
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

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
