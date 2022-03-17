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

public class StandardErrorIndicatorTest extends AbstractIndicatorTest {
    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(10, 20, 30, 40, 50, 40, 40, 50, 40, 30, 20, 10);
    }

    @Test
    public void usingBarCount5UsingClosePrice() {
        StandardErrorIndicator se = new StandardErrorIndicator(new ClosePriceIndicator(data), 5);

        assertEquals(0, se.getValue(0));
        assertEquals(3.5355, se.getValue(1));
        assertEquals(4.714, se.getValue(2));
        assertEquals(5.5902, se.getValue(3));
        assertEquals(6.3246, se.getValue(4));
        assertEquals(4.5607, se.getValue(5));
        assertEquals(2.8284, se.getValue(6));
        assertEquals(2.1909, se.getValue(7));
        assertEquals(2.1909, se.getValue(8));
        assertEquals(2.8284, se.getValue(9));
        assertEquals(4.5607, se.getValue(10));
        assertEquals(6.3246, se.getValue(11));
    }

    @Test
    public void shouldBeZeroWhenBarCountIs1() {
        StandardErrorIndicator se = new StandardErrorIndicator(new ClosePriceIndicator(data), 1);
        assertEquals(0, se.getValue(1));
        assertEquals(0, se.getValue(3));
    }
}
