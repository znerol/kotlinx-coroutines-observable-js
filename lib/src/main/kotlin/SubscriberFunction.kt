package ch.znerol.kotlinx.coroutines.observable

typealias SubscriberFunction<T> = (observer: SubscriptionObserver<T>) -> Subscription