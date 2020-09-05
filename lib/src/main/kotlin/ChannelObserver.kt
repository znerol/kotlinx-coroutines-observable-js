package ch.znerol.kotlinx.coroutines.observable

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

/**
 * TC39 [Observer] which uses a [SendChannel] to pass values to a flow.
 */
class ChannelObserver<T>(
        private val channel: SendChannel<T>,
        private val scope: CoroutineScope
) : Observer<T> {
    override fun next(value: T) {
        scope.launch {
            channel.send(value)
        }
    }

    override fun error(err: Any) {
        scope.launch {
            channel.close(if (err is Throwable) {
                err
            } else {
                RuntimeException("$err")
            })
        }
    }

    override fun complete() {
        scope.launch {
            channel.close()
        }
    }

    @ExperimentalCoroutinesApi
    override val closed: Boolean
        get() = channel.isClosedForSend
}