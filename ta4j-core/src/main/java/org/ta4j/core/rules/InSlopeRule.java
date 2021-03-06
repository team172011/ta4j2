/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Ta4j Organization & respective
 * authors (see AUTHORS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.ta4j.core.rules;

import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;

import static java.lang.Double.NaN;


/**
 * Indicator-in-slope rule.
 *
 * Satisfied when the difference of the value of the {@link Indicator indicator}
 * and the previous (n-th) value of the {@link Indicator indicator} is between
 * the values of maxSlope or/and minSlope. It can test both, positive and
 * negative slope.
 */
public class InSlopeRule extends AbstractRule {

    /** The actual indicator */
    private Indicator<Double> ref;
    /** The previous n-th value of ref */
    private PreviousValueIndicator prev;
    /** The minimum slope between ref and prev */
    private Double minSlope;
    /** The maximum slope between ref and prev */
    private Double maxSlope;

    /**
     * Constructor.
     *  @param ref      the reference indicator
     * @param minSlope minumum slope between reference and previous indicator
     */
    public InSlopeRule(Indicator<Double> ref, double minSlope) {
        this(ref, 1, minSlope, NaN);
    }

    /**
     * Constructor.
     *  @param ref      the reference indicator
     * @param minSlope minumum slope between value of reference and previous
     *                 indicator
     * @param maxSlope maximum slope between value of reference and previous
     */
    public InSlopeRule(Indicator<Double> ref, double minSlope, double maxSlope) {
        this(ref, 1, minSlope, maxSlope);
    }

    /**
     * Constructor.
     *  @param ref         the reference indicator
     * @param nthPrevious defines the previous n-th indicator
     * @param maxSlope    maximum slope between value of reference and previous
     */
    public InSlopeRule(Indicator<Double> ref, int nthPrevious, double maxSlope) {
        this(ref, nthPrevious, NaN, maxSlope);
    }

    /**
     * Constructor.
     *  @param ref         the reference indicator
     * @param nthPrevious defines the previous n-th indicator
     * @param minSlope    minumum slope between value of reference and previous
 *                    indicator
     * @param maxSlope    maximum slope between value of reference and previous
     */
    public InSlopeRule(Indicator<Double> ref, int nthPrevious, double minSlope, double maxSlope) {
        this.ref = ref;
        this.prev = new PreviousValueIndicator(ref, nthPrevious);
        this.minSlope = minSlope;
        this.maxSlope = maxSlope;
    }

    /** This rule does not use the {@code tradingRecord}. */
    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        NumericIndicator diff = NumericIndicator.of(ref).minus(prev);
        Double val = diff.getValue(index);
        boolean minSlopeSatisfied = minSlope.isNaN() || val >= minSlope;
        boolean maxSlopeSatisfied = maxSlope.isNaN() || val <= maxSlope;
        boolean isNaN = minSlope.isNaN() && maxSlope.isNaN();

        final boolean satisfied = minSlopeSatisfied && maxSlopeSatisfied && !isNaN;
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }
}
