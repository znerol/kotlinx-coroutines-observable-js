import { intFlow } from "kotlinx-coroutines-observable-js-test-ir"
import { Observer } from "rxjs"

describe('kotlinx.coroutines.observable JS/IR', () => {
    it('should work', done => {
        const observable = new Observable(intFlow);
        observable.pipe(toArray()).subscribe(ints => {
            expect(ints).toEqual([1,2,3]);
            done();
        });
    });
})
