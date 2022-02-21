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
package org.ta4j.core.indicators.volume;

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;


public class PVIIndicatorTest extends AbstractIndicatorTest {


    @Test
    public void getValue() {

        List<Bar> bars = new ArrayList<Bar>();
        bars.add(new MockBar(1355.69, 2739));
        bars.add(new MockBar(1325.51, 3119));
        bars.add(new MockBar(1335.02, 3466));
        bars.add(new MockBar(1313.72, 2577));
        bars.add(new MockBar(1319.99, 2480));
        bars.add(new MockBar(1331.85, 2329));
        bars.add(new MockBar(1329.04, 2793));
        bars.add(new MockBar(1362.16, 3378));
        bars.add(new MockBar(1365.51, 2417));
        bars.add(new MockBar(1374.02, 1442));
        BarSeries series = new MockBarSeries(bars);

        PVIIndicator pvi = new PVIIndicator(series);
        assertEquals(1000, pvi.getValue(0));
        assertEquals(977.7383, pvi.getValue(1));
        assertEquals(984.7532, pvi.getValue(2));
        assertEquals(984.7532, pvi.getValue(3));
        assertEquals(984.7532, pvi.getValue(4));
        assertEquals(984.7532, pvi.getValue(5));
        assertEquals(982.6755, pvi.getValue(6));
        assertEquals(1007.164, pvi.getValue(7));
        assertEquals(1007.164, pvi.getValue(8));
        assertEquals(1007.164, pvi.getValue(9));
    }
}
