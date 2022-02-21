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

import org.junit.Test;
import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.mocks.MockBarSeries;

public class ExpectancyCriterionTest extends AbstractCriterionTest {

    @Test
    public void calculateOnlyWithProfitPositions() {
        MockBarSeries series = new MockBarSeries(100, 110, 120, 130, 150, 160);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(2, series),
                Trade.buyAt(3, series), Trade.sellAt(5, series));

        AnalysisCriterion avgLoss = new ExpectancyCriterion();
        assertEquals(0, avgLoss.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithMixedPositions() {
        MockBarSeries series = new MockBarSeries(100, 110, 80, 130, 150, 160);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(2, series),
                Trade.buyAt(3, series), Trade.sellAt(5, series));

        AnalysisCriterion avgLoss = new ExpectancyCriterion();
        assertEquals(-1.25, avgLoss.calculate(series, tradingRecord));
    }

    @Test
    public void calculateOnlyWithLossPositions() {
        MockBarSeries series = new MockBarSeries(100, 95, 80, 70, 60, 50);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));

        AnalysisCriterion avgLoss = new ExpectancyCriterion();
        assertEquals(0, avgLoss.calculate(series, tradingRecord));
    }

    @Test
    public void calculateProfitWithShortPositions() {
        MockBarSeries series = new MockBarSeries(160, 140, 120, 100, 80, 60);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(5, series));

        AnalysisCriterion avgLoss = new ExpectancyCriterion();
        assertEquals(0, avgLoss.calculate(series, tradingRecord));
    }

    @Test
    public void calculateProfitWithMixedShortPositions() {
        MockBarSeries series = new MockBarSeries(160, 200, 120, 100, 80, 60);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(5, series));

        AnalysisCriterion avgLoss = new ExpectancyCriterion();
        assertEquals(-1.25, avgLoss.calculate(series, tradingRecord));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new ExpectancyCriterion();
        assertTrue(criterion.betterThan((2.0), (1.5)));
        assertFalse(criterion.betterThan((1.5), (2.0)));
    }

    @Test
    public void testCalculateOneOpenPositionShouldReturnZero() {
        new OpenedPositionUtils().testCalculateOneOpenPositionShouldReturnExpectedValue(new ExpectancyCriterion(), 0);
    }

}