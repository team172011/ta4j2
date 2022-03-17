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

import java.time.Duration;
import java.time.ZonedDateTime;

public class BaseBarBuilder {

    private Duration timePeriod;
    private ZonedDateTime endTime;
    private double openPrice;
    private double closePrice;
    private double highPrice;
    private double lowPrice;
    private double amount;
    private double volume;
    private double trades;

    BaseBarBuilder() {
    }

    public BaseBarBuilder timePeriod(Duration timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    public BaseBarBuilder endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public BaseBarBuilder openPrice(Double openPrice) {
        this.openPrice = openPrice;
        return this;
    }

    public BaseBarBuilder closePrice(Double closePrice) {
        this.closePrice = closePrice;
        return this;
    }

    public BaseBarBuilder highPrice(Double highPrice) {
        this.highPrice = highPrice;
        return this;
    }

    public BaseBarBuilder lowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
        return this;
    }

    public BaseBarBuilder amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public BaseBarBuilder volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public BaseBarBuilder trades(double trades) {
        this.trades = trades;
        return this;
    }

    public BaseBar build() {

        return new BaseBar(timePeriod, endTime, openPrice, highPrice, lowPrice, closePrice, volume, amount, trades);
    }
}
