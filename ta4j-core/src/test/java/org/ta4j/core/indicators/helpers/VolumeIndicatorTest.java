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

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;


import static junit.framework.TestCase.assertEquals;



public class VolumeIndicatorTest extends AbstractIndicatorTest {


    @Test
    public void indicatorShouldRetrieveBarVolume() {
        BarSeries series = new MockBarSeries();
        VolumeIndicator volumeIndicator = new VolumeIndicator(series);
        for (int i = 0; i < 10; i++) {
            assertEquals(volumeIndicator.getValue(i), series.getBar(i).getVolume());
        }
    }

    @Test
    public void sumOfVolume() {
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(new MockBar(0, 10));
        bars.add(new MockBar(0, 11));
        bars.add(new MockBar(0, 12));
        bars.add(new MockBar(0, 13));
        bars.add(new MockBar(0, 150));
        bars.add(new MockBar(0, 155));
        bars.add(new MockBar(0, 160));
        VolumeIndicator volumeIndicator = new VolumeIndicator(new MockBarSeries(bars), 3);

        assertEquals(10, volumeIndicator.getValue(0));
        assertEquals(21, volumeIndicator.getValue(1));
        assertEquals(33, volumeIndicator.getValue(2));
        assertEquals(36, volumeIndicator.getValue(3));
        assertEquals(175, volumeIndicator.getValue(4));
        assertEquals(318, volumeIndicator.getValue(5));
        assertEquals(465, volumeIndicator.getValue(6));
    }
}