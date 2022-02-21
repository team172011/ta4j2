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
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.mocks.MockBar;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;


public class CovarianceIndicatorTest extends AbstractIndicatorTest {

    private ClosePriceIndicator close;
    private VolumeIndicator volume;

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
    public void usingBarCount5UsingClosePriceAndVolume() {
        CovarianceIndicator covar = new CovarianceIndicator(close, volume, 5);

        assertEquals(0, covar.getValue(0), EPS);
        assertEquals(26.25, covar.getValue(1), EPS);
        assertEquals(63.3333, covar.getValue(2), EPS);
        assertEquals(143.75, covar.getValue(3), EPS);
        assertEquals(156, covar.getValue(4), EPS);
        assertEquals(60.8, covar.getValue(5), EPS);
        assertEquals(15.2, covar.getValue(6), EPS);
        assertEquals(-17.6, covar.getValue(7), EPS);
        assertEquals(4, covar.getValue(8), EPS);
        assertEquals(11.6, covar.getValue(9), EPS);
        assertEquals(-14.4, covar.getValue(10), EPS);
        assertEquals(-100.2, covar.getValue(11), EPS);
        assertEquals(-70.0, covar.getValue(12), EPS);
        assertEquals(24.6, covar.getValue(13), EPS);
        assertEquals(35.0, covar.getValue(14), EPS);
        assertEquals(-19.0, covar.getValue(15), EPS);
        assertEquals(-47.8, covar.getValue(16), EPS);
        assertEquals(11.4, covar.getValue(17), EPS);
        assertEquals(55.8, covar.getValue(18), EPS);
        assertEquals(33.4, covar.getValue(19), EPS);
    }

    @Test
    public void firstValueShouldBeZero() {
        CovarianceIndicator covar = new CovarianceIndicator(close, volume, 5);
        assertEquals(0, covar.getValue(0), EPS);
    }

    @Test
    public void shouldBeZeroWhenBarCountIs1() {
        CovarianceIndicator covar = new CovarianceIndicator(close, volume, 1);
        assertEquals(0, covar.getValue(3), EPS);
        assertEquals(0, covar.getValue(8), EPS);
    }
}
