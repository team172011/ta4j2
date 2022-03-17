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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.rules.FixedRule;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.ta4j.core.AbstractIndicatorTest.EPS;

public class BarSeriesTest extends AbstractIndicatorTest {

    private BarSeries defaultSeries;
    private BarSeries subSeries;
    private BarSeries emptySeries;
    private List<Bar> bars;
    private String defaultName;

    @Before
    public void setUp() {
        bars = new LinkedList<>();
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 13, 0, 0, 0, 0, ZoneId.systemDefault()), 1d));
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 14, 0, 0, 0, 0, ZoneId.systemDefault()), 2d));
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 15, 0, 0, 0, 0, ZoneId.systemDefault()), 3d));
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 20, 0, 0, 0, 0, ZoneId.systemDefault()), 4d));
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 25, 0, 0, 0, 0, ZoneId.systemDefault()), 5d));
        bars.add(new MockBar(ZonedDateTime.of(2014, 6, 30, 0, 0, 0, 0, ZoneId.systemDefault()), 6d));

        defaultName = "Series Name";

        defaultSeries = new BaseBarSeriesBuilder().withName(defaultName).withBars(bars).build();

        subSeries = defaultSeries.getSubSeries(2, 5);
        emptySeries = new BaseBarSeriesBuilder().build();

        Strategy strategy = new BaseStrategy(new FixedRule(0, 2, 3, 6), new FixedRule(1, 4, 7, 8));
        strategy.setUnstablePeriod(2); // Strategy would need a real test class

    }

    /**
     * Tests if the addBar(bar, boolean) function works correct.
     */
    @Test
    public void replaceBarTest() {
        BarSeries series = new BaseBarSeriesBuilder().build();
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()), 1d), true);
        assertEquals(1, series.getBarCount());
        assertEquals(series.getLastBar().getClosePrice(), 1d, EPS);
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(1), 2d), false);
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(2), 3d), false);
        assertEquals(3, series.getBarCount());
        assertEquals(series.getLastBar().getClosePrice(), 3d, EPS);
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(3), 4d), true);
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(4), 5d), true);
        assertEquals(3, series.getBarCount());
        assertEquals(series.getLastBar().getClosePrice(), 5d, EPS);
    }

    @Test
    public void getEndGetBeginGetBarCountIsEmptyTest() {

        // Default series
        assertEquals(0, defaultSeries.getBeginIndex());
        assertEquals(bars.size() - 1, defaultSeries.getEndIndex());
        assertEquals(bars.size(), defaultSeries.getBarCount());
        assertFalse(defaultSeries.isEmpty());
        // Constrained series
        assertEquals(0, subSeries.getBeginIndex());
        assertEquals(2, subSeries.getEndIndex());
        assertEquals(3, subSeries.getBarCount());
        assertFalse(subSeries.isEmpty());
        // Empty series
        assertEquals(-1, emptySeries.getBeginIndex());
        assertEquals(-1, emptySeries.getEndIndex());
        assertEquals(0, emptySeries.getBarCount());
        assertTrue(emptySeries.isEmpty());
    }

    @Test
    public void getBarDataTest() {
        // Default series
        assertEquals(bars, defaultSeries.getBarData());
        // Constrained series
        assertNotEquals(bars, subSeries.getBarData());
        // Empty series
        assertEquals(0, emptySeries.getBarData().size());
    }

    @Test
    public void getSeriesPeriodDescriptionTest() {
        // Default series
        assertTrue(defaultSeries.getSeriesPeriodDescription()
                .endsWith(bars.get(defaultSeries.getEndIndex()).getEndTime().format(DateTimeFormatter.ISO_DATE_TIME)));
        assertTrue(defaultSeries.getSeriesPeriodDescription()
                .startsWith(
                        bars.get(defaultSeries.getBeginIndex()).getEndTime().format(DateTimeFormatter.ISO_DATE_TIME)));
        // Constrained series
        assertTrue(subSeries.getSeriesPeriodDescription()
                .endsWith(bars.get(4).getEndTime().format(DateTimeFormatter.ISO_DATE_TIME)));
        assertTrue(subSeries.getSeriesPeriodDescription()
                .startsWith(bars.get(2).getEndTime().format(DateTimeFormatter.ISO_DATE_TIME)));
        // Empty series
        assertEquals("", emptySeries.getSeriesPeriodDescription());
    }

    @Test
    public void getNameTest() {
        assertEquals(defaultName, defaultSeries.getName());
        assertEquals(defaultName, subSeries.getName());
    }

    @Test
    public void getBarWithRemovedIndexOnMovingSeriesShouldReturnFirstRemainingBarTest() {
        Bar bar = defaultSeries.getBar(4);
        defaultSeries.setMaximumBarCount(2);

        assertSame(bar, defaultSeries.getBar(0));
        assertSame(bar, defaultSeries.getBar(1));
        assertSame(bar, defaultSeries.getBar(2));
        assertSame(bar, defaultSeries.getBar(3));
        assertSame(bar, defaultSeries.getBar(4));
        assertNotSame(bar, defaultSeries.getBar(5));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getBarOnMovingAndEmptySeriesShouldThrowExceptionTest() {
        defaultSeries.setMaximumBarCount(2);
        bars.clear(); // Should not be used like this
        defaultSeries.getBar(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getBarWithNegativeIndexShouldThrowExceptionTest() {
        defaultSeries.getBar(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getBarWithIndexGreaterThanBarCountShouldThrowExceptionTest() {
        defaultSeries.getBar(10);
    }

    @Test
    public void getBarOnMovingSeriesTest() {
        Bar bar = defaultSeries.getBar(4);
        defaultSeries.setMaximumBarCount(2);
        assertEquals(bar, defaultSeries.getBar(4));
    }

    @Test
    public void subSeriesCreationTest() {
        BarSeries subSeries = defaultSeries.getSubSeries(2, 5);
        assertEquals(3, subSeries.getBarCount());
        assertEquals(defaultSeries.getName(), subSeries.getName());
        assertEquals(0, subSeries.getBeginIndex());
        assertEquals(defaultSeries.getBeginIndex(), subSeries.getBeginIndex());
        assertEquals(2, subSeries.getEndIndex());
        assertNotEquals(defaultSeries.getEndIndex(), subSeries.getEndIndex());
        assertEquals(3, subSeries.getBarCount());

        subSeries = defaultSeries.getSubSeries(0, 1000);
        assertEquals(0, subSeries.getBeginIndex());
        assertEquals(defaultSeries.getBarCount(), subSeries.getBarCount());
        assertEquals(defaultSeries.getEndIndex(), subSeries.getEndIndex());
    }

    @Test(expected = IllegalArgumentException.class)
    public void subSeriesCreationWithNegativeIndexTest() {
        defaultSeries.getSubSeries(-1000, 1000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subSeriesWithWrongArgumentsTest() {
        defaultSeries.getSubSeries(10, 9);
    }

    @Test
    public void maximumBarCountOnConstrainedSeriesShouldNotThrowExceptionTest() {
        try {
            subSeries.setMaximumBarCount(10);
        } catch (Exception e) {
            Assert.fail("setMaximumBarCount onConstrained series should not throw Exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeMaximumBarCountShouldThrowExceptionTest() {
        defaultSeries.setMaximumBarCount(-1);
    }

    @Test
    public void setMaximumBarCountTest() {
        // Before
        assertEquals(0, defaultSeries.getBeginIndex());
        assertEquals(bars.size() - 1, defaultSeries.getEndIndex());
        assertEquals(bars.size(), defaultSeries.getBarCount());

        defaultSeries.setMaximumBarCount(3);

        // After
        assertEquals(0, defaultSeries.getBeginIndex());
        assertEquals(5, defaultSeries.getEndIndex());
        assertEquals(3, defaultSeries.getBarCount());
    }

    @Test(expected = NullPointerException.class)
    public void addNullBarShouldThrowExceptionTest() {
        defaultSeries.addBar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addBarWithEndTimePriorToSeriesEndTimeShouldThrowExceptionTest() {
        defaultSeries.addBar(new MockBar(ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()), 99d));
    }

    @Test
    public void addBarTest() {
        defaultSeries = new BaseBarSeriesBuilder().build();
        Bar bar1 = new MockBar(ZonedDateTime.of(2014, 6, 13, 0, 0, 0, 0, ZoneId.systemDefault()), 1d);
        Bar bar2 = new MockBar(ZonedDateTime.of(2014, 6, 14, 0, 0, 0, 0, ZoneId.systemDefault()), 2d);

        assertEquals(0, defaultSeries.getBarCount());
        assertEquals(-1, defaultSeries.getBeginIndex());
        assertEquals(-1, defaultSeries.getEndIndex());

        defaultSeries.addBar(bar1);
        assertEquals(1, defaultSeries.getBarCount());
        assertEquals(0, defaultSeries.getBeginIndex());
        assertEquals(0, defaultSeries.getEndIndex());

        defaultSeries.addBar(bar2);
        assertEquals(2, defaultSeries.getBarCount());
        assertEquals(0, defaultSeries.getBeginIndex());
        assertEquals(1, defaultSeries.getEndIndex());
    }

    @Test
    public void addPriceTest() {
        ClosePriceIndicator cp = new ClosePriceIndicator(defaultSeries);
        HighPriceIndicator mxPrice = new HighPriceIndicator(defaultSeries);
        LowPriceIndicator mnPrice = new LowPriceIndicator(defaultSeries);
        PreviousValueIndicator prevValue = new PreviousValueIndicator(cp, 1);

        Double prevClose = defaultSeries.getBar(defaultSeries.getEndIndex() - 1).getClosePrice();
        Double currentMin = mnPrice.getValue(defaultSeries.getEndIndex());
        Double currentClose = cp.getValue(defaultSeries.getEndIndex());

        assertEquals(currentClose, defaultSeries.getLastBar().getClosePrice());
        defaultSeries.addPrice(100);
        assertEquals(100d, cp.getValue(defaultSeries.getEndIndex()), EPS); // adding1 is new close
        assertEquals(100d, mxPrice.getValue(defaultSeries.getEndIndex()), EPS); // adding1 also new max
        assertEquals(currentMin, mnPrice.getValue(defaultSeries.getEndIndex()), EPS); // min stays same
        assertEquals(prevClose, prevValue.getValue(defaultSeries.getEndIndex()), EPS); // previous close stays

        defaultSeries.addPrice(0);
        assertEquals(0, cp.getValue(defaultSeries.getEndIndex()), EPS); // adding2 is new close
        assertEquals(100, mxPrice.getValue(defaultSeries.getEndIndex()), EPS); // max stays 100
        assertEquals(0, mnPrice.getValue(defaultSeries.getEndIndex()), EPS); // min is new adding2
        assertEquals(prevClose, prevValue.getValue(defaultSeries.getEndIndex()), EPS); // previous close stays
    }

    /**
     * Tests if the {@link BaseBarSeries#addTrade(long, double )} method works
     * correct.
     */
    @Test
    public void addTradeTest() {
        BarSeries series = new BaseBarSeriesBuilder().build();
        series.addBar(new MockBar(ZonedDateTime.now(ZoneId.systemDefault()), 1d));
        series.addTrade(200, 11.5);
        assertEquals(200, series.getLastBar().getVolume());
        assertEquals(11.5d, series.getLastBar().getClosePrice(), EPS);
        series.addTrade(200, 100d);
        assertEquals(400, series.getLastBar().getVolume());
        assertEquals(100d, series.getLastBar().getClosePrice(), EPS);
    }

    @Test
    public void subSeriesOfMaxBarCountSeriesTest() {
        final BarSeries series = new BaseBarSeriesBuilder().withName("Series with maxBar count")
                .withMaxBarCount(20)
                .build();
        final int timespan = 5;

        IntStream.range(0, 100).forEach(i -> {
            series.addBar(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(i), 5d, 7d, 1d, 5d, i);
            int startIndex = Math.max(0, series.getEndIndex() - timespan + 1);
            int endIndex = i + 1;
            final BarSeries subSeries = series.getSubSeries(startIndex, endIndex);
            assertEquals(subSeries.getBarCount(), endIndex - startIndex);

            final Bar subSeriesLastBar = subSeries.getLastBar();
            final Bar seriesLastBar = series.getLastBar();
            assertEquals(subSeriesLastBar.getVolume(), seriesLastBar.getVolume());
        });
    }
}
