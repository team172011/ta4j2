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
package org.ta4j.core.mocks;

import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;

import java.util.List;

public class MockAnalysisCriterion implements AnalysisCriterion {

    private final BarSeries series;
    private final List<Double> values;

    /**
     * Constructor.
     * 
     * @param series BarSeries of the AnalysisCriterion
     * @param values AnalysisCriterion values
     */
    public MockAnalysisCriterion(BarSeries series, List<Double> values) {
        this.series = series;
        this.values = values;
    }

    /**
     * Gets the final criterion value.
     * 
     * @param series   BarSeries is ignored
     * @param position is ignored
     * @return
     */
    public double calculate(BarSeries series, Position position) {
        return values.get(values.size() - 1);
    }

    /**
     * Gets the final criterion value.
     * 
     * @param series        BarSeries is ignored
     * @param tradingRecord is ignored
     * @return
     */
    public double calculate(BarSeries series, TradingRecord tradingRecord) {
        return values.get(values.size() - 1);
    }

    /**
     * Compares two criterion values and returns true if first value is greater than
     * second value, false otherwise.
     * 
     * @param criterionValue1 first value
     * @param criterionValue2 second value
     * @return boolean indicating first value is greater than second value
     */
    public boolean betterThan(double criterionValue1, double criterionValue2) {
        return criterionValue1 > (criterionValue2);
    }

    public BarSeries getSeries() {
        return series;
    }

}
