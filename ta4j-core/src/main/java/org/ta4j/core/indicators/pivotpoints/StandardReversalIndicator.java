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
package org.ta4j.core.indicators.pivotpoints;

import org.ta4j.core.Bar;
import org.ta4j.core.indicators.RecursiveCachedIndicator;

import java.util.List;

import static java.lang.Double.NaN;


/**
 * Pivot Reversal Indicator.
 *
 * @see <a href=
 *      "http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points">
 *      http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:pivot_points</a>
 */
public class StandardReversalIndicator extends RecursiveCachedIndicator<Double> {

    private final PivotPointIndicator pivotPointIndicator;
    private final PivotLevel level;

    /**
     * Constructor.
     *
     * Calculates the (standard) reversal for the corresponding pivot level
     *
     * @param pivotPointIndicator the {@link PivotPointIndicator} for this reversal
     * @param level               the {@link PivotLevel} for this reversal
     */
    public StandardReversalIndicator(PivotPointIndicator pivotPointIndicator, PivotLevel level) {
        super(pivotPointIndicator);
        this.pivotPointIndicator = pivotPointIndicator;
        this.level = level;
    }

    @Override
    protected Double calculate(int index) {
        List<Integer> barsOfPreviousPeriod = pivotPointIndicator.getBarsOfPreviousPeriod(index);
        if (barsOfPreviousPeriod.isEmpty()) {
            return NaN;
        }
        switch (level) {
        case RESISTANCE_3:
            return calculateR3(barsOfPreviousPeriod, index);
        case RESISTANCE_2:
            return calculateR2(barsOfPreviousPeriod, index);
        case RESISTANCE_1:
            return calculateR1(barsOfPreviousPeriod, index);
        case SUPPORT_1:
            return calculateS1(barsOfPreviousPeriod, index);
        case SUPPORT_2:
            return calculateS2(barsOfPreviousPeriod, index);
        case SUPPORT_3:
            return calculateS3(barsOfPreviousPeriod, index);
        default:
            return NaN;
        }

    }

    private Double calculateR3(List<Integer> barsOfPreviousPeriod, int index) {
        Bar bar = getBarSeries().getBar(barsOfPreviousPeriod.get(0));
        Double low = bar.getLowPrice();
        Double high = bar.getHighPrice();
        for (int i : barsOfPreviousPeriod) {
            low = Math.min(getBarSeries().getBar(i).getLowPrice(), low);
            high = Math.max(getBarSeries().getBar(i).getHighPrice(), high);
        }
        return high+(2 * ((pivotPointIndicator.getValue(index) - (low))));
    }

    private Double calculateR2(List<Integer> barsOfPreviousPeriod, int index) {
        Bar bar = getBarSeries().getBar(barsOfPreviousPeriod.get(0));
        Double low = bar.getLowPrice();
        Double high = bar.getHighPrice();
        for (int i : barsOfPreviousPeriod) {
            low = Math.min(getBarSeries().getBar(i).getLowPrice(), low);
            high = Math.max(getBarSeries().getBar(i).getHighPrice(), high);
        }
        return pivotPointIndicator.getValue(index) + (high - low);
    }

    private Double calculateR1(List<Integer> barsOfPreviousPeriod, int index) {
        Double low = getBarSeries().getBar(barsOfPreviousPeriod.get(0)).getLowPrice();
        for (int i : barsOfPreviousPeriod) {
            low = Math.min(getBarSeries().getBar(i).getLowPrice(), low);
        }
        return 2 * (pivotPointIndicator.getValue(index)) - (low);
    }

    private Double calculateS1(List<Integer> barsOfPreviousPeriod, int index) {
        Double high = getBarSeries().getBar(barsOfPreviousPeriod.get(0)).getHighPrice();
        for (int i : barsOfPreviousPeriod) {
            high = Math.max(getBarSeries().getBar(i).getHighPrice(),high);
        }
        return 2 * (pivotPointIndicator.getValue(index)) - (high);
    }

    private Double calculateS2(List<Integer> barsOfPreviousPeriod, int index) {
        Bar bar = getBarSeries().getBar(barsOfPreviousPeriod.get(0));
        Double high = bar.getHighPrice();
        Double low = bar.getLowPrice();
        for (int i : barsOfPreviousPeriod) {
            high = Math.max(getBarSeries().getBar(i).getHighPrice(), high);
            low = Math.min(getBarSeries().getBar(i).getLowPrice(), low);
        }
        return pivotPointIndicator.getValue(index) - ((high - (low)));
    }

    private Double calculateS3(List<Integer> barsOfPreviousPeriod, int index) {
        Bar bar = getBarSeries().getBar(barsOfPreviousPeriod.get(0));
        Double high = bar.getHighPrice();
        Double low = bar.getLowPrice();
        for (int i : barsOfPreviousPeriod) {
            high = Math.max(getBarSeries().getBar(i).getHighPrice(), high);
            low = Math.min(getBarSeries().getBar(i).getLowPrice(), low);
        }
        return low - (2 * ((high - (pivotPointIndicator.getValue(index)))));
    }
}
