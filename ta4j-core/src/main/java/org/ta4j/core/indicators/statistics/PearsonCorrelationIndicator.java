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
package org.ta4j.core.indicators.statistics;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RecursiveCachedIndicator;

import static java.lang.Double.NaN;


/**
 * Indicator-Pearson-Correlation
 *
 * @see <a href=
 *      "http://www.statisticshowto.com/probability-and-statistics/correlation-coefficient-formula/">
 *      http://www.statisticshowto.com/probability-and-statistics/correlation-coefficient-formula/</a>
 */
public class PearsonCorrelationIndicator extends RecursiveCachedIndicator<Double> {

    private final Indicator<Double> indicator1;
    private final Indicator<Double> indicator2;
    private final int barCount;

    /**
     * Constructor.
     *
     * @param indicator1 the first indicator
     * @param indicator2 the second indicator
     * @param barCount   the time frame
     */
    public PearsonCorrelationIndicator(Indicator<Double> indicator1, Indicator<Double> indicator2, int barCount) {
        super(indicator1);
        this.indicator1 = indicator1;
        this.indicator2 = indicator2;
        this.barCount = barCount;
    }

    @Override
    protected Double calculate(int index) {

        double n = barCount;

        double Sx =0;
        double Sy =0;
        double Sxx =0;
        double Syy =0;
        double Sxy =0;

        for (int i = Math.max(getBarSeries().getBeginIndex(), index - barCount + 1); i <= index; i++) {

            Double x = indicator1.getValue(i);
            Double y = indicator2.getValue(i);

            Sx = Sx+(x);
            Sy = Sy+(y);
            Sxy = Sxy+(x*(y));
            Sxx = Sxx+(x*(x));
            Syy = Syy+(y*(y));
        }

        // (n * Sxx - Sx * Sx) * (n * Syy - Sy * Sy)
        Double toSqrt = (n*(Sxx) - (Sx*(Sx)))
                *(n*(Syy) - (Sy*(Sy)));

        if (toSqrt> ((0))) {
            // pearson = (n * Sxy - Sx * Sy) / sqrt((n * Sxx - Sx * Sx) * (n * Syy - Sy *
            // Sy))
            return (n*(Sxy) - (Sx*(Sy))) / Math.sqrt(toSqrt);
        }

        return NaN;
    }
}
