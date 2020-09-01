import { Observable } from "rxjs"
import { expect } from "chai"
import { toArray } from 'rxjs/operators';

import { intFlow } from "kotlinx-coroutines-observable-js-test-legacy"

describe('kotlinx.coroutines.observable JS/Legacy', () => {
    it('intFlow returns [1,2,3]', done => {
        const observable = new Observable(intFlow);
        observable.pipe(toArray()).subscribe(ints => {
            expect(ints).to.deep.equal([1,2,3]);
            done();
        });
    });
})
