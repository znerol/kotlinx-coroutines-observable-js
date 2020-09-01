import ch.znerol.kotlinx.coroutines.observable.asSubscriber
import kotlinx.coroutines.flow.flowOf

@ExperimentalJsExport
@JsExport
val intFlow = flowOf(1, 2, 3).asSubscriber()
