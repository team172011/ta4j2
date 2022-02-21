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
import org.ta4j.core.mocks.MockBarSeries;

import java.time.ZonedDateTime;

import static java.lang.Double.NaN;


public class LowestValueIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(1, 2, 3, 4, 3, 4, 5, 6, 4, 3, 2, 4, 3, 1);
    }

    @Test
    public void lowestValueIndicatorUsingBarCount5UsingClosePrice() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 5);
        assertEquals(1.0, lowestValue.getValue(1));
        assertEquals(1.0, lowestValue.getValue(2));
        assertEquals(1.0, lowestValue.getValue(3));
        assertEquals(1.0, lowestValue.getValue(4));
        assertEquals(2.0, lowestValue.getValue(5));
        assertEquals(3.0, lowestValue.getValue(6));
        assertEquals(3.0, lowestValue.getValue(7));
        assertEquals(3.0, lowestValue.getValue(8));
        assertEquals(3.0, lowestValue.getValue(9));
        assertEquals(2.0, lowestValue.getValue(10));
        assertEquals(2.0, lowestValue.getValue(11));
        assertEquals(2.0, lowestValue.getValue(12));

    }

    @Test
    public void lowestValueIndicatorValueShouldBeEqualsToFirstDataValue() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 5);
        assertEquals(1.0, lowestValue.getValue(0));
    }

    @Test
    public void lowestValueIndicatorWhenBarCountIsGreaterThanIndex() {
        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(data), 500);
        assertEquals(1.0, lowestValue.getValue(12));
    }

    @Test
    public void onlyNaNValues() {
        BaseBarSeries series = new BaseBarSeries("NaN test");
        for (long i = 0; i <= 10000; i++) {
            series.addBar(ZonedDateTime.now() .plusDays(i), NaN, NaN, NaN, NaN, 0);
        }

        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(series), 5);
        for (int i = series.getBeginIndex(); i <= series.getEndIndex(); i++) {
            assertEquals(NaN, lowestValue.getValue(i));
        }
    }

    @Test
    public void naNValuesInIntervall() {
        BaseBarSeries series = new BaseBarSeries("NaN test");
        for (long i = 0; i <= 10; i++) { // (NaN, 1, NaN, 2, NaN, 3, NaN, 4, ...)
            series.addBar(ZonedDateTime.now() .plusDays(i), NaN, NaN, NaN, NaN, 0);
        }

        LowestValueIndicator lowestValue = new LowestValueIndicator(new ClosePriceIndicator(series), 2);
        for (int i = series.getBeginIndex(); i <= series.getEndIndex(); i++) {
            if (i % 2 != 0) {
                assertEquals(series.getBar(i - 1).getClosePrice(), lowestValue.getValue(i));
            } else
                assertEquals(series.getBar(Math.max(0, i - 1)).getClosePrice(), lowestValue.getValue(i));
        }
    }
}
