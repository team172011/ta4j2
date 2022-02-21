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

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;






public class WMAIndicatorTest extends AbstractIndicatorTest {

    @Test
    public void calculate() {
        MockBarSeries series = new MockBarSeries(1d, 2d, 3d, 4d, 5d, 6d);
        Indicator<Double> close = new ClosePriceIndicator(series);
        Indicator<Double> wmaIndicator = new WMAIndicator(close, 3);

        assertEquals(1, wmaIndicator.getValue(0));
        assertEquals(1.6667, wmaIndicator.getValue(1));
        assertEquals(2.3333, wmaIndicator.getValue(2));
        assertEquals(3.3333, wmaIndicator.getValue(3));
        assertEquals(4.3333, wmaIndicator.getValue(4));
        assertEquals(5.3333, wmaIndicator.getValue(5));
    }

    @Test
    public void wmaWithBarCountGreaterThanSeriesSize() {
        MockBarSeries series = new MockBarSeries(1d, 2d, 3d, 4d, 5d, 6d);
        Indicator<Double> close = new ClosePriceIndicator(series);
        Indicator<Double> wmaIndicator = new WMAIndicator(close, 55);

        assertEquals(1, wmaIndicator.getValue(0));
        assertEquals(1.6667, wmaIndicator.getValue(1));
        assertEquals(2.3333, wmaIndicator.getValue(2));
        assertEquals(3, wmaIndicator.getValue(3));
        assertEquals(3.6666, wmaIndicator.getValue(4));
        assertEquals(4.3333, wmaIndicator.getValue(5));
    }

    @Test
    public void wmaUsingBarCount9UsingClosePrice() {
        // Example from
        // http://traders.com/Documentation/FEEDbk_docs/2010/12/TradingIndexesWithHullMA.xls
        BarSeries data = new MockBarSeries(84.53, 87.39, 84.55, 82.83, 82.58, 83.74, 83.33, 84.57, 86.98,
                87.10, 83.11, 83.60, 83.66, 82.76, 79.22, 79.03, 78.18, 77.42, 74.65, 77.48, 76.87);

        WMAIndicator wma = new WMAIndicator(new ClosePriceIndicator(data), 9);
        assertEquals(84.4958, wma.getValue(8));
        assertEquals(85.0158, wma.getValue(9));
        assertEquals(84.6807, wma.getValue(10));
        assertEquals(84.5387, wma.getValue(11));
        assertEquals(84.4298, wma.getValue(12));
        assertEquals(84.1224, wma.getValue(13));
        assertEquals(83.1031, wma.getValue(14));
        assertEquals(82.1462, wma.getValue(15));
        assertEquals(81.1149, wma.getValue(16));
        assertEquals(80.0736, wma.getValue(17));
        assertEquals(78.6907, wma.getValue(18));
        assertEquals(78.1504, wma.getValue(19));
        assertEquals(77.6133, wma.getValue(20));
    }
}
