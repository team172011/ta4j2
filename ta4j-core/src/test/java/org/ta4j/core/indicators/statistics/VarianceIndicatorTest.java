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
package org.ta4j.core.indicators.statistics;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;






public class VarianceIndicatorTest extends AbstractIndicatorTest {
    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 0, 9);
    }

    @Test
    public void varianceUsingBarCount4UsingClosePrice() {
        VarianceIndicator var = new VarianceIndicator(new ClosePriceIndicator(data), 4);

        assertEquals(0, var.getValue(0));
        assertEquals(0.25, var.getValue(1));
        assertEquals(2.0 / 3, var.getValue(2));
        assertEquals(1.25, var.getValue(3));
        assertEquals(0.5, var.getValue(4));
        assertEquals(0.25, var.getValue(5));
        assertEquals(0.5, var.getValue(6));
        assertEquals(0.5, var.getValue(7));
        assertEquals(0.5, var.getValue(8));
        assertEquals(3.5, var.getValue(9));
        assertEquals(10.5, var.getValue(10));
    }

    @Test
    public void firstValueShouldBeZero() {
        VarianceIndicator var = new VarianceIndicator(new ClosePriceIndicator(data), 4);
        assertEquals(0, var.getValue(0));
    }

    @Test
    public void varianceShouldBeZeroWhenBarCountIs1() {
        VarianceIndicator var = new VarianceIndicator(new ClosePriceIndicator(data), 1);
        assertEquals(0, var.getValue(3));
        assertEquals(0, var.getValue(8));
    }

    @Test
    public void varianceUsingBarCount2UsingClosePrice() {
        VarianceIndicator var = new VarianceIndicator(new ClosePriceIndicator(data), 2);

        assertEquals(0, var.getValue(0));
        assertEquals(0.25, var.getValue(1));
        assertEquals(0.25, var.getValue(2));
        assertEquals(0.25, var.getValue(3));
        assertEquals(2.25, var.getValue(9));
        assertEquals(20.25, var.getValue(10));
    }
}
