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
import org.ta4j.core.BaseBarSeriesBuilder;
import org.ta4j.core.TestUtils;
import org.ta4j.core.mocks.MockBar;

import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;


public class ATRIndicatorTest extends AbstractIndicatorTest {


    @Test
    public void testDummy() throws Exception {
        BarSeries series = new BaseBarSeriesBuilder().build();
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(5), 0, 12, 15, 8, 0, 0, 0));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(4), 0, 8, 11, 6, 0, 0, 0));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(3), 0, 15, 17, 14, 0, 0, 0));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(2), 0, 15, 17, 14, 0, 0, 0));
        series.addBar(new MockBar(ZonedDateTime.now().minusSeconds(1), 0, 0, 0, 2, 0, 0, 0));
        ATRIndicator indicator = new ATRIndicator(series, 3);

        assertEquals(7d, indicator.getValue(0), TestUtils.GENERAL_OFFSET);
        assertEquals(6d / 3 + (1 - 1d / 3) * indicator.getValue(0), indicator.getValue(1),
                TestUtils.GENERAL_OFFSET);
        assertEquals(9d / 3 + (1 - 1d / 3) * indicator.getValue(1), indicator.getValue(2),
                TestUtils.GENERAL_OFFSET);
        assertEquals(3d / 3 + (1 - 1d / 3) * indicator.getValue(2), indicator.getValue(3),
                TestUtils.GENERAL_OFFSET);
        assertEquals(15d / 3 + (1 - 1d / 3) * indicator.getValue(3), indicator.getValue(4),
                TestUtils.GENERAL_OFFSET);
    }


}
