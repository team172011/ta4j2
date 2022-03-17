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

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

public class SumIndicatorTest extends AbstractIndicatorTest {

    private SumIndicator sumIndicator;

    @Before
    public void setUp() {
        BarSeries series = new BaseBarSeries();
        ConstantIndicator<Double> constantIndicator = new ConstantIndicator<>(series, (6d));
        FixedIndicator<Double> mockIndicator = new FixedIndicator<>(series, (-2.0), (0.00), (1.00), (2.53), (5.87),
                (6.00), (10.0));
        FixedIndicator<Double> mockIndicator2 = new FixedIndicator<>(series, 0d, 1d, 2d, (3d), (10d), (-42d), (-1337d));
        sumIndicator = new SumIndicator(constantIndicator, mockIndicator, mockIndicator2);
    }

    @Test
    public void getValue() {
        assertEquals(4.0, sumIndicator.getValue(0));
        assertEquals(7.0, sumIndicator.getValue(1));
        assertEquals(9.0, sumIndicator.getValue(2));
        assertEquals(11.53, sumIndicator.getValue(3));
        assertEquals(21.87, sumIndicator.getValue(4));
        assertEquals(-30.0, sumIndicator.getValue(5));
        assertEquals(-1321.0, sumIndicator.getValue(6));
    }
}
