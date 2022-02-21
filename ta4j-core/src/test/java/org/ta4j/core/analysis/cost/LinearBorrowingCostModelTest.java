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
package org.ta4j.core.analysis.cost;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Position;
import org.ta4j.core.Trade;

public class LinearBorrowingCostModelTest extends AbstractIndicatorTest {

    private CostModel borrowingModel;

    @Before
    public void setUp() throws Exception {
        borrowingModel = new LinearBorrowingCostModel(0.01);
    }

    @Test
    public void calculateZeroTest() {
        // Price - Amount calculation Test
        double price = (100);
        double amount = (2);
        double cost = borrowingModel.calculate(price, amount);

        assertEquals((0), cost);
    }

    @Test
    public void calculateBuyPosition() {
        // Holding a bought asset should not incur borrowing costs
        int holdingPeriod = 2;
        Trade entry = Trade.buyAt(0, (100), (1));
        Trade exit = Trade.sellAt(holdingPeriod, (110), (1));

        Position position = new Position(entry, exit, new ZeroCostModel(), borrowingModel);

        Double costsFromPosition = position.getHoldingCost();
        Double costsFromModel = borrowingModel.calculate(position, holdingPeriod);

        assertEquals(costsFromModel, costsFromPosition);
        assertEquals(costsFromModel, 0d);
    }

    @Test
    public void calculateSellPosition() {
        // Short selling incurs borrowing costs
        int holdingPeriod = 2;
        Trade entry = Trade.sellAt(0, (100), (1));
        Trade exit = Trade.buyAt(holdingPeriod, (110), (1));

        Position position = new Position(entry, exit, new ZeroCostModel(), borrowingModel);

        Double costsFromPosition = position.getHoldingCost();
        Double costsFromModel = borrowingModel.calculate(position, holdingPeriod);

        assertEquals(costsFromModel, costsFromPosition);
        assertEquals(costsFromModel, 2.d);
    }

    @Test
    public void calculateOpenSellPosition() {
        // Short selling incurs borrowing costs. Since position is still open, accounted
        // for until current index
        int currentIndex = 4;
        Position position = new Position(Trade.TradeType.SELL, new ZeroCostModel(), borrowingModel);
        position.operate(0, (100), (1));

        Double costsFromPosition = position.getHoldingCost(currentIndex);
        Double costsFromModel = borrowingModel.calculate(position, currentIndex);

        assertEquals(costsFromModel, costsFromPosition);
        assertEquals(costsFromModel, 4d);
    }

    @Test
    public void testEquality() {
        LinearBorrowingCostModel model = new LinearBorrowingCostModel(0.1);
        CostModel modelSameClass = new LinearBorrowingCostModel(0.2);
        CostModel modelSameFee = new LinearBorrowingCostModel(0.1);
        CostModel modelOther = new ZeroCostModel();

        boolean equality = model.equals(modelSameFee);
        boolean inequality1 = model.equals(modelSameClass);
        boolean inequality2 = model.equals(modelOther);

        assertTrue(equality);
        assertFalse(inequality1);
        assertFalse(inequality2);
    }
}