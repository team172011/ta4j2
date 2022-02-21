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
package org.ta4j.core.indicators.ichimoku;

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.mocks.MockBar;


import java.util.List;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class IchimokuChikouSpanIndicatorTest extends AbstractIndicatorTest {

    private Bar bar(int i) {
        return new MockBar(i);
    }

    private BarSeries barSeries(int count) {
        final List<Bar> bars = IntStream.range(0, count).boxed().map(this::bar).collect(toList());
        return new BaseBarSeries(bars);
    }

    @Test
    public void testCalculateWithDefaultParam() {
        final BarSeries barSeries = barSeries(27);

        final IchimokuChikouSpanIndicator indicator = new IchimokuChikouSpanIndicator(barSeries);

        assertEquals((26), indicator.getValue(0));
        assertEquals(Double.NaN, indicator.getValue(1));
        assertEquals(Double.NaN, indicator.getValue(2));
        assertEquals(Double.NaN, indicator.getValue(3));
        assertEquals(Double.NaN, indicator.getValue(4));
        assertEquals(Double.NaN, indicator.getValue(5));
        assertEquals(Double.NaN, indicator.getValue(6));
        assertEquals(Double.NaN, indicator.getValue(7));
        assertEquals(Double.NaN, indicator.getValue(8));
        assertEquals(Double.NaN, indicator.getValue(9));
        assertEquals(Double.NaN, indicator.getValue(10));
        assertEquals(Double.NaN, indicator.getValue(11));
        assertEquals(Double.NaN, indicator.getValue(12));
        assertEquals(Double.NaN, indicator.getValue(13));
        assertEquals(Double.NaN, indicator.getValue(14));
        assertEquals(Double.NaN, indicator.getValue(15));
        assertEquals(Double.NaN, indicator.getValue(16));
        assertEquals(Double.NaN, indicator.getValue(17));
        assertEquals(Double.NaN, indicator.getValue(18));
        assertEquals(Double.NaN, indicator.getValue(19));
        assertEquals(Double.NaN, indicator.getValue(20));
        assertEquals(Double.NaN, indicator.getValue(21));
        assertEquals(Double.NaN, indicator.getValue(22));
        assertEquals(Double.NaN, indicator.getValue(23));
        assertEquals(Double.NaN, indicator.getValue(24));
        assertEquals(Double.NaN, indicator.getValue(25));
        assertEquals(Double.NaN, indicator.getValue(26));
    }

    @Test
    public void testCalculateWithSpecifiedValue() {
        final BarSeries barSeries = barSeries(11);

        final IchimokuChikouSpanIndicator indicator = new IchimokuChikouSpanIndicator(barSeries, 3);

        assertEquals((3), indicator.getValue(0));
        assertEquals((4), indicator.getValue(1));
        assertEquals((5), indicator.getValue(2));
        assertEquals((6), indicator.getValue(3));
        assertEquals((7), indicator.getValue(4));
        assertEquals((8), indicator.getValue(5));
        assertEquals((9), indicator.getValue(6));
        assertEquals((10), indicator.getValue(7));
        assertEquals(Double.NaN, indicator.getValue(8));
        assertEquals(Double.NaN, indicator.getValue(9));
        assertEquals(Double.NaN, indicator.getValue(10));
    }

}