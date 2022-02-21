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
import org.ta4j.core.*;
import org.ta4j.core.criteria.AbstractCriterionTest;
import org.ta4j.core.criteria.OpenedPositionUtils;
import org.ta4j.core.mocks.MockBarSeries;

public class GrossReturnCriterionTest extends AbstractCriterionTest {

    @Test
    public void calculateWithWinningLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(2, series),
                Trade.buyAt(3, series), Trade.sellAt(5, series));

        AnalysisCriterion ret = new GrossReturnCriterion();
        assertEquals(1.10 * 1.05, ret.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithLosingLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));

        AnalysisCriterion ret = new GrossReturnCriterion();
        assertEquals(0.95 * 0.7, ret.calculate(series, tradingRecord));
    }

    @Test
    public void calculateReturnWithWinningShortPositions() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(5, series));

        AnalysisCriterion ret = new GrossReturnCriterion();
        assertEquals(1.05 * 1.30, ret.calculate(series, tradingRecord));
    }

    @Test
    public void calculateReturnWithLosingShortPositions() {
        MockBarSeries series = new MockBarSeries(100, 105, 100, 80, 85, 130);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(5, series));

        AnalysisCriterion ret = new GrossReturnCriterion();
        assertEquals(0.95 * 0.70, ret.calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithNoPositionsShouldReturn1() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);

        AnalysisCriterion ret = new GrossReturnCriterion();
        assertEquals(1d, ret.calculate(series, new BaseTradingRecord()));
    }

    @Test
    public void calculateWithOpenedPositionShouldReturn1() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);
        AnalysisCriterion ret = new GrossReturnCriterion();
        Position position = new Position();
        assertEquals(1d, ret.calculate(series, position));
        position.operate(0);
        assertEquals(1d, ret.calculate(series, position));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new GrossReturnCriterion();
        assertTrue(criterion.betterThan((2.0), (1.5)));
        assertFalse(criterion.betterThan((1.5), (2.0)));
    }

    @Test
    public void testCalculateOneOpenPositionShouldReturnOne() {
        new OpenedPositionUtils().testCalculateOneOpenPositionShouldReturnExpectedValue(new GrossReturnCriterion(), 1);
    }
}
