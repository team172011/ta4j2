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
package org.ta4j.core;

import org.junit.Test;

import java.time.ZonedDateTime;

import static junit.framework.TestCase.assertEquals;

public class SeriesBuilderTest extends AbstractIndicatorTest {

    private final BaseBarSeriesBuilder seriesBuilder = new BaseBarSeriesBuilder();

    @Test
    public void testBuilder() {
        BarSeries defaultSeries = seriesBuilder.build(); // build a new empty unnamed bar series
        BarSeries defaultSeriesName = seriesBuilder.withName("default").build(); // build a new empty bar series using
                                                                                 // BigDecimal as delegate
        BarSeries series = seriesBuilder.withMaxBarCount(100).withName("useDouble Num").build();

        for (int j = 1000; j >= 0; j--) {
            double i = j;
            defaultSeries.addBar(ZonedDateTime.now().minusSeconds(j), i, i, i, i, i);
            defaultSeriesName.addBar(ZonedDateTime.now().minusSeconds(j), i, i, i, i, i);
            series.addBar(ZonedDateTime.now().minusSeconds(j), i, i, i, i, i);
        }

        assertEquals(0, defaultSeries.getBar(1000).getClosePrice(), EPS);
        assertEquals(1000, defaultSeries.getBar(0).getClosePrice(), EPS);
        assertEquals(defaultSeriesName.getName(), "default");
        assertEquals(99, series.getBar(0).getClosePrice(), EPS);
    }
}
