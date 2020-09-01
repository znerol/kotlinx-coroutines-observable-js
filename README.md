Kotlin flow compatibility with TC39 observable
==============================================

Turns a [katlin flow][1] into a [TC39 SubscriberFunction][2] suitable for
consumption by compliant reactive frameworks such as [rxjs][3] and
[zen-observable][4].

Usage
-----

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

License
-------

Apache License 2.0

[1]: https://kotlinlang.org/docs/reference/coroutines/flow.html
[2]: https://github.com/tc39/proposal-observable#observable
[3]: https://rxjs.dev/
[4]: https://github.com/zenparsing/zen-observable
