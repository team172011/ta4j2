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
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.*;

/**
 * Stochastic oscillator K.
 *
 * Receives barSeries and barCount and calculates the
 * StochasticOscillatorKIndicator over ClosePriceIndicator, or receives an
 * indicator, HighPriceIndicator and LowPriceIndicator and returns
 * StochasticOsiclatorK over this indicator.
 */
public class StochasticOscillatorKIndicator extends CachedIndicator<Double> {
    private final Indicator<Double> indicator;

    private final int barCount;

    private HighPriceIndicator highPriceIndicator;

    private LowPriceIndicator lowPriceIndicator;

    public StochasticOscillatorKIndicator(BarSeries barSeries, int barCount) {
        this(new ClosePriceIndicator(barSeries), barCount, new HighPriceIndicator(barSeries),
                new LowPriceIndicator(barSeries));
    }

    public StochasticOscillatorKIndicator(Indicator<Double> indicator, int barCount,
            HighPriceIndicator highPriceIndicator, LowPriceIndicator lowPriceIndicator) {
        super(indicator);
        this.indicator = indicator;
        this.barCount = barCount;
        this.highPriceIndicator = highPriceIndicator;
        this.lowPriceIndicator = lowPriceIndicator;
    }

    @Override
    protected Double calculate(int index) {
        HighestValueIndicator highestHigh = new HighestValueIndicator(highPriceIndicator, barCount);
        LowestValueIndicator lowestMin = new LowestValueIndicator(lowPriceIndicator, barCount);

        double highestHighPrice = highestHigh.getValue(index);
        double lowestLowPrice = lowestMin.getValue(index);

        return ((indicator.getValue(index) - lowestLowPrice) / (highestHighPrice - lowestLowPrice)) * 100;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " barCount: " + barCount;
    }
}
