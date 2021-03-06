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
import org.ta4j.core.mocks.MockBarSeries;






public class LossIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(1, 2, 3, 4, 3, 4, 7, 4, 3, 3, 5, 3, 2);
    }

    @Test
    public void lossUsingClosePrice() {
        LossIndicator loss = new LossIndicator(new ClosePriceIndicator(data));
        assertEquals(0, loss.getValue(0));
        assertEquals(0, loss.getValue(1));
        assertEquals(0, loss.getValue(2));
        assertEquals(0, loss.getValue(3));
        assertEquals(1, loss.getValue(4));
        assertEquals(0, loss.getValue(5));
        assertEquals(0, loss.getValue(6));
        assertEquals(3, loss.getValue(7));
        assertEquals(1, loss.getValue(8));
        assertEquals(0, loss.getValue(9));
        assertEquals(0, loss.getValue(10));
        assertEquals(2, loss.getValue(11));
        assertEquals(1, loss.getValue(12));
    }
}
