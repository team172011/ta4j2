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
package org.ta4j.core.indicators.helpers;

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.mocks.MockBarSeries;

public class DifferencePercentageIndicatorTest extends AbstractIndicatorTest {
    private DifferencePercentageIndicator percentageChangeIndicator;

    @Test
    public void getValueWithoutThreshold() {
        BarSeries series = new MockBarSeries();
        FixedIndicator<Double> mockIndicator = new FixedIndicator<>(series, 100d, 101d, 98.98d, (102.186952),
                (91.9682568), (100.5213046824), (101.526517729224));

        percentageChangeIndicator = new DifferencePercentageIndicator(mockIndicator);
        assertEquals(Double.NaN, percentageChangeIndicator.getValue(0));
        assertEquals((1), percentageChangeIndicator.getValue(1));
        assertEquals((-2), percentageChangeIndicator.getValue(2));
        assertEquals((3.24), percentageChangeIndicator.getValue(3));
        assertEquals((-10), percentageChangeIndicator.getValue(4));
        assertEquals((9.3), percentageChangeIndicator.getValue(5));
        assertEquals((1), percentageChangeIndicator.getValue(6));
    }

    @Test
    public void getValueWithNumThreshold() {
        BarSeries series = new MockBarSeries();
        FixedIndicator<Double> mockIndicator = new FixedIndicator<>(series, (1000d), (1010d), (1020d), (1050d),
                (1060.5), (1081.5), (1102.5), (1091.475), (1113.525), (1036.35), (1067.4405));

        percentageChangeIndicator = new DifferencePercentageIndicator(mockIndicator, (5));
        assertEquals(Double.NaN, percentageChangeIndicator.getValue(0));
        assertEquals((1), percentageChangeIndicator.getValue(1));
        assertEquals(2, percentageChangeIndicator.getValue(2));
        assertEquals((5), percentageChangeIndicator.getValue(3));
        assertEquals((1), percentageChangeIndicator.getValue(4));
        assertEquals((3), percentageChangeIndicator.getValue(5));
        assertEquals((5), percentageChangeIndicator.getValue(6));
        assertEquals((-1), percentageChangeIndicator.getValue(7));
        assertEquals((1), percentageChangeIndicator.getValue(8));
        assertEquals((-6), percentageChangeIndicator.getValue(9));
        assertEquals((3), percentageChangeIndicator.getValue(10));
    }

    @Test
    public void getValueWithNumberThreshold() {
        BarSeries series = new MockBarSeries();
        FixedIndicator<Double> mockIndicator = new FixedIndicator<>(series, (1000d), (1000d), (1010d), (1025d),
                (1038.325));

        percentageChangeIndicator = new DifferencePercentageIndicator(mockIndicator, 1.5);
        assertEquals(Double.NaN, percentageChangeIndicator.getValue(0));
        assertEquals((0), percentageChangeIndicator.getValue(1));
        assertEquals((1), percentageChangeIndicator.getValue(2));
        assertEquals((2.5), percentageChangeIndicator.getValue(3));
        assertEquals((1.3), percentageChangeIndicator.getValue(4));
    }
}
