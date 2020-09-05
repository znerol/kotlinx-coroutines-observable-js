import Observable from "zen-observable"
import { assert, expect } from "chai"

import { intFlow, throwEvenFlow, collectInKotlin } from "kotlinx-coroutines-observable-js-test-kt"

describe('kotlinx.coroutines.observable', () => {
    it('intFlow returns [1,2,3]', done => {
        const observable = new Observable(intFlow);
        observable
            .reduce((carry: number[], val: number) => carry.concat([val]), [])
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

    it('collects zen-observable in kotlin', async () => {
        const observable = Observable.of(1, 2, 3);
        const result = await collectInKotlin(observable)
        expect(result).to.deep.equal([1, 2, 3])
    });

    it('kotlin rethrows errors occuring when collecting zen-observable', async () => {
        const observable = Observable.of(1, 2, 3).map(x => {
            if (x % 2 === 0) {
                throw Error("Only odd values allowed");
            }
            return x;
        });

        try {
            await collectInKotlin(observable)
            assert.fail("collectInKotlin failed to throw");
        }
        catch(err) {
            expect(err).to
                .be.an.instanceOf(Error)
                .with.property('message', 'Only odd values allowed');
        }
    });
});
