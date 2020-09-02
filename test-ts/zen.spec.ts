import Observable from "zen-observable"
import { expect } from "chai"

import { intFlow, throwEvenFlow } from "kotlinx-coroutines-observable-js-test-kt"

describe('kotlinx.coroutines.observable', () => {
    it('intFlow returns [1,2,3]', done => {
        const observable = new Observable(intFlow);
        observable
            .reduce((carry, val) => carry.concat([val]), [])
            .subscribe(ints => {
                expect(ints).to.deep.equal([1,2,3]);
                done();
            });
    });

    it('throwEvenFlow throws after [1]', done => {
        const observable = new Observable(throwEvenFlow);
        let valsCount = 0;
        observable.subscribe(
            value => {
                expect(value).to.equal(1);
                valsCount++;
            },
            err => {
                expect(valsCount).to.equal(1);
                expect(err).to.exist
                    .and.be.an.instanceOf(Error)
                    .with.property('message', 'Only odd values allowed');
                done();
            });
    });
});
