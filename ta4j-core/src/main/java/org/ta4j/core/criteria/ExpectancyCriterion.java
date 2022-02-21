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
import org.ta4j.core.criteria.pnl.ProfitLossRatioCriterion;


/**
 * Expectancy criterion (Kelly Criterion).
 *
 * Measures the positive or negative expectancy. The higher the positive number,
 * the better a winning expectation. A negative number means there is only
 * losing expectations.
 *
 * @see <a href=
 *      "https://www.straightforex.com/advanced-forex-course/money-management/two-important-things-to-be-considered/">https://www.straightforex.com/advanced-forex-course/money-management/two-important-things-to-be-considered/</a>
 * 
 */
public class ExpectancyCriterion extends AbstractAnalysisCriterion {

    private final ProfitLossRatioCriterion profitLossRatioCriterion = new ProfitLossRatioCriterion();
    private final NumberOfPositionsCriterion numberOfPositionsCriterion = new NumberOfPositionsCriterion();
    private final NumberOfWinningPositionsCriterion numberOfWinningPositionsCriterion = new NumberOfWinningPositionsCriterion();

    @Override
    public double calculate(BarSeries series, Position position) {
        Double profitLossRatio = profitLossRatioCriterion.calculate(series, position);
        Double numberOfPositions = numberOfPositionsCriterion.calculate(series, position);
        Double numberOfWinningPositions = numberOfWinningPositionsCriterion.calculate(series, position);
        return calculate(series, profitLossRatio, numberOfWinningPositions, numberOfPositions);
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        Double profitLossRatio = profitLossRatioCriterion.calculate(series, tradingRecord);
        Double numberOfPositions = numberOfPositionsCriterion.calculate(series, tradingRecord);
        Double numberOfWinningPositions = numberOfWinningPositionsCriterion.calculate(series, tradingRecord);
        return calculate(series, profitLossRatio, numberOfWinningPositions, numberOfPositions);
    }

    /** The higher the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1> (criterionValue2);
    }

    private Double calculate(BarSeries series, Double profitLossRatio, Double numberOfWinningPositions,
            Double numberOfAllPositions) {
        if (numberOfAllPositions == 0|| profitLossRatio == 0) {
            return 0d;
        }
        // Expectancy = (1 + AW/AL) * (ProbabilityToWinOnePosition - 1)
        Double probabiltyToWinOnePosition = numberOfWinningPositions / (numberOfAllPositions);
        return (1+(profitLossRatio))*((probabiltyToWinOnePosition) - 1);
    }

}
