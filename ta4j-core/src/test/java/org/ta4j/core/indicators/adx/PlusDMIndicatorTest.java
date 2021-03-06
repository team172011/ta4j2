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
package org.ta4j.core.indicators.adx;

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;





public class PlusDMIndicatorTest extends AbstractIndicatorTest {

    @Test
    public void zeroDirectionalMovement() {
        MockBar yesterdayBar = new MockBar(0, 0, 10, 2);
        MockBar todayBar = new MockBar(0, 0, 6, 6);
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(yesterdayBar);
        bars.add(todayBar);
        MockBarSeries series = new MockBarSeries(bars);
        PlusDMIndicator dup = new PlusDMIndicator(series);
        assertEquals(0, dup.getValue(1));
    }

    @Test
    public void zeroDirectionalMovement2() {
        MockBar yesterdayBar = new MockBar(0, 0, 6, 12);
        MockBar todayBar = new MockBar(0, 0, 12, 6);
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(yesterdayBar);
        bars.add(todayBar);
        MockBarSeries series = new MockBarSeries(bars);
        PlusDMIndicator dup = new PlusDMIndicator(series);
        assertEquals(0, dup.getValue(1));
    }

    @Test
    public void zeroDirectionalMovement3() {
        MockBar yesterdayBar = new MockBar(0, 0, 6, 20);
        MockBar todayBar = new MockBar(0, 0, 12, 4);
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(yesterdayBar);
        bars.add(todayBar);
        MockBarSeries series = new MockBarSeries(bars);
        PlusDMIndicator dup = new PlusDMIndicator(series);
        assertEquals(0, dup.getValue(1));
    }

    @Test
    public void positiveDirectionalMovement() {
        MockBar yesterdayBar = new MockBar(0, 0, 6, 6);
        MockBar todayBar = new MockBar(0, 0, 12, 4);
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(yesterdayBar);
        bars.add(todayBar);
        MockBarSeries series = new MockBarSeries(bars);
        PlusDMIndicator dup = new PlusDMIndicator(series);
        assertEquals(6, dup.getValue(1));
    }
}
