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
package org.ta4j.core.analysis;

import org.junit.Test;
import org.ta4j.core.*;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.Collections;

public class CashFlowTest extends AbstractIndicatorTest {

    @Test
    public void cashFlowSize() {
        BarSeries sampleBarSeries = new MockBarSeries(1d, 2d, 3d, 4d, 5d);
        CashFlow cashFlow = new CashFlow(sampleBarSeries, new BaseTradingRecord());
        assertEquals(5, cashFlow.getSize());

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(1, cashFlow.getValue(2));
        assertEquals(1, cashFlow.getValue(3));
        assertEquals(1, cashFlow.getValue(4));

    }

    @Test
    public void cashFlowBuyWithOnlyOnePosition() {
        BarSeries sampleBarSeries = new MockBarSeries(1d, 2d);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(1, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(2, cashFlow.getValue(1));
    }

    @Test
    public void cashFlowWithSellAndBuyTrades() {
        BarSeries sampleBarSeries = new MockBarSeries(2, 1, 3, 5, 6, 3, 20);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(1, sampleBarSeries), Trade.buyAt(3, sampleBarSeries), Trade.sellAt(4, sampleBarSeries),
                Trade.sellAt(5, sampleBarSeries), Trade.buyAt(6, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(0.5, cashFlow.getValue(1));
        assertEquals(0.5, cashFlow.getValue(2));
        assertEquals(0.5, cashFlow.getValue(3));
        assertEquals(0.6, cashFlow.getValue(4));
        assertEquals(0.6, cashFlow.getValue(5));
        assertEquals(-2.8, cashFlow.getValue(6));
    }

    @Test
    public void cashFlowSell() {
        BarSeries sampleBarSeries = new MockBarSeries(1, 2, 4, 8, 16, 32);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(2, sampleBarSeries),
                Trade.buyAt(3, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(1, cashFlow.getValue(2));
        assertEquals(0, cashFlow.getValue(3));
        assertEquals(0, cashFlow.getValue(4));
        assertEquals(0, cashFlow.getValue(5));
    }

    @Test
    public void cashFlowShortSell() {
        BarSeries sampleBarSeries = new MockBarSeries(1, 2, 4, 8, 16, 32);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(2, sampleBarSeries), Trade.sellAt(2, sampleBarSeries), Trade.buyAt(4, sampleBarSeries),
                Trade.buyAt(4, sampleBarSeries), Trade.sellAt(5, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(2, cashFlow.getValue(1));
        assertEquals(4, cashFlow.getValue(2));
        assertEquals(0, cashFlow.getValue(3));
        assertEquals(-8, cashFlow.getValue(4));
        assertEquals(-8, cashFlow.getValue(5));
    }

    @Test
    public void cashFlowShortSellWith20PercentGain() {
        BarSeries sampleBarSeries = new MockBarSeries(110, 100, 90, 80);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(1, sampleBarSeries),
                Trade.buyAt(3, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(1.1, cashFlow.getValue(2));
        assertEquals(1.2, cashFlow.getValue(3));
    }

    @Test
    public void cashFlowShortSellWith20PercentLoss() {
        BarSeries sampleBarSeries = new MockBarSeries(90, 100, 110, 120);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(1, sampleBarSeries),
                Trade.buyAt(3, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(0.9, cashFlow.getValue(2));
        assertEquals(0.8, cashFlow.getValue(3));
    }

    @Test
    public void cashFlowShortSellWith100PercentLoss() {
        BarSeries sampleBarSeries = new MockBarSeries(90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(1, sampleBarSeries),
                Trade.buyAt(11, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(0.9, cashFlow.getValue(2));
        assertEquals(0.8, cashFlow.getValue(3));
        assertEquals(0.7, cashFlow.getValue(4));
        assertEquals(0.6, cashFlow.getValue(5));
        assertEquals(0.5, cashFlow.getValue(6));
        assertEquals(0.4, cashFlow.getValue(7));
        assertEquals(0.3, cashFlow.getValue(8));
        assertEquals(0.2, cashFlow.getValue(9));
        assertEquals(0.1, cashFlow.getValue(10));
        assertEquals(0.0, cashFlow.getValue(11));
    }

    @Test
    public void cashFlowShortSellWithOver100PercentLoss() {
        BarSeries sampleBarSeries = new MockBarSeries(100, 150, 200, 210);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, sampleBarSeries),
                Trade.buyAt(3, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(0.5, cashFlow.getValue(1));
        assertEquals(0.0, cashFlow.getValue(2));
        assertEquals(-0.1, cashFlow.getValue(3));
    }

    @Test
    public void cashFlowShortSellBigLossWithNegativeCashFlow() {
        BarSeries sampleBarSeries = new MockBarSeries(3, 20);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, sampleBarSeries),
                Trade.buyAt(1, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(-4.666666666666667, cashFlow.getValue(1));
    }

    @Test
    public void cashFlowValueWithOnlyOnePositionAndAGapBefore() {
        BarSeries sampleBarSeries = new MockBarSeries(1d, 1d, 2d);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(1, sampleBarSeries),
                Trade.sellAt(2, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(2, cashFlow.getValue(2));
    }

    @Test
    public void cashFlowValueWithOnlyOnePositionAndAGapAfter() {
        BarSeries sampleBarSeries = new MockBarSeries(1d, 2d, 2d);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(1, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(3, cashFlow.getSize());
        assertEquals(1, cashFlow.getValue(0));
        assertEquals(2, cashFlow.getValue(1));
        assertEquals(2, cashFlow.getValue(2));
    }

    @Test
    public void cashFlowValueWithTwoPositionsAndLongTimeWithoutTrades() {
        BarSeries sampleBarSeries = new MockBarSeries(1d, 2d, 4d, 8d, 16d, 32d);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(1, sampleBarSeries),
                Trade.sellAt(2, sampleBarSeries), Trade.buyAt(4, sampleBarSeries), Trade.sellAt(5, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(1, cashFlow.getValue(1));
        assertEquals(2, cashFlow.getValue(2));
        assertEquals(2, cashFlow.getValue(3));
        assertEquals(2, cashFlow.getValue(4));
        assertEquals(4, cashFlow.getValue(5));
    }

    @Test
    public void cashFlowValue() {
        // First sample series
        BarSeries sampleBarSeries = new MockBarSeries(3d, 2d, 5d, 1000d, 5000d, 0.0001d, 4d, 7d, 6d, 7d, 8d, 5d, 6d);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(2, sampleBarSeries), Trade.buyAt(6, sampleBarSeries), Trade.sellAt(8, sampleBarSeries),
                Trade.buyAt(9, sampleBarSeries), Trade.sellAt(11, sampleBarSeries));

        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);

        assertEquals(1, cashFlow.getValue(0));
        assertEquals(2d / 3, cashFlow.getValue(1));
        assertEquals(5d / 3, cashFlow.getValue(2));
        assertEquals(5d / 3, cashFlow.getValue(3));
        assertEquals(5d / 3, cashFlow.getValue(4));
        assertEquals(5d / 3, cashFlow.getValue(5));
        assertEquals(5d / 3, cashFlow.getValue(6));
        assertEquals(5d / 3 * 7d / 4, cashFlow.getValue(7));
        assertEquals(5d / 3 * 6d / 4, cashFlow.getValue(8));
        assertEquals(5d / 3 * 6d / 4, cashFlow.getValue(9));
        assertEquals(5d / 3 * 6d / 4 * 8d / 7, cashFlow.getValue(10));
        assertEquals(5d / 3 * 6d / 4 * 5d / 7, cashFlow.getValue(11));
        assertEquals(5d / 3 * 6d / 4 * 5d / 7, cashFlow.getValue(12));

        // Second sample series
        sampleBarSeries = new MockBarSeries(5d, 6d, 3d, 7d, 8d, 6d, 10d, 15d, 6d);
        tradingRecord = new BaseTradingRecord(Trade.buyAt(4, sampleBarSeries), Trade.sellAt(5, sampleBarSeries),
                Trade.buyAt(6, sampleBarSeries), Trade.sellAt(8, sampleBarSeries));

        CashFlow flow = new CashFlow(sampleBarSeries, tradingRecord);
        assertEquals(1, flow.getValue(0));
        assertEquals(1, flow.getValue(1));
        assertEquals(1, flow.getValue(2));
        assertEquals(1, flow.getValue(3));
        assertEquals(1, flow.getValue(4));
        assertEquals(0.75, flow.getValue(5));
        assertEquals(0.75, flow.getValue(6));
        assertEquals(1.125, flow.getValue(7));
        assertEquals(0.45, flow.getValue(8));
    }

    @Test
    public void cashFlowValueWithNoPositions() {
        BarSeries sampleBarSeries = new MockBarSeries(3d, 2d, 5d, 4d, 7d, 6d, 7d, 8d, 5d, 6d);
        CashFlow cashFlow = new CashFlow(sampleBarSeries, new BaseTradingRecord());
        assertEquals(1, cashFlow.getValue(4));
        assertEquals(1, cashFlow.getValue(7));
        assertEquals(1, cashFlow.getValue(9));
    }

    @Test
    public void reallyLongCashFlow() {
        int size = 1000000;
        BarSeries sampleBarSeries = new MockBarSeries(Collections.nCopies(size, new MockBar(10)));
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, sampleBarSeries),
                Trade.sellAt(size - 1, sampleBarSeries));
        CashFlow cashFlow = new CashFlow(sampleBarSeries, tradingRecord);
        assertEquals(1, cashFlow.getValue(size - 1));
    }

}
