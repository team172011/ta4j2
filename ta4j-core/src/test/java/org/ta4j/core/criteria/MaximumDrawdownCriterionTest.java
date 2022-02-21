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



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class MaximumDrawdownCriterionTest extends AbstractCriterionTest {

    @Test
    public void calculateWithNoTrades() {
        MockBarSeries series = new MockBarSeries(1, 2, 3, 6, 5, 20, 3);
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();

        assertEquals(0d, mdd.calculate(series, new BaseTradingRecord()));
    }

    @Test
    public void calculateWithOnlyGains() {
        MockBarSeries series = new MockBarSeries(1, 2, 3, 6, 8, 20, 3);
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));

        assertEquals(0d, mdd.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithGainsAndLosses() {
        MockBarSeries series = new MockBarSeries(1, 2, 3, 6, 5, 20, 3);
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(3, series), Trade.sellAt(4, series), Trade.buyAt(5, series), Trade.sellAt(6, series));

        assertEquals(.875d, mdd.calculate(series, tradingRecord));

    }

    @Test
    public void calculateWithNullSeriesSizeShouldreturn0() {
        MockBarSeries series = new MockBarSeries(new double[] {});
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();
        assertEquals(0d, mdd.calculate(series, new BaseTradingRecord()));
    }

    @Test
    public void withTradesThatSellBeforeBuying() {
        MockBarSeries series = new MockBarSeries(2, 1, 3, 5, 6, 3, 20);
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(3, series), Trade.sellAt(4, series), Trade.sellAt(5, series), Trade.buyAt(6, series));
        assertEquals(3.8d, mdd.calculate(series, tradingRecord));
    }

    @Test
    public void withSimpleTrades() {
        MockBarSeries series = new MockBarSeries(1, 10, 5, 6, 1);
        AnalysisCriterion mdd = new MaximumDrawdownCriterion();
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(1, series), Trade.sellAt(2, series), Trade.buyAt(2, series), Trade.sellAt(3, series),
                Trade.buyAt(3, series), Trade.sellAt(4, series));
        assertEquals(.9d, mdd.calculate(series, tradingRecord));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new MaximumDrawdownCriterion();
        assertTrue(criterion.betterThan((0.9), (1.5)));
        assertFalse(criterion.betterThan((1.2), (0.4)));
    }
}
