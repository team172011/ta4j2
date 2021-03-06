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
import org.ta4j.core.*;


public class LinearTransactionCostModelTest extends AbstractIndicatorTest {

    private CostModel transactionModel;

    @Before
    public void setUp() throws Exception {
        transactionModel = new LinearTransactionCostModel(0.01);
    }

    @Test
    public void calculateSingleTradeCost() {
        // Price - Amount calculation Test
        double price = 100;
        double amount = 2;
        double cost = transactionModel.calculate(price, amount);

        assertEquals(2, cost);
    }

    @Test
    public void calculateBuyPosition() {
        // Calculate the transaction costs of a closed long position
        int holdingPeriod = 2;
        Trade entry = Trade.buyAt(0, 100, 1, transactionModel);
        Trade exit = Trade.sellAt(holdingPeriod, 110, 1, transactionModel);

        Position position = new Position(entry, exit, transactionModel, new ZeroCostModel());

        Double costFromBuy = entry.getCost();
        Double costFromSell = exit.getCost();
        Double costsFromModel = transactionModel.calculate(position, holdingPeriod);

        assertEquals(costsFromModel, costFromBuy+(costFromSell));
        assertEquals(costsFromModel, (2.1));
        assertEquals(costFromBuy, (1));
    }

    @Test
    public void calculateSellPosition() {
        // Calculate the transaction costs of a closed short position
        int holdingPeriod = 2;
        Trade entry = Trade.sellAt(0, (100), (1), transactionModel);
        Trade exit = Trade.buyAt(holdingPeriod, (110), (1), transactionModel);

        Position position = new Position(entry, exit, transactionModel, new ZeroCostModel());

        Double costFromBuy = entry.getCost();
        Double costFromSell = exit.getCost();
        Double costsFromModel = transactionModel.calculate(position, holdingPeriod);

        assertEquals(costsFromModel, costFromBuy+(costFromSell));
        assertEquals(costsFromModel, (2.1));
        assertEquals(costFromBuy, (1));
    }

    @Test
    public void calculateOpenSellPosition() {
        // Calculate the transaction costs of an open position
        int currentIndex = 4;
        Position position = new Position(Trade.TradeType.BUY, transactionModel, new ZeroCostModel());
        position.operate(0, 100, 1);

        Double costsFromModel = transactionModel.calculate(position, currentIndex);

        assertEquals(costsFromModel, (1));
    }

    @Test
    public void testEquality() {
        LinearTransactionCostModel model = new LinearTransactionCostModel(0.1);
        CostModel modelSameClass = new LinearTransactionCostModel(0.2);
        CostModel modelSameFee = new LinearTransactionCostModel(0.1);
        CostModel modelOther = new ZeroCostModel();

        boolean equality = model.equals(modelSameFee);
        boolean inequality1 = model.equals(modelSameClass);
        boolean inequality2 = model.equals(modelOther);

        assertTrue(equality);
        assertFalse(inequality1);
        assertFalse(inequality2);
    }

}