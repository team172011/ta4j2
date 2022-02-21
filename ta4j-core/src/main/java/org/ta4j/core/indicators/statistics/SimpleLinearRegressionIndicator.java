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
import org.ta4j.core.indicators.CachedIndicator;

import static java.lang.Double.NaN;

/**
 * Simple linear regression indicator.
 *
 * A moving (i.e. over the time frame) simple linear regression (least squares).
 * y = slope * x + intercept See also:
 * http://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
 */
public class SimpleLinearRegressionIndicator extends CachedIndicator<Double> {

    /**
     * The type for the outcome of the {@link SimpleLinearRegressionIndicator}
     */
    public enum  SimpleLinearRegressionType {
        Y, SLOPE, INTERCEPT
    }

    private final Indicator<Double> indicator;
    private final int barCount;
    private double slope;
    private double intercept;
    private final SimpleLinearRegressionType type;

    /**
     * Constructor for the y-values of the formula (y = slope * x + intercept).
     *
     * @param indicator the indicator for the x-values of the formula.
     * @param barCount  the time frame
     */
    public SimpleLinearRegressionIndicator(Indicator<Double> indicator, int barCount) {
        this(indicator, barCount, SimpleLinearRegressionType.Y);
    }

    /**
     * Constructor.
     *
     * @param indicator the indicator for the x-values of the formula.
     * @param barCount  the time frame
     * @param type      the type of the outcome value (y, slope, intercept)
     */
    public SimpleLinearRegressionIndicator(Indicator<Double> indicator, int barCount, SimpleLinearRegressionType type) {
        super(indicator);
        this.indicator = indicator;
        this.barCount = barCount;
        this.type = type;
    }

    @Override
    protected Double calculate(int index) {
        final int startIndex = Math.max(0, index - barCount + 1);
        if (index - startIndex + 1 < 2) {
            // Not enough observations to compute a regression line
            return NaN;
        }
        calculateRegressionLine(startIndex, index);

        if (type == SimpleLinearRegressionType.SLOPE) {
            return slope;
        }

        if (type == SimpleLinearRegressionType.INTERCEPT) {
            return intercept;
        }

        return slope * index + intercept;
    }

    /**
     * Calculates the regression line.
     *
     * @param startIndex the start index (inclusive) in the bar series
     * @param endIndex   the end index (inclusive) in the bar series
     */
    private void calculateRegressionLine(int startIndex, int endIndex) {
        // First pass: compute xBar and yBar
        double sumX = 0;
        double sumY = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            sumX = sumX + i;
            sumY = sumY + indicator.getValue(i);
        }
        double nbObservations = endIndex - startIndex + 1;
        double xBar = sumX / nbObservations;
        double yBar = sumY / nbObservations;

        // Second pass: compute slope and intercept
        double xxBar = 0;
        double xyBar = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            double dX = i - xBar;
            double dY = indicator.getValue(i) - yBar;
            xxBar = xxBar + dX * dX;
            xyBar = xyBar + dX * dY;
        }

        slope = xyBar / xxBar;
        intercept = yBar - slope * xBar;
    }
}
