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

import org.ta4j.core.BarSeries;

import static java.lang.Double.NaN;

/**
 * The Class RandomWalkIndexLowIndicator.
 *
 * @see <a href=
 *      "http://https://rtmath.net/helpFinAnalysis/html/934563a8-9171-42d2-8444-486691234b1d.html">Source
 *      of formular</a>
 */
public class RWILowIndicator extends CachedIndicator<Double> {

    private final int barCount;

    /**
     * Constructor.
     *
     * @param series   the series
     * @param barCount the time frame
     */
    public RWILowIndicator(BarSeries series, int barCount) {
        super(series);
        this.barCount = barCount;
    }

    @Override
    protected Double calculate(int index) {
        if (index - barCount + 1 < getBarSeries().getBeginIndex()) {
            return NaN;
        }

        double minRWIL = 0;
        for (int n = 2; n <= barCount; n++) {
            minRWIL = Math.max(minRWIL, calcRWIHFor(index, n));
        }

        return minRWIL;
    }

    private Double calcRWIHFor(final int index, final int n) {
        BarSeries series = getBarSeries();
        double low = series.getBar(index).getLowPrice();
        double highN = series.getBar(index + 1 - n).getHighPrice();
        double atrN = new ATRIndicator(series, n).getValue(index);
        double sqrtN = Math.sqrt(n);

        return highN - (low) / (atrN * (sqrtN));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }
}
