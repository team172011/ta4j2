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

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The cash flow.
 *
 * This class allows to follow the money cash flow involved by a list of
 * positions over a bar series.
 */
public class CashFlow implements Indicator<Double> {

    /**
     * The bar series
     */
    private final BarSeries barSeries;

    /**
     * The cash flow values
     */
    private List<Double> values;

    /**
     * Constructor for cash flows of a closed position.
     *
     * @param barSeries the bar series
     * @param position  a single position
     */
    public CashFlow(BarSeries barSeries, Position position) {
        this.barSeries = barSeries;
        values = new ArrayList<>(Collections.singletonList((1d)));
        calculate(position);
        fillToTheEnd();
    }

    /**
     * Constructor for cash flows of closed positions of a trading record.
     *
     * @param barSeries     the bar series
     * @param tradingRecord the trading record
     */
    public CashFlow(BarSeries barSeries, TradingRecord tradingRecord) {
        this.barSeries = barSeries;
        values = new ArrayList<>(Collections.singletonList(1d));
        calculate(tradingRecord);

        fillToTheEnd();
    }

    /**
     * Constructor.
     *
     * @param barSeries     the bar series
     * @param tradingRecord the trading record
     * @param finalIndex    index up until cash flows of open positions are
     *                      considered
     */
    public CashFlow(BarSeries barSeries, TradingRecord tradingRecord, int finalIndex) {
        this.barSeries = barSeries;
        values = new ArrayList<>(Collections.singletonList(1d));
        calculate(tradingRecord, finalIndex);

        fillToTheEnd();
    }

    /**
     * @param index the bar index
     * @return the cash flow value at the index-th position
     */
    @Override
    public Double getValue(int index) {
        return values.get(index);
    }

    @Override
    public BarSeries getBarSeries() {
        return barSeries;
    }

    /**
     * @return the size of the bar series
     */
    public int getSize() {
        return barSeries.getBarCount();
    }

    /**
     * Calculates the cash flow for a single closed position.
     *
     * @param position a single position
     */
    private void calculate(Position position) {
        if (position.isOpened()) {
            throw new IllegalArgumentException(
                    "Position is not closed. Final index of observation needs to be provided.");
        }
        calculate(position, position.getExit().getIndex());
    }

    /**
     * Calculates the cash flow for a single position (including accrued cashflow
     * for open positions).
     *
     * @param position   a single position
     * @param finalIndex index up until cash flow of open positions is considered
     */
    private void calculate(Position position, int finalIndex) {
        boolean isLongTrade = position.getEntry().isBuy();
        int endIndex = determineEndIndex(position, finalIndex, barSeries.getEndIndex());
        final int entryIndex = position.getEntry().getIndex();
        int begin = entryIndex + 1;
        if (begin > values.size()) {
            Double lastValue = values.get(values.size() - 1);
            values.addAll(Collections.nCopies(begin - values.size(), lastValue));
        }
        // Trade is not valid if net balance at the entryIndex is negative
        if (values.get(values.size() - 1) > (0)) {
            int startingIndex = Math.max(begin, 1);

            int nPeriods = endIndex - entryIndex;
            Double holdingCost = position.getHoldingCost(endIndex);
            Double avgCost = holdingCost / (nPeriods);

            // Add intermediate cash flows during position
            Double netEntryPrice = position.getEntry().getNetPrice();
            for (int i = startingIndex; i < endIndex; i++) {
                Double intermediateNetPrice = addCost(barSeries.getBar(i).getClosePrice(), avgCost, isLongTrade);
                Double ratio = getIntermediateRatio(isLongTrade, netEntryPrice, intermediateNetPrice);
                values.add(values.get(entryIndex)*(ratio));
            }

            // add net cash flow at exit position
            Double exitPrice;
            if (position.getExit() != null) {
                exitPrice = position.getExit().getNetPrice();
            } else {
                exitPrice = barSeries.getBar(endIndex).getClosePrice();
            }
            Double ratio = getIntermediateRatio(isLongTrade, netEntryPrice, addCost(exitPrice, avgCost, isLongTrade));
            values.add(values.get(entryIndex)*(ratio));
        }
    }

    /**
     * Calculates the ratio of intermediate prices.
     *
     * @param isLongTrade true, if the entry trade type is BUY
     * @param entryPrice  price ratio denominator
     * @param exitPrice   price ratio numerator
     */
    private static Double getIntermediateRatio(boolean isLongTrade, Double entryPrice, Double exitPrice) {
        double ratio;
        if (isLongTrade) {
            ratio = exitPrice / (entryPrice);
        } else {
            ratio = 2 - (exitPrice / (entryPrice));
        }
        return ratio;
    }

    /**
     * Calculates the cash flow for the closed positions of a trading record.
     *
     * @param tradingRecord the trading record
     */
    private void calculate(TradingRecord tradingRecord) {
        // For each position...
        tradingRecord.getPositions().forEach(this::calculate);
    }

    /**
     * Calculates the cash flow for all positions of a trading record, including
     * accrued cash flow of an open position.
     *
     * @param tradingRecord the trading record
     * @param finalIndex    index up until cash flows of open positions are
     *                      considered
     */
    private void calculate(TradingRecord tradingRecord, int finalIndex) {
        calculate(tradingRecord);

        // Add accrued cash flow of open position
        if (tradingRecord.getCurrentPosition().isOpened()) {
            calculate(tradingRecord.getCurrentPosition(), finalIndex);
        }
    }

    /**
     * Adjusts (intermediate) price to incorporate trading costs.
     *
     * @param rawPrice    the gross asset price
     * @param holdingCost share of the holding cost per period
     * @param isLongTrade true, if the entry trade type is BUY
     */
    static Double addCost(Double rawPrice, Double holdingCost, boolean isLongTrade) {
        double netPrice;
        if (isLongTrade) {
            netPrice = rawPrice - (holdingCost);
        } else {
            netPrice = rawPrice+(holdingCost);
        }
        return netPrice;
    }

    /**
     * Fills with last value till the end of the series.
     */
    private void fillToTheEnd() {
        if (barSeries.getEndIndex() >= values.size()) {
            Double lastValue = values.get(values.size() - 1);
            values.addAll(Collections.nCopies(barSeries.getEndIndex() - values.size() + 1, lastValue));
        }
    }

    /**
     * Determines the the valid final index to be considered.
     *
     * @param position   the position
     * @param finalIndex index up until cash flows of open positions are considered
     * @param maxIndex   maximal valid index
     */
    static int determineEndIndex(Position position, int finalIndex, int maxIndex) {
        int idx = finalIndex;
        // After closing of position, no further accrual necessary
        if (position.getExit() != null) {
            idx = Math.min(position.getExit().getIndex(), finalIndex);
        }
        // Accrual at most until maximal index of asset data
        if (idx > maxIndex) {
            idx = maxIndex;
        }
        return idx;
    }
}