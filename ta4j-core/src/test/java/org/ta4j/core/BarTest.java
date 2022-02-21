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

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;


public class BarTest extends AbstractIndicatorTest {

    private Bar bar;

    private ZonedDateTime beginTime;

    private ZonedDateTime endTime;

    @Before
    public void setUp() {
        beginTime = ZonedDateTime.of(2014, 6, 25, 0, 0, 0, 0, ZoneId.systemDefault());
        endTime = ZonedDateTime.of(2014, 6, 25, 1, 0, 0, 0, ZoneId.systemDefault());
        bar = new BaseBar(Duration.ofHours(1), endTime);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void addTrades() {

        bar.addTrade(3, 200.0);
        bar.addTrade(4, 201.0);
        bar.addTrade(2, 198.0);

        assertEquals(3, bar.getTrades());
        assertEquals(3 * 200 + 4 * 201 + 2 * 198, bar.getAmount());
        assertEquals(200, bar.getOpenPrice());
        assertEquals(198, bar.getClosePrice());
        assertEquals(198, bar.getLowPrice());
        assertEquals(201, bar.getHighPrice());
        assertEquals(9, bar.getVolume());
    }

    @Test
    public void getTimePeriod() {
        assertEquals(beginTime, bar.getEndTime().minus(bar.getTimePeriod()));
    }

    @Test
    public void getBeginTime() {
        assertEquals(beginTime, bar.getBeginTime());
    }

    @Test
    public void inPeriod() {
        assertFalse(bar.inPeriod(null));

        assertFalse(bar.inPeriod(beginTime.withDayOfMonth(24)));
        assertFalse(bar.inPeriod(beginTime.withDayOfMonth(26)));
        assertTrue(bar.inPeriod(beginTime.withMinute(30)));

        assertTrue(bar.inPeriod(beginTime));
        assertFalse(bar.inPeriod(endTime));
    }

    @Test
    public void equals() {
        Bar bar1 = new BaseBar(Duration.ofHours(1), endTime);
        Bar bar2 = new BaseBar(Duration.ofHours(1), endTime);

        assertEquals(bar1, bar2);
    }

    @Test
    public void hashCode2() {
        Bar bar1 = new BaseBar(Duration.ofHours(1), endTime);
        Bar bar2 = new BaseBar(Duration.ofHours(1), endTime);

        assertEquals(bar1.hashCode(), bar2.hashCode());
    }
}
