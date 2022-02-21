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

public class StandardDeviationIndicatorTest extends AbstractIndicatorTest {
    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 0, 9);
    }

    @Test
    public void standardDeviationUsingBarCount4UsingClosePrice() {
        StandardDeviationIndicator sdv = new StandardDeviationIndicator(new ClosePriceIndicator(data), 4);

        assertEquals(0, sdv.getValue(0));
        assertEquals(Math.sqrt(0.25), sdv.getValue(1));
        assertEquals(Math.sqrt(2.0 / 3), sdv.getValue(2));
        assertEquals(Math.sqrt(1.25), sdv.getValue(3));
        assertEquals(Math.sqrt(0.5), sdv.getValue(4));
        assertEquals(Math.sqrt(0.25), sdv.getValue(5));
        assertEquals(Math.sqrt(0.5), sdv.getValue(6));
        assertEquals(Math.sqrt(0.5), sdv.getValue(7));
        assertEquals(Math.sqrt(0.5), sdv.getValue(8));
        assertEquals(Math.sqrt(3.5), sdv.getValue(9));
        assertEquals(Math.sqrt(10.5), sdv.getValue(10));
    }

    @Test
    public void standardDeviationShouldBeZeroWhenBarCountIs1() {
        StandardDeviationIndicator sdv = new StandardDeviationIndicator(new ClosePriceIndicator(data), 1);
        assertEquals(0, sdv.getValue(3));
        assertEquals(0, sdv.getValue(8));
    }
}
