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
package org.ta4j.core.rules;

import org.ta4j.core.Indicator;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;

/**
 * A trailing stop-loss rule
 *
 * Satisfied when the price reaches the trailing loss threshold.
 */
public class TrailingStopLossRule extends AbstractRule {

    /**
     * The price indicator
     */
    private final Indicator<Double> priceIndicator;

    /** The barCount */
    private final int barCount;

    /** the loss-distance as percentage */
    private final Double lossPercentage;

    /**
     * the current stop loss price activation
     */
    private Double currentStopLossLimitActivation;

    /**
     * Constructor.
     * 
     * @param indicator      the (close price) indicator
     * @param lossPercentage the loss percentage
     * @param barCount       number of bars to look back for the calculation
     */
    public TrailingStopLossRule(Indicator<Double> indicator, double lossPercentage, int barCount) {
        this.priceIndicator = indicator;
        this.barCount = barCount;
        this.lossPercentage = lossPercentage;
    }

    /**
     * Constructor.
     * 
     * @param indicator      the (close price) indicator
     * @param lossPercentage the loss percentage
     */
    public TrailingStopLossRule(Indicator<Double> indicator, double lossPercentage) {
        this(indicator, lossPercentage, Integer.MAX_VALUE);
    }

    /** This rule uses the {@code tradingRecord}. */
    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        // No trading history or no position opened, no loss
        if (tradingRecord != null) {
            Position currentPosition = tradingRecord.getCurrentPosition();
            if (currentPosition.isOpened()) {
                double currentPrice = priceIndicator.getValue(index);
                int positionIndex = currentPosition.getEntry().getIndex();

                if (currentPosition.getEntry().isBuy()) {
                    satisfied = isBuySatisfied(currentPrice, index, positionIndex);
                } else {
                    satisfied = isSellSatisfied(currentPrice, index, positionIndex);
                }
            }
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }

    private boolean isBuySatisfied(Double currentPrice, int index, int positionIndex) {
        HighestValueIndicator highest = new HighestValueIndicator(priceIndicator,
                getValueIndicatorBarCount(index, positionIndex));
        double highestClose = highest.getValue(index);
        double lossRatioThreshold = (100 - lossPercentage) / 100;
        currentStopLossLimitActivation = highestClose * lossRatioThreshold;
        return currentPrice <= currentStopLossLimitActivation;
    }

    public Double getCurrentStopLossLimitActivation() {
        return currentStopLossLimitActivation;
    }

    private boolean isSellSatisfied(Double currentPrice, int index, int positionIndex) {
        LowestValueIndicator lowest = new LowestValueIndicator(priceIndicator,
                getValueIndicatorBarCount(index, positionIndex));
        Double lowestClose = lowest.getValue(index);
        Double lossRatioThreshold = (100 + lossPercentage) / 100;
        currentStopLossLimitActivation = lowestClose * lossRatioThreshold;
        return currentPrice >= currentStopLossLimitActivation;
    }

    private int getValueIndicatorBarCount(int index, int positionIndex) {
        return Math.min(index - positionIndex + 1, this.barCount);
    }

    @Override
    protected void traceIsSatisfied(int index, boolean isSatisfied) {
        if (log.isTraceEnabled()) {
            log.trace("{}#isSatisfied({}): {}. Current price: {}, Current stop loss activation: {}",
                    getClass().getSimpleName(), index, isSatisfied, priceIndicator.getValue(index),
                    currentStopLossLimitActivation);
        }
    }
}
