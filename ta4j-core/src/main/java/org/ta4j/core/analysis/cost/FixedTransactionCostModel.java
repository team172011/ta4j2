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

import org.ta4j.core.Position;
import org.ta4j.core.Trade;

import java.util.Objects;


public class FixedTransactionCostModel implements CostModel {

    private static final long serialVersionUID = 3486293523619720786L;

    /**
     * Cost per {@link Trade trade}.
     */
    private final Double feePerTrade;

    /**
     * Constructor for a fixed fee trading cost model.
     *
     * Open {@link Position position} cost: (fixedFeePerTrade * 1) Closed
     * {@link Position position} cost: (fixedFeePerTrade * 2)
     *
     * @param feePerTrade the fixed fee per {@link Trade trade})
     */
    public FixedTransactionCostModel(Double feePerTrade) {
        this.feePerTrade = feePerTrade;
    }

    /**
     * Calculates the transaction cost of a position.
     *
     * @param position     the position
     * @param currentIndex current bar index (irrelevant for the
     *                     FixedTransactionCostModel)
     * @return the absolute position cost
     */
    @Override
    public double calculate(Position position, int currentIndex) {
        double pricePerAsset = position.getEntry().getPricePerAsset();
        double multiplier = 1;
        if (position.isClosed()) {
            multiplier = 2;
        }
        return feePerTrade * multiplier;
    }

    /**
     * Calculates the transaction cost of a position.
     *
     * @param position the position
     * @return the absolute position cost
     */
    @Override
    public double calculate(Position position) {
        return this.calculate(position, 0);
    }

    /**
     * Calculates the transaction cost based on the price and the amount (both
     * irrelevant for the FixedTransactionCostModel as the fee is always the same).
     *
     * @param price  the price per asset
     * @param amount number of traded assets
     */
    @Override
    public double calculate(double price, double amount) {
        return feePerTrade;
    }

    /**
     * Evaluate if two models are equal
     *
     * @param otherModel model to compare with
     */
    public boolean equals(CostModel otherModel) {
        boolean equality = false;
        if (this.getClass().equals(otherModel.getClass())) {
            equality = Objects.equals(((FixedTransactionCostModel) otherModel).feePerTrade, this.feePerTrade);
        }
        return equality;
    }
}
