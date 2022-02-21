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
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.mocks.MockBar;

import java.util.ArrayList;
import java.util.List;





public class WilliamsRIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;


    @Before
    public void setUp() {

        List<Bar> bars = new ArrayList<Bar>();
        bars.add(new MockBar(44.98, 45.05, 45.17, 44.96));
        bars.add(new MockBar(45.05, 45.10, 45.15, 44.99));
        bars.add(new MockBar(45.11, 45.19, 45.32, 45.11));
        bars.add(new MockBar(45.19, 45.14, 45.25, 45.04));
        bars.add(new MockBar(45.12, 45.15, 45.20, 45.10));
        bars.add(new MockBar(45.15, 45.14, 45.20, 45.10));
        bars.add(new MockBar(45.13, 45.10, 45.16, 45.07));
        bars.add(new MockBar(45.12, 45.15, 45.22, 45.10));
        bars.add(new MockBar(45.15, 45.22, 45.27, 45.14));
        bars.add(new MockBar(45.24, 45.43, 45.45, 45.20));
        bars.add(new MockBar(45.43, 45.44, 45.50, 45.39));
        bars.add(new MockBar(45.43, 45.55, 45.60, 45.35));
        bars.add(new MockBar(45.58, 45.55, 45.61, 45.39));

        data = new BaseBarSeries(bars);
    }

    @Test
    public void williamsRUsingBarCount5UsingClosePrice() {
        WilliamsRIndicator wr = new WilliamsRIndicator(new ClosePriceIndicator(data), 5, new HighPriceIndicator(data),
                new LowPriceIndicator(data));

        assertEquals(-47.2222, wr.getValue(4));
        assertEquals(-54.5454, wr.getValue(5));
        assertEquals(-78.5714, wr.getValue(6));
        assertEquals(-47.6190, wr.getValue(7));
        assertEquals(-25d, wr.getValue(8));
        assertEquals(-5.2632, wr.getValue(9));
        assertEquals(-13.9535, wr.getValue(10));

    }

    @Test
    public void williamsRUsingBarCount10UsingClosePrice() {
        WilliamsRIndicator wr = new WilliamsRIndicator(new ClosePriceIndicator(data), 10, new HighPriceIndicator(data),
                new LowPriceIndicator(data));

        assertEquals(-4.0816, wr.getValue(9));
        assertEquals(-11.7647, wr.getValue(10));
        assertEquals(-8.9286, wr.getValue(11));
        assertEquals(-10.5263, wr.getValue(12));

    }

    @Test
    public void valueLessThenBarCount() {
        WilliamsRIndicator wr = new WilliamsRIndicator(new ClosePriceIndicator(data), 100, new HighPriceIndicator(data),
                new LowPriceIndicator(data));

        assertEquals(-100d * (0.12 / 0.21), wr.getValue(0));
        assertEquals(-100d * (0.07 / 0.21), wr.getValue(1));
        assertEquals(-100d * (0.13 / 0.36), wr.getValue(2));
        assertEquals(-100d * (0.18 / 0.36), wr.getValue(3));
    }
}
