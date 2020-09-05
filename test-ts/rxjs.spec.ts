import { Observable, of } from "rxjs"
import { assert, expect } from "chai"
import { map, catchError, toArray } from 'rxjs/operators';

import { intFlow, throwEvenFlow, collectInKotlin } from "kotlinx-coroutines-observable-js-test-kt"

describe('kotlinx.coroutines.observable', () => {
    it('intFlow returns [1,2,3]', done => {
        const observable = new Observable(intFlow);
        observable.pipe(toArray()).subscribe(ints => {
            expect(ints).to.deep.equal([1,2,3]);
            done();
        });
    });

    it('throwEvenFlow throws after [1]', done => {
        const observable = new Observable(throwEvenFlow);
        observable
            .pipe(
                catchError(err => {
                    expect(err).to
                        .be.an.instanceOf(Error)
                        .with.property('message', 'Only odd values allowed');
                    return of(-1);
                })
            )
            .pipe(toArray())
            .subscribe(ints => {
                expect(ints).to.deep.equal([1, -1]);
                done();
            })
    })

    it('collects rxjs observable in kotlin', async () => {
        const observable = of(1, 2, 3);
        const result = await collectInKotlin(observable)
        expect(result).to.deep.equal([1, 2, 3])
    });

    it('kotlin rethrows errors occuring when collecting rxjs observable', async () => {
        const observable = of(1, 2, 3).pipe(
            map(x => {
                if (x % 2 === 0) {
                    throw Error("Only odd values allowed");
                }
                return x;
            })
        );

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
})
