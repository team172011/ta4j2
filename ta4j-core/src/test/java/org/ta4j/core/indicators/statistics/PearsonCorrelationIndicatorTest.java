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
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.mocks.MockBar;

import java.time.ZonedDateTime;

public class PearsonCorrelationIndicatorTest extends AbstractIndicatorTest {

    private Indicator<Double> close, volume;

    @Before
    public void setUp() {
        BarSeries data = new BaseBarSeriesBuilder().build();
        int i = 20;
        // close, volume
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 6, 100d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 7, 105d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 9, 130d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 12, 160d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 11, 150d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 10, 130d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 11, 95d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 13, 120d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 15, 180d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 12, 160d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 8, 150d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 4, 200d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 3, 150d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 4, 85d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 3, 70d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 5, 90d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 8, 100d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 9, 95d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i--), 11, 110d));
        data.addBar(new MockBar(ZonedDateTime.now().minusSeconds(i), 10, 95d));

        close = new ClosePriceIndicator(data);
        volume = new VolumeIndicator(data, 2);
    }

    @Test
    public void test() {
        PearsonCorrelationIndicator coef = new PearsonCorrelationIndicator(close, volume, 5);

        assertEquals(0.94947469058476818628408908843839, coef.getValue(1));
        assertEquals(0.9640797490298872, coef.getValue(2));
        assertEquals(0.9666189661412724, coef.getValue(3));
        assertEquals(0.9219, coef.getValue(4));
        assertEquals(0.9205, coef.getValue(5));
        assertEquals(0.4565, coef.getValue(6));
        assertEquals(-0.4622, coef.getValue(7));
        assertEquals(0.05747, coef.getValue(8));
        assertEquals(0.1442, coef.getValue(9));
        assertEquals(-0.1263, coef.getValue(10));
        assertEquals(-0.5345, coef.getValue(11));
        assertEquals(-0.7275, coef.getValue(12));
        assertEquals(0.1676, coef.getValue(13));
        assertEquals(0.2506, coef.getValue(14));
        assertEquals(-0.2938, coef.getValue(15));
        assertEquals(-0.3586, coef.getValue(16));
        assertEquals(0.1713, coef.getValue(17));
        assertEquals(0.9841, coef.getValue(18));
        assertEquals(0.9799, coef.getValue(19));
    }
}
