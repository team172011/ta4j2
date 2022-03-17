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

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;

public class IchimokuIndicatorTest extends AbstractIndicatorTest {

    protected BarSeries data;

    @Before
    public void setUp() {
        final List<Bar> bars = new ArrayList<>();
        bars.add(new MockBar(44.98, 45.05, 45.17, 44.96));
        bars.add(new MockBar(45.05, 45.10, 45.15, 44.99));
        bars.add(new MockBar(45.11, 45.19, 45.32, 45.11));
        bars.add(new MockBar(45.19, 45.14, 45.25, 45.04));
        bars.add(new MockBar(45.12, 45.15, 45.20, 45.10));
        bars.add(new MockBar(45.15, 45.14, 45.20, 45.10));
        bars.add(new MockBar(45.13, 45.10, 45.16, 45.07));
        bars.add(new MockBar(45.12, 45.15, 45.22, 45.10));
        bars.add(new MockBar(45.15, 45.22, 45.27, 45.14));
        bars.add(new MockBar(45.24, 45.43, 45.45, 45.20));
        bars.add(new MockBar(45.43, 45.44, 45.50, 45.39));
        bars.add(new MockBar(45.43, 45.55, 45.60, 45.35));
        bars.add(new MockBar(45.58, 45.55, 45.61, 45.39));
        bars.add(new MockBar(45.45, 45.01, 45.55, 44.80));
        bars.add(new MockBar(45.03, 44.23, 45.04, 44.17));
        bars.add(new MockBar(44.23, 43.95, 44.29, 43.81));
        bars.add(new MockBar(43.91, 43.08, 43.99, 43.08));
        bars.add(new MockBar(43.07, 43.55, 43.65, 43.06));
        bars.add(new MockBar(43.56, 43.95, 43.99, 43.53));
        bars.add(new MockBar(43.93, 44.47, 44.58, 43.93));
        data = new MockBarSeries(bars);
    }

    @Test
    public void ichimoku() {
        IchimokuTenkanSenIndicator tenkanSen = new IchimokuTenkanSenIndicator(data, 3);
        IchimokuKijunSenIndicator kijunSen = new IchimokuKijunSenIndicator(data, 5);
        IchimokuSenkouSpanAIndicator senkouSpanA = new IchimokuSenkouSpanAIndicator(data, tenkanSen, kijunSen, 5);
        IchimokuSenkouSpanBIndicator senkouSpanB = new IchimokuSenkouSpanBIndicator(data, 9, 5);
        final int chikouSpanTimeDelay = 5;
        IchimokuChikouSpanIndicator chikouSpan = new IchimokuChikouSpanIndicator(data, chikouSpanTimeDelay);

        assertEquals(45.155, tenkanSen.getValue(3));
        assertEquals(45.18, tenkanSen.getValue(4));
        assertEquals(45.145, tenkanSen.getValue(5));
        assertEquals(45.135, tenkanSen.getValue(6));
        assertEquals(45.145, tenkanSen.getValue(7));
        assertEquals(45.17, tenkanSen.getValue(8));
        assertEquals(44.06, tenkanSen.getValue(16));
        assertEquals(43.675, tenkanSen.getValue(17));
        assertEquals(43.525, tenkanSen.getValue(18));

        assertEquals(45.14, kijunSen.getValue(3));
        assertEquals(45.14, kijunSen.getValue(4));
        assertEquals(45.155, kijunSen.getValue(5));
        assertEquals(45.18, kijunSen.getValue(6));
        assertEquals(45.145, kijunSen.getValue(7));
        assertEquals(45.17, kijunSen.getValue(8));
        assertEquals(44.345, kijunSen.getValue(16));
        assertEquals(44.305, kijunSen.getValue(17));
        assertEquals(44.05, kijunSen.getValue(18));

        assertEquals(Double.NaN, senkouSpanA.getValue(3));
        assertEquals(45.065, senkouSpanA.getValue(4));
        assertEquals(45.1475, senkouSpanA.getValue(7));
        assertEquals(45.16, senkouSpanA.getValue(8));
        assertEquals(45.15, senkouSpanA.getValue(9));
        assertEquals(45.1575, senkouSpanA.getValue(10));
        assertEquals(45.4275, senkouSpanA.getValue(16));
        assertEquals(45.205, senkouSpanA.getValue(17));
        assertEquals(44.89, senkouSpanA.getValue(18));

        assertEquals(Double.NaN, senkouSpanB.getValue(3));
        assertEquals(45.065, senkouSpanB.getValue(4));
        assertEquals(45.065, senkouSpanB.getValue(5));
        assertEquals(45.14, senkouSpanB.getValue(6));
        assertEquals(45.14, senkouSpanB.getValue(7));
        assertEquals(45.22, senkouSpanB.getValue(13));
        assertEquals(45.34, senkouSpanB.getValue(16));
        assertEquals(45.205, senkouSpanB.getValue(17));
        assertEquals(44.89, senkouSpanB.getValue(18));

        assertEquals(data.getBar(chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(0));
        assertEquals(data.getBar(1 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(1));
        assertEquals(data.getBar(2 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(2));
        assertEquals(data.getBar(3 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(3));
        assertEquals(data.getBar(4 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(4));
        assertEquals(data.getBar(5 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(5));
        assertEquals(data.getBar(6 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(6));
        assertEquals(data.getBar(7 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(7));
        assertEquals(data.getBar(8 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(8));
        assertEquals(data.getBar(9 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(9));
        assertEquals(data.getBar(10 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(10));
        assertEquals(data.getBar(11 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(11));
        assertEquals(data.getBar(12 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(12));
        assertEquals(data.getBar(13 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(13));
        assertEquals(data.getBar(14 + chikouSpanTimeDelay).getClosePrice(), chikouSpan.getValue(14));
        assertEquals(Double.NaN, chikouSpan.getValue(15));
        assertEquals(Double.NaN, chikouSpan.getValue(16));
        assertEquals(Double.NaN, chikouSpan.getValue(17));
        assertEquals(Double.NaN, chikouSpan.getValue(18));
        assertEquals(Double.NaN, chikouSpan.getValue(19));
    }
}
