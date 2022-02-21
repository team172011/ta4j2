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
import org.ta4j.core.analysis.Returns;

import java.util.Collections;
import java.util.List;


/**
 * Expected Shortfall criterion.
 *
 * Measures the expected shortfall of the strategy log-return time-series.
 *
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Expected_shortfall">https://en.wikipedia.org/wiki/Expected_shortfall</a>
 *
 */
public class ExpectedShortfallCriterion extends AbstractAnalysisCriterion {
    /**
     * Confidence level as absolute value (e.g. 0.95)
     */
    private final Double confidence;

    /**
     * Constructor
     *
     * @param confidence the confidence level
     */
    public ExpectedShortfallCriterion(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public double calculate(BarSeries series, Position position) {
        if (position != null && position.getEntry() != null && position.getExit() != null) {
            Returns returns = new Returns(series, position, Returns.ReturnType.LOG);
            return calculateES(returns, confidence);
        }
        return 0d;
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        Returns returns = new Returns(series, tradingRecord, Returns.ReturnType.LOG);
        return calculateES(returns, confidence);
    }

    /**
     * Calculates the Expected Shortfall on the return series
     * 
     * @param returns    the corresponding returns
     * @param confidence the confidence level
     * @return the relative Expected Shortfall
     */
    private static Double calculateES(Returns returns, Double confidence) {
        // select non-NaN returns
        List<Double> returnRates = returns.getValues().subList(1, returns.getSize() + 1);
        double expectedShortfall = 0;
        if (!returnRates.isEmpty()) {
            // F(x_var) >= alpha (=1-confidence)
            int nInBody = (int) (returns.getSize() * confidence);
            int nInTail = returns.getSize() - nInBody;

            // calculate average tail loss
            Collections.sort(returnRates);
            List<Double> tailEvents = returnRates.subList(0, nInTail);
            double sum = 0;
            for (int i = 0; i < nInTail; i++) {
                sum = sum+(tailEvents.get(i));
            }
            expectedShortfall = sum / nInTail;

            // ES is non-positive
            if (expectedShortfall > 0) {
                expectedShortfall = 0;
            }
        }
        return expectedShortfall;
    }

    /** The higher the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1> (criterionValue2);
    }
}
