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
package org.ta4j.core.criteria.pnl;

import org.junit.Test;
import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.criteria.AbstractCriterionTest;
import org.ta4j.core.criteria.OpenedPositionUtils;
import org.ta4j.core.mocks.MockBarSeries;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class ProfitLossPercentageCriterionTest extends AbstractCriterionTest {
    
    @Test
    public void calculateWithWinningLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(2, series),
                Trade.buyAt(3, series), Trade.sellAt(5, series));
        AnalysisCriterion profit = new ProfitLossPercentageCriterion();
        assertEquals(10 + 5, profit.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithLosingLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));

        AnalysisCriterion profit = new ProfitLossPercentageCriterion();
        assertEquals(-5 + -30, profit.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithOneWinningAndOneLosingLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 195, 100, 80, 85, 70);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));

        AnalysisCriterion profit = new ProfitLossPercentageCriterion();
        assertEquals(95 + -30, profit.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithWinningShortPositions() {
        MockBarSeries series = new MockBarSeries(100, 90, 100, 95, 95, 100);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(3, series));
        AnalysisCriterion profit = new ProfitLossPercentageCriterion();
        assertEquals(10 + 5, profit.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithLosingShortPositions() {
        MockBarSeries series = new MockBarSeries(100, 110, 100, 105, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(3, series));
        AnalysisCriterion profit = new ProfitLossPercentageCriterion();
        assertEquals(-10 - 5, profit.calculate(series, tradingRecord));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new ProfitLossPercentageCriterion();
        assertTrue(criterion.betterThan((50), (45)));
        assertFalse(criterion.betterThan((45), (50)));
    }

    @Test
    public void testCalculateOneOpenPositionShouldReturnZero() {
        new OpenedPositionUtils().testCalculateOneOpenPositionShouldReturnExpectedValue(new ProfitLossPercentageCriterion(), 0);
    }
}
