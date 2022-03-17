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
package org.ta4j.core.indicators.aroon;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;

import java.util.Objects;

import static java.lang.Double.NaN;

/**
 * Aroon down indicator.
 *
 * @see <a href=
 *      "http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:aroon">chart_school:technical_indicators:aroon</a>
 */
public class AroonDownIndicator extends CachedIndicator<Double> {

    private final int barCount;
    private final LowestValueIndicator lowestLowPriceIndicator;
    private final Indicator<Double> lowPriceIndicator;

    /**
     * Constructor.
     *
     * @param lowPriceIndicator the indicator for the low price (default
     *                          {@link LowPriceIndicator})
     * @param barCount          the time frame
     */
    public AroonDownIndicator(Indicator<Double> lowPriceIndicator, int barCount) {
        super(lowPriceIndicator);
        this.barCount = barCount;
        this.lowPriceIndicator = lowPriceIndicator;
        // + 1 needed for last possible iteration in loop
        this.lowestLowPriceIndicator = new LowestValueIndicator(lowPriceIndicator, barCount + 1);
    }

    /**
     * Default Constructor that is using the low price
     *
     * @param series   the bar series
     * @param barCount the time frame
     */
    public AroonDownIndicator(BarSeries series, int barCount) {
        this(new LowPriceIndicator(series), barCount);
    }

    @Override
    protected Double calculate(int index) {
        if (Double.isNaN(getBarSeries().getBar(index).getLowPrice()))
            return NaN;

        // Getting the Doubleber of bars since the lowest close price
        int endIndex = Math.max(0, index - barCount);
        int nbBars = 0;
        for (int i = index; i > endIndex; i--) {
            if (Objects.equals(lowPriceIndicator.getValue(i), lowestLowPriceIndicator.getValue(index))) {
                break;
            }
            nbBars++;
        }

        return ((((double) barCount - nbBars) / barCount) * 100);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }
}
