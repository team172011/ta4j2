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



public class NumberOfWinningPositionsCriterionTest extends AbstractCriterionTest {
    @Test
    public void calculateWithNoPositions() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);

        assertEquals(0, new NumberOfWinningPositionsCriterion().calculate(series, new BaseTradingRecord()));
    }

    @Test
    public void calculateWithTwoLongPositions() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.buyAt(0, series), Trade.sellAt(2, series),
                Trade.buyAt(3, series), Trade.sellAt(5, series));

        assertEquals(2, new NumberOfWinningPositionsCriterion().calculate(series, tradingRecord));
    }

    @Test
    public void calculateWithOneLongPosition() {
        MockBarSeries series = new MockBarSeries(100, 105, 110, 100, 95, 105);
        Position position = new Position(Trade.buyAt(0, series), Trade.sellAt(2, series));

        assertEquals(1, new NumberOfWinningPositionsCriterion().calculate(series, position));
    }

    @Test
    public void calculateWithTwoShortPositions() {
        MockBarSeries series = new MockBarSeries(110, 105, 110, 100, 95, 105);
        TradingRecord tradingRecord = new BaseTradingRecord(Trade.sellAt(0, series), Trade.buyAt(1, series),
                Trade.sellAt(2, series), Trade.buyAt(4, series));

        assertEquals(2, new NumberOfWinningPositionsCriterion().calculate(series, tradingRecord));
    }

    @Test
    public void betterThan() {
        AnalysisCriterion criterion = new NumberOfWinningPositionsCriterion();
        assertTrue(criterion.betterThan((6), (3)));
        assertFalse(criterion.betterThan((4), (7)));
    }

    @Test
    public void testCalculateOneOpenPositionShouldReturnZero() {
        new OpenedPositionUtils().testCalculateOneOpenPositionShouldReturnExpectedValue(new NumberOfWinningPositionsCriterion(), 0);
    }
}
