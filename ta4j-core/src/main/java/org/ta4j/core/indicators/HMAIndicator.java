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
import org.ta4j.core.indicators.numeric.NumericIndicator;


/**
 * Hull moving average (HMA) indicator.
 *
 * @see <a href="http://alanhull.com/hull-moving-average">
 *      http://alanhull.com/hull-moving-average</a>
 */
public class HMAIndicator extends CachedIndicator<Double> {

    private final int barCount;
    private final WMAIndicator sqrtWma;

    public HMAIndicator(Indicator<Double> indicator, int barCount) {
        super(indicator);
        this.barCount = barCount;

        WMAIndicator halfWma = new WMAIndicator(indicator, barCount / 2);
        WMAIndicator origWma = new WMAIndicator(indicator, barCount);

        Indicator<Double> indicatorForSqrtWma = NumericIndicator.of(NumericIndicator.of(halfWma).multipliedBy(2).minus(origWma));
        sqrtWma = new WMAIndicator(indicatorForSqrtWma, (int) Math.sqrt(barCount));
    }

    @Override
    protected Double calculate(int index) {
        return sqrtWma.getValue(index);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }

}
