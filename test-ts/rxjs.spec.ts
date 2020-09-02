import { Observable, of } from "rxjs"
import { expect } from "chai"
import { catchError, toArray } from 'rxjs/operators';

import { intFlow, throwEvenFlow } from "kotlinx-coroutines-observable-js-test-kt"

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
})
