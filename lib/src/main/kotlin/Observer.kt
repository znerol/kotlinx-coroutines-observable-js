package ch.znerol.kotlinx.coroutines.observable

@JsExport
external interface Observer<T> {
    val closed: Boolean
    fun next(value: T)
    fun error(err: Any)
    fun complete()
}
