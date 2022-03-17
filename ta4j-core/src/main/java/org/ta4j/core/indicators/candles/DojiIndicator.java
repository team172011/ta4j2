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
package org.ta4j.core.indicators.candles;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.numeric.NumericIndicator;

/**
 * Doji indicator.
 *
 * A candle/bar is considered Doji if its body height is lower than the average
 * multiplied by a factor.
 *
 * @see <a href=
 *      "http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks#doji">
 *      http://stockcharts.com/school/doku.php?id=chart_school:chart_analysis:introduction_to_candlesticks#doji</a>
 */
public class DojiIndicator extends CachedIndicator<Boolean> {

    /**
     * Body height
     */
    private final Indicator<Double> bodyHeightInd;
    /**
     * Average body height
     */
    private final SMAIndicator averageBodyHeightInd;

    private final Double factor;

    /**
     * Constructor.
     *
     * @param series     the bar series
     * @param barCount   the number of bars used to calculate the average body
     *                   height
     * @param bodyFactor the factor used when checking if a candle is Doji
     */
    public DojiIndicator(BarSeries series, int barCount, Double bodyFactor) {
        super(series);
        bodyHeightInd = NumericIndicator.of(new RealBodyIndicator(series)).abs();
        averageBodyHeightInd = new SMAIndicator(bodyHeightInd, barCount);
        factor = (bodyFactor);
    }

    @Override
    protected Boolean calculate(int index) {
        if (index < 1) {
            return bodyHeightInd.getValue(index) == 0;
        }
        double averageBodyHeight = averageBodyHeightInd.getValue(index - 1);
        double currentBodyHeight = bodyHeightInd.getValue(index);
        return currentBodyHeight < averageBodyHeight * factor;
    }
}
