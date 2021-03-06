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
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.ta4j.core.TestUtils.assertIndicatorEquals;


public class MMAIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(64.75, 63.79, 63.73, 63.73, 63.55, 63.19, 63.91, 63.85, 62.95, 63.37,
                61.33, 61.51);
    }

    @Test
    public void firstValueShouldBeEqualsToFirstDataValue() throws Exception {
        Indicator<Double> actualIndicator = new MMAIndicator(new ClosePriceIndicator(data), 1);
        assertEquals(64.75, actualIndicator.getValue(0), TestUtils.GENERAL_OFFSET);
    }

    @Test
    public void mmaUsingBarCount10UsingClosePrice() throws Exception {
        Indicator<Double> actualIndicator = new MMAIndicator(new ClosePriceIndicator(data), 10);
        assertEquals(63.9983, actualIndicator.getValue(9), TestUtils.GENERAL_OFFSET);
        assertEquals(63.7315, actualIndicator.getValue(10), TestUtils.GENERAL_OFFSET);
        assertEquals(63.5093, actualIndicator.getValue(11), TestUtils.GENERAL_OFFSET);
    }

    @Test
    public void stackOverflowError() throws Exception {
        List<Bar> bigListOfBars = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            bigListOfBars.add(new MockBar((double) i));
        }
        MockBarSeries bigSeries = new MockBarSeries(bigListOfBars);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(bigSeries);
        Indicator<Double> actualIndicator = new MMAIndicator(closePrice, 10);
        // if a StackOverflowError is thrown here, then the RecursiveCachedIndicator
        // does not work as intended.
        assertEquals(9990.0, actualIndicator.getValue(9999), TestUtils.GENERAL_OFFSET);
    }
}
