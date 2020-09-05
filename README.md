Kotlin flow compatibility with TC39 observable
==============================================

[![Build Status](https://travis-ci.org/znerol/kotlinx-coroutines-observable-js.svg?branch=master)](https://travis-ci.org/znerol/kotlinx-coroutines-observable-js)

`Flow.asSubscriber()` extension function turns a [kotlin flow][1] into a [TC39
SubscriberFunction][2] suitable for consumption by compliant reactive
frameworks such as [rxjs][3] and [zen-observable][4].

`Observable.asFlow()` extension function turns a [TC39 Observable][2] into a
[kotlin flow][1].

Example for asSubscriber()
--------------------------

Define and export flows as subscriber function in kotlin:

```kotlin
@JsExport
val testSubscriber = flowOf(1,2,3,4).asSubscriber()
```

Consume exported flows as observable in JavaScript/TypeScript:

```javascript
const obs = new Observer(testSubscriber);
obs.subscribe(x => console.log(x));
```

Example for asFlow()
--------------------

Define and export a function which accepts an observable in kotlin:

```kotlin
@JsExport
fun printInKotlin(observable: Observable<Int>) {
    GlobalScope.launch {
        observable.asFlow().collect { value ->
            print(value)
        }
    }
}

Call exported function from JavaScript/TypeScript:

```javascript
const obs = Observable.of(1,2,3);
printInKotlin(obs);
```

License
-------

Apache License 2.0

[1]: https://kotlinlang.org/docs/reference/coroutines/flow.html
[2]: https://github.com/tc39/proposal-observable#observable
[3]: https://rxjs.dev/
[4]: https://github.com/zenparsing/zen-observable
