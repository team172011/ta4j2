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
package org.ta4j.core.criteria;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.CashFlow;


/**
 * Maximum drawdown criterion.
 *
 * @see <a href=
 *      "http://en.wikipedia.org/wiki/Drawdown_%28economics%29">https://en.wikipedia.org/wiki/Drawdown_(economics)</a>
 */
public class MaximumDrawdownCriterion extends AbstractAnalysisCriterion {

    @Override
    public double calculate(BarSeries series, Position position) {
        if (position != null && position.getEntry() != null && position.getExit() != null) {
            CashFlow cashFlow = new CashFlow(series, position);
            return calculateMaximumDrawdown(series, cashFlow);
        }
        return 0d;
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        CashFlow cashFlow = new CashFlow(series, tradingRecord);
        return calculateMaximumDrawdown(series, cashFlow);
    }

    /** The lower the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1 < (criterionValue2);
    }

    /**
     * Calculates the maximum drawdown from a cash flow over a series.
     *
     * @param series   the bar series
     * @param cashFlow the cash flow
     * @return the maximum drawdown from a cash flow over a series
     */
    private Double calculateMaximumDrawdown(BarSeries series, CashFlow cashFlow) {
        double maximumDrawdown = 0d;
        double maxPeak = 0d;
        if (!series.isEmpty()) {
            // The series is not empty
            for (int i = series.getBeginIndex(); i <= series.getEndIndex(); i++) {
                double value = cashFlow.getValue(i);
                if (value > maxPeak ) {
                    maxPeak = value;
                }

                double drawdown = (maxPeak - value) / maxPeak;
                if (drawdown > maximumDrawdown) {
                    maximumDrawdown = drawdown;
                }
            }
        }
        return maximumDrawdown;
    }
}
