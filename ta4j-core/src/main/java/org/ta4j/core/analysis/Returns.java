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

import static java.lang.Double.NaN;

/**
 * The return rates.
 *
 * This class allows to compute the return rate of a price time-series
 */
public class Returns implements Indicator<Double> {

    public enum ReturnType {
        LOG {
            @Override
            public Double calculate(Double xNew, Double xOld) {
                // r_i = ln(P_i/P_(i-1))
                return Math.log(xNew / (xOld));
            }
        },
        ARITHMETIC {
            @Override
            public Double calculate(Double xNew, Double xOld) {
                // r_i = P_i/P_(i-1) - 1
                return xNew / xOld - 1;
            }
        };

        /**
         * @return calculate a single return rate
         */
        public abstract Double calculate(Double xNew, Double xOld);
    }

    private final ReturnType type;

    /**
     * The bar series
     */
    private final BarSeries barSeries;

    /**
     * The return rates
     */
    private List<Double> values;

    /**
     * Constructor.
     *
     * @param barSeries the bar series
     * @param position  a single position
     */
    public Returns(BarSeries barSeries, Position position, ReturnType type) {
        this.barSeries = barSeries;
        this.type = type;
        // at index 0, there is no return
        values = new ArrayList<>(Collections.singletonList(NaN));
        calculate(position);

        fillToTheEnd();
    }

    /**
     * Constructor.
     *
     * @param barSeries     the bar series
     * @param tradingRecord the trading record
     */
    public Returns(BarSeries barSeries, TradingRecord tradingRecord, ReturnType type) {
        this.barSeries = barSeries;
        this.type = type;
        // at index 0, there is no return
        values = new ArrayList<>(Collections.singletonList(NaN));
        calculate(tradingRecord);

        fillToTheEnd();
    }

    public List<Double> getValues() {
        return values;
    }

    /**
     * @param index the bar index
     * @return the return rate value at the index-th position
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
     * @return the size of the return series.
     */
    public int getSize() {
        return barSeries.getBarCount() - 1;
    }

    public void calculate(Position position) {
        calculate(position, barSeries.getEndIndex());
    }

    /**
     * Calculates the cash flow for a single position (including accrued cashflow
     * for open positions).
     *
     * @param position   a single position
     * @param finalIndex index up until cash flow of open positions is considered
     */
    public void calculate(Position position, int finalIndex) {
        boolean isLongTrade = position.getEntry().isBuy();
        int endIndex = CashFlow.determineEndIndex(position, finalIndex, barSeries.getEndIndex());
        final int entryIndex = position.getEntry().getIndex();
        int begin = entryIndex + 1;
        if (begin > values.size()) {
            values.addAll(Collections.nCopies(begin - values.size(), 0d));
        }

        int startingIndex = Math.max(begin, 1);
        int nPeriods = endIndex - entryIndex;
        Double holdingCost = position.getHoldingCost(endIndex);
        Double avgCost = holdingCost / (nPeriods);

        // returns are per period (iterative). Base price needs to be updated
        // accordingly
        Double lastPrice = position.getEntry().getNetPrice();
        for (int i = startingIndex; i < endIndex; i++) {
            Double intermediateNetPrice = CashFlow.addCost(barSeries.getBar(i).getClosePrice(), avgCost, isLongTrade);
            Double assetReturn = type.calculate(intermediateNetPrice, lastPrice);

            Double strategyReturn;
            if (position.getEntry().isBuy()) {
                strategyReturn = assetReturn;
            } else {
                strategyReturn = assetReturn * (-1);
            }
            values.add(strategyReturn);
            // update base price
            lastPrice = barSeries.getBar(i).getClosePrice();
        }

        // add net return at exit position
        Double exitPrice;
        if (position.getExit() != null) {
            exitPrice = position.getExit().getNetPrice();
        } else {
            exitPrice = barSeries.getBar(endIndex).getClosePrice();
        }

        Double strategyReturn;
        Double assetReturn = type.calculate(CashFlow.addCost(exitPrice, avgCost, isLongTrade), lastPrice);
        if (position.getEntry().isBuy()) {
            strategyReturn = assetReturn;
        } else {
            strategyReturn = assetReturn * (-1);
        }
        values.add(strategyReturn);
    }

    /**
     * Calculates the returns for a trading record.
     *
     * @param tradingRecord the trading record
     */
    private void calculate(TradingRecord tradingRecord) {
        // For each position...
        tradingRecord.getPositions().forEach(this::calculate);
    }

    /**
     * Fills with zeroes until the end of the series.
     */
    private void fillToTheEnd() {
        if (barSeries.getEndIndex() >= values.size()) {
            values.addAll(Collections.nCopies(barSeries.getEndIndex() - values.size() + 1, 0d));
        }
    }
}