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

import org.ta4j.core.*;


/**
 * Versus "buy and hold" criterion.
 *
 * Compares the value of a provided {@link AnalysisCriterion criterion} with the
 * value of a "buy and hold".
 */
public class VersusBuyAndHoldCriterion extends AbstractAnalysisCriterion {

    private final AnalysisCriterion criterion;

    /**
     * Constructor.
     * 
     * @param criterion an analysis criterion to be compared
     */
    public VersusBuyAndHoldCriterion(AnalysisCriterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public double calculate(BarSeries series, Position position) {
        TradingRecord fakeRecord = createBuyAndHoldTradingRecord(series);
        return criterion.calculate(series, position) / (criterion.calculate(series, fakeRecord));
    }

    @Override
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        TradingRecord fakeRecord = createBuyAndHoldTradingRecord(series);
        return criterion.calculate(series, tradingRecord) / (criterion.calculate(series, fakeRecord));
    }

    /** The higher the criterion value, the better. */
    @Override
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1> (criterionValue2);
    }

    private TradingRecord createBuyAndHoldTradingRecord(BarSeries series) {
        return createBuyAndHoldTradingRecord(series, series.getBeginIndex(), series.getEndIndex());
    }

    private TradingRecord createBuyAndHoldTradingRecord(BarSeries series, int beginIndex, int endIndex) {
        TradingRecord fakeRecord = new BaseTradingRecord();
        fakeRecord.enter(beginIndex, series.getBar(beginIndex).getClosePrice(), 1d);
        fakeRecord.exit(endIndex, series.getBar(endIndex).getClosePrice(), 1d);
        return fakeRecord;
    }
}
