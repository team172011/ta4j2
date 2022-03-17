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
package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;

import static java.lang.Double.NaN;

/**
 * Difference Change Indicator.
 *
 * Get the difference in percentage from the last time the threshold was
 * reached.
 *
 * Or if you don't pass the threshold you will always just get the difference
 * percentage from the precious value.
 *
 */
public class DifferencePercentageIndicator extends CachedIndicator<Double> {

    private final Indicator<Double> indicator;
    private final Double percentageThreshold;
    private Double lastNotification;

    public DifferencePercentageIndicator(Indicator<Double> indicator) {
        this(indicator, 0);
    }

    public DifferencePercentageIndicator(Indicator<Double> indicator, Number percentageThreshold) {
        this(indicator, percentageThreshold.doubleValue());
    }

    public DifferencePercentageIndicator(Indicator<Double> indicator, Double percentageThreshold) {
        super(indicator);
        this.indicator = indicator;
        this.percentageThreshold = percentageThreshold;

    }

    @Override
    protected Double calculate(int index) {
        Double value = indicator.getValue((index));
        if (lastNotification == null) {
            lastNotification = value;
            return NaN;
        }

        Double changeFraction = value / (lastNotification);
        Double changePercentage = fractionToPercentage(changeFraction);

        if (Math.abs(changePercentage) >= (percentageThreshold)) {
            lastNotification = value;
        }

        return changePercentage;
    }

    private Double fractionToPercentage(Double changeFraction) {
        return changeFraction * 100 - 100;
    }
}
