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
package org.ta4j.core.mocks;

import org.ta4j.core.BaseBar;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A mock bar with sample data.
 */
public class MockBar extends BaseBar {

    private static final long serialVersionUID = -4546486893163810212L;

    public MockBar(double closePrice) {
        this(ZonedDateTime.now(), closePrice);
    }

    public MockBar(double closePrice, int volume) {
        super(Duration.ofDays(1), ZonedDateTime.now(), 0d, 0d, 0d, closePrice, (double) volume, 0d, 0d);
    }

    public MockBar(double closePrice, Double volume) {
        super(Duration.ofDays(1), ZonedDateTime.now(), 0d, 0d, 0d, closePrice, volume, 0d, 0d);
    }

    public MockBar(ZonedDateTime endTime, double closePrice) {
        super(Duration.ofDays(1), endTime, 0d, 0d, 0d, closePrice, 0d, 0d, 0d);
    }

    public MockBar(ZonedDateTime endTime, int closePrice, Double volume) {
        super(Duration.ofDays(1), endTime, 0d, 0d, 0d, (double) closePrice, volume, 0d, 0d);
    }

    public MockBar(ZonedDateTime endTime, int closePrice, int volume) {
        super(Duration.ofDays(1), endTime, 0d, 0d, 0d, (double) closePrice, (double) volume, 0d, 0d);
    }

    public MockBar(ZonedDateTime endTime, double closePrice, Double volume) {
        super(Duration.ofDays(1), endTime, 0d, 0d, 0d, closePrice, volume, 0d, 0d);
    }

    public MockBar(double openPrice, double closePrice, double highPrice, double lowPrice) {
        super(Duration.ofDays(1), ZonedDateTime.now(), openPrice, highPrice, lowPrice, closePrice, 1d, 0d, 0d);
    }

    public MockBar(double openPrice, double closePrice, double highPrice, double lowPrice, Double volume) {
        super(Duration.ofDays(1), ZonedDateTime.now(), openPrice, highPrice, lowPrice, closePrice, volume, 0d, 0d);
    }

    public MockBar(double openPrice, double closePrice, double highPrice, double lowPrice, int volume) {
        super(Duration.ofDays(1), ZonedDateTime.now(), openPrice, highPrice, lowPrice, closePrice, (double) volume, 0d,
                0d);
    }

    public MockBar(ZonedDateTime endTime, int openPrice, int closePrice, int highPrice, int lowPrice, int amount,
            Double volume, Double trades) {
        super(Duration.ofDays(1), endTime, (double) openPrice, (double) highPrice, (double) lowPrice,
                (double) closePrice, volume, (double) amount, 0d);
    }

    public MockBar(ZonedDateTime endTime, Number openPrice, Number closePrice, Number highPrice, Number lowPrice,
            Number amount, Number volume, Number trades) {
        super(Duration.ofDays(1), endTime, openPrice.doubleValue(), highPrice.doubleValue(), lowPrice.doubleValue(),
                closePrice.doubleValue(), volume.doubleValue(), amount.doubleValue(), 0d);
    }

    public MockBar(ZonedDateTime endTime, double openPrice, double closePrice, double highPrice, double lowPrice,
            long amount, Double volume, Double trades) {
        super(Duration.ofDays(1), endTime, openPrice, highPrice, lowPrice, closePrice, volume, (double) amount, 0d);
    }

    public MockBar(ZonedDateTime endTime, double openPrice, double closePrice, double highPrice, double lowPrice,
            double amount, Double volume, Double trades) {
        super(Duration.ofDays(1), endTime, openPrice, highPrice, lowPrice, closePrice, volume, amount, 0d);
    }
}
