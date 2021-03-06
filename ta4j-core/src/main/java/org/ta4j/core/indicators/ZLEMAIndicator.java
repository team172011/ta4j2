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
package org.ta4j.core.indicators;

import org.ta4j.core.Indicator;


/**
 * Zero-lag exponential moving average indicator.
 *
 * @see <a href=
 *      "http://www.fmlabs.com/reference/default.htm?url=ZeroLagExpMA.htm">
 *      http://www.fmlabs.com/reference/default.htm?url=ZeroLagExpMA.htm</a>
 */
public class ZLEMAIndicator extends RecursiveCachedIndicator<Double> {

    private final Indicator<Double> indicator;
    private final int barCount;
    private final Double k;
    private final int lag;

    public ZLEMAIndicator(Indicator<Double> indicator, int barCount) {
        super(indicator);
        this.indicator = indicator;
        this.barCount = barCount;
        k = 2 / ((double) barCount + 1);
        lag = (barCount - 1) / 2;
    }

    @Override
    protected Double calculate(int index) {
        if (index + 1 < barCount) {
            // Starting point of the ZLEMA
            return new SMAIndicator(indicator, barCount).getValue(index);
        }
        if (index == 0) {
            // If the barCount is bigger than the indicator's value count
            return indicator.getValue(0);
        }
        double zlemaPrev = getValue(index - 1);
        return k*(2 * indicator.getValue(index) - indicator.getValue(index - lag)) + ((1 - k) * zlemaPrev);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }
}
