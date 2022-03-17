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
import org.ta4j.core.*;
import org.ta4j.core.mocks.MockBarSeries;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LinearTransactionCostCriterionTest extends AbstractCriterionTest {

    @Test
    public void dummyData() {
        MockBarSeries series = new MockBarSeries(100, 150, 200, 100, 50, 100);
        TradingRecord tradingRecord = new BaseTradingRecord();
        double criterion;

        tradingRecord.operate(0);
        tradingRecord.operate(1);
        criterion = new LinearTransactionCostCriterion(1000d, 0.005, 0.2).calculate(series, tradingRecord);
        assertEquals(12.861, criterion);

        tradingRecord.operate(2);
        tradingRecord.operate(3);
        criterion = new LinearTransactionCostCriterion(1000d, 0.005, 0.2).calculate(series, tradingRecord);
        assertEquals(24.3759857625, criterion);

        tradingRecord.operate(5);
        criterion = new LinearTransactionCostCriterion(1000d, 0.005, 0.2).calculate(series, tradingRecord);
        assertEquals(28.2488150711875, criterion);
    }

    @Test
    public void fixedCost() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord();
        double criterion;

        tradingRecord.operate(0);
        tradingRecord.operate(1);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 1.3d).calculate(series, tradingRecord);
        assertEquals(2.6d, criterion);

        tradingRecord.operate(2);
        tradingRecord.operate(3);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 1.3d).calculate(series, tradingRecord);
        assertEquals(5.2d, criterion);

        tradingRecord.operate(0);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 1.3d).calculate(series, tradingRecord);
        assertEquals(6.5d, criterion);
    }

    @Test
    public void fixedCostWithOnePosition() {
        MockBarSeries series = new MockBarSeries(100, 95, 100, 80, 85, 70);
        Position position = new Position();
        double criterion;

        criterion = new LinearTransactionCostCriterion(1000d, 0d, 0.75d).calculate(series, position);
        assertEquals(0d, criterion);

        position.operate(1);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 0.75d).calculate(series, position);
        assertEquals(0.75d, criterion);

        position.operate(3);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 0.75d).calculate(series, position);
        assertEquals(1.5d, criterion);

        position.operate(4);
        criterion = new LinearTransactionCostCriterion(1000d, 0d, 0.75d).calculate(series, position);
        assertEquals(1.5d, criterion);
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new LinearTransactionCostCriterion(1000, 0.5);
        assertTrue(criterion.betterThan(3.1, 4.2));
        assertFalse(criterion.betterThan(2.1, 1.9));
    }
}
