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
import org.ta4j.core.criteria.pnl.GrossReturnCriterion;


/**
 * Calculates the average return per bar criterion.
 *
 * <p>
 * The {@link GrossReturnCriterion gross return} raised to the power of 1
 * divided by {@link NumberOfBarsCriterion number of bars}.
 */
public class AverageReturnPerBarCriterion extends AbstractAnalysisCriterion {

    private final GrossReturnCriterion grossReturn = new GrossReturnCriterion();
    private final NumberOfBarsCriterion numberOfBars = new NumberOfBarsCriterion();

    @Override
    public double calculate(BarSeries series, Position position) {
        double bars = numberOfBars.calculate(series, position);
        if (bars==0) {
            return 1d;
        }

        return Math.pow(grossReturn.calculate(series, position),1/ bars);
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        double bars = numberOfBars.calculate(series, tradingRecord);
        if (bars == 0) {
            return 1d;
        }

        return Math.pow(grossReturn.calculate(series, tradingRecord), 1 / bars);
    }

    /** The higher the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1> (criterionValue2);
    }
}
