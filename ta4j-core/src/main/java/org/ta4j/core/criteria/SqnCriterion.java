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

import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.criteria.helpers.StandardDeviationCriterion;
import org.ta4j.core.criteria.pnl.ProfitLossCriterion;


/**
 * The SQN ("System Quality Number") Criterion.
 * 
 * @see <a href=
 *      "https://indextrader.com.au/van-tharps-sqn/">https://indextrader.com.au/van-tharps-sqn/</a>
 */
public class SqnCriterion extends AbstractAnalysisCriterion {

    private final AnalysisCriterion criterion;
    private final StandardDeviationCriterion standardDeviationCriterion;
    private final NumberOfPositionsCriterion numberOfPositionsCriterion = new NumberOfPositionsCriterion();

    /**
     * The number to be used for the part of <code>√(numberOfPositions)</code>
     * within the SQN-Formula when there are more than 100 trades. If this value is
     * <code>null</code>, then the number of positions calculated by
     * {@link #numberOfPositionsCriterion} is used instead.
     */
    private final Integer nPositions;

    /**
     * Constructor.
     * 
     * <p>
     * Uses ProfitLossCriterion for {@link #criterion}.
     */
    public SqnCriterion() {
        this(new ProfitLossCriterion());
    }

    /**
     * Constructor.
     * 
     * @param criterion the Criterion (e.g. ProfitLossCriterion or
     *                  ExpectancyCriterion)
     */
    public SqnCriterion(AnalysisCriterion criterion) {
        this(criterion, null);
    }

    /**
     * Constructor.
     * 
     * @param criterion  the Criterion (e.g. ProfitLossCriterion or
     *                   ExpectancyCriterion)
     * @param nPositions the {@link #nPositions} (optional)
     */
    public SqnCriterion(AnalysisCriterion criterion, Integer nPositions) {
        this.criterion = criterion;
        this.nPositions = nPositions;
        this.standardDeviationCriterion = new StandardDeviationCriterion(criterion);
    }

    @Override
    public double calculate(BarSeries series, Position position) {
        Double numberOfPositions = numberOfPositionsCriterion.calculate(series, position);
        Double pnl = criterion.calculate(series, position);
        Double avgPnl = pnl / (numberOfPositions);
        Double stdDevPnl = standardDeviationCriterion.calculate(series, position);
        if (stdDevPnl == 0) {
            return 0d;
        }
        // SQN = (Average (PnL) / StdDev(PnL)) * SquareRoot(NumberOfTrades)
        return avgPnl / (stdDevPnl)*(Math.sqrt(numberOfPositions));
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        if (tradingRecord.getPositions().isEmpty())
            return 0d;
        double numberOfPositions = numberOfPositionsCriterion.calculate(series, tradingRecord);
        double pnl = criterion.calculate(series, tradingRecord);
        double avgPnl = pnl / (numberOfPositions);
        double stdDevPnl = standardDeviationCriterion.calculate(series, tradingRecord);
        if (stdDevPnl == 0) {
            return 0d;
        }
        if (nPositions != null && numberOfPositions> (100)) {
            numberOfPositions = nPositions;
        }
        // SQN = (Average (PnL) / StdDev(PnL)) * SquareRoot(NumberOfTrades)
        return avgPnl / (stdDevPnl)*(Math.sqrt(numberOfPositions));
    }

    /** The higher the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1> (criterionValue2);
    }

}
