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

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.*;
import org.ta4j.core.mocks.MockBarSeries;

public class ReturnOverMaxDrawdownCriterionTest extends AbstractCriterionTest {

    private AnalysisCriterion rrc;

    @Before
    public void setUp() {
        this.rrc = new ReturnOverMaxDrawdownCriterion();
    }

    @Test
    public void rewardRiskRatioCriterion() {
        MockBarSeries series = new MockBarSeries(100, 105, 95, 100, 90, 95, 80, 120);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(4, series), Trade.buyAt(5, series), Trade.sellAt(7, series));

        double totalProfit = (105d / 100) * (90d / 95d) * (120d / 95);
        double peak = (105d / 100) * (100d / 95);
        double low = (105d / 100) * (90d / 95) * (80d / 95);

        assertEquals(totalProfit / ((peak - low) / peak), rrc.calculate(series, tradingRecord));
    }

    @Test
    public void rewardRiskRatioCriterionOnlyWithGain() {
        MockBarSeries series = new MockBarSeries(1, 2, 3, 6, 8, 20, 3);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(5, series));
        assertTrue(Double.isNaN(rrc.calculate(series, tradingRecord)));
    }

    @Test
    public void rewardRiskRatioCriterionWithNoPositions() {
        MockBarSeries series = new MockBarSeries(1, 2, 3, 6, 8, 20, 3);
        assertTrue(Double.isNaN(rrc.calculate(series, new BaseTradingRecord())));
    }

    @Test
    public void withOnePosition() {
        MockBarSeries series = new MockBarSeries(100, 95, 95, 100, 90, 95, 80, 120);
        Position position = new Position(Trade.buyAt(0, series), Trade.sellAt(1, series));

        AnalysisCriterion ratioCriterion = new ReturnOverMaxDrawdownCriterion();
        assertEquals((95d / 100) / ((1d - 0.95d)), ratioCriterion.calculate(series, position));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new ReturnOverMaxDrawdownCriterion();
        assertTrue(criterion.betterThan((3.5), (2.2)));
        assertFalse(criterion.betterThan((1.5), (2.7)));
    }

    @Test
    public void testNoDrawDownForTradingRecord() {
        final MockBarSeries series = new MockBarSeries(100, 105, 95, 100, 90, 95, 80, 120);
        final TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(1, series),
                Trade.buyAt(2, series), Trade.sellAt(3, series));

        final Double result = rrc.calculate(series, tradingRecord);

        assertEquals(Double.NaN, result);
    }

    @Test
    public void testNoDrawDownForPosition() {
        final MockBarSeries series = new MockBarSeries(100, 105, 95, 100, 90, 95, 80, 120);
        final Position position = new Position(Trade.buyAt(0, series), Trade.sellAt(1, series));

        final Double result = rrc.calculate(series, position);

        assertEquals(Double.NaN, result);
    }
}
