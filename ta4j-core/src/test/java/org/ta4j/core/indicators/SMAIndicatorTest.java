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
package org.ta4j.core.indicators;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.*;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;



import static org.junit.Assert.assertEquals;
import static org.ta4j.core.TestUtils.assertIndicatorEquals;



public class SMAIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2);
    }

    @Test
    public void usingBarCount3UsingClosePrice() {
        Indicator<Double> indicator = new SMAIndicator(new ClosePriceIndicator(data), 3);

        assertEquals(1, indicator.getValue(0), EPS);
        assertEquals(1.5, indicator.getValue(1), EPS);
        assertEquals(2, indicator.getValue(2), EPS);
        assertEquals(3, indicator.getValue(3), EPS);
        assertEquals(10d / 3, indicator.getValue(4), EPS);
        assertEquals(11d / 3, indicator.getValue(5), EPS);
        assertEquals(4, indicator.getValue(6), EPS);
        assertEquals(13d / 3, indicator.getValue(7), EPS);
        assertEquals(4, indicator.getValue(8), EPS);
        assertEquals(10d / 3, indicator.getValue(9), EPS);
        assertEquals(10d / 3, indicator.getValue(10), EPS);
        assertEquals(10d / 3, indicator.getValue(11), EPS);
        assertEquals(3, indicator.getValue(12), EPS);
    }

    @Test
    public void whenBarCountIs1ResultShouldBeIndicatorValue() throws Exception {
        Indicator<Double> indicator = new SMAIndicator(new ClosePriceIndicator(data), 1);
        for (int i = 0; i < data.getBarCount(); i++) {
            assertEquals(data.getBar(i).getClosePrice(), indicator.getValue(i), EPS);
        }
    }


}
