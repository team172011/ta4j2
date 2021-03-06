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
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;



import static junit.framework.TestCase.assertEquals;



public class ZLEMAIndicatorTest extends AbstractIndicatorTest {

    private BarSeries data;

    @Before
    public void setUp() {
        data = new MockBarSeries(10, 15, 20, 18, 17, 18, 15, 12, 10, 8, 5, 2);
    }

    @Test
    public void ZLEMAUsingBarCount10UsingClosePrice() {
        ZLEMAIndicator zlema = new ZLEMAIndicator(new ClosePriceIndicator(data), 10);

        assertEquals(11.9091, zlema.getValue(9));
        assertEquals(8.8347, zlema.getValue(10));
        assertEquals(5.7739, zlema.getValue(11));
    }

    @Test
    public void ZLEMAFirstValueShouldBeEqualsToFirstDataValue() {
        ZLEMAIndicator zlema = new ZLEMAIndicator(new ClosePriceIndicator(data), 10);
        assertEquals(10, zlema.getValue(0));
    }

    @Test
    public void valuesLessThanBarCountMustBeEqualsToSMAValues() {
        ZLEMAIndicator zlema = new ZLEMAIndicator(new ClosePriceIndicator(data), 10);
        SMAIndicator sma = new SMAIndicator(new ClosePriceIndicator(data), 10);

        for (int i = 0; i < 9; i++) {
            assertEquals(sma.getValue(i), zlema.getValue(i));
        }
    }

    @Test
    public void smallBarCount() {
        ZLEMAIndicator zlema = new ZLEMAIndicator(new ClosePriceIndicator(data), 1);
        assertEquals(10, zlema.getValue(0));
    }
}
