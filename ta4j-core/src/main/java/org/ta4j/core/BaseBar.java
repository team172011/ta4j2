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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

import static java.lang.Double.NaN;

/**
 * Base implementation of a {@link Bar}.
 */
public class BaseBar implements Bar {

    private static final long serialVersionUID = 8038383777467488147L;
    /** Time period (e.g. 1 day, 15 min, etc.) of the bar */
    private Duration timePeriod;
    /** End time of the bar */
    private ZonedDateTime endTime;
    /** Begin time of the bar */
    private ZonedDateTime beginTime;
    /** Open price of the period */
    private double openPrice = -1d;
    /** Close price of the period */
    private double closePrice;
    /** High price of the period */
    private double highPrice = Integer.MIN_VALUE;
    /** Low price of the period */
    private double lowPrice = Integer.MAX_VALUE;
    /** Traded amount during the period */
    private double amount;
    /** Volume of the period */
    private double volume;
    /** Trade count */
    private double trades = 0.0;

    /**
     * Constructor.
     * 
     * @param timePeriod  the time period
     * @param endTime     the end time of the bar period
     */
    public BaseBar(Duration timePeriod, ZonedDateTime endTime) {
        checkTimeArguments(timePeriod, endTime);
        this.timePeriod = timePeriod;
        this.endTime = endTime;
        this.beginTime = endTime.minus(timePeriod);
    }

    /**
     * Constructor.
     * 
     * @param timePeriod the time period
     * @param endTime    the end time of the bar period
     * @param openPrice  the open price of the bar period
     * @param highPrice  the highest price of the bar period
     * @param lowPrice   the lowest price of the bar period
     * @param closePrice the close price of the bar period
     * @param volume     the volume of the bar period
     */
    public BaseBar(Duration timePeriod, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice,
                   double closePrice, double volume) {
        this(timePeriod, endTime, openPrice, highPrice, lowPrice, closePrice, volume, 0.0);
    }

    /**
     * Constructor.
     * 
     * @param timePeriod the time period
     * @param endTime    the end time of the bar period
     * @param openPrice  the open price of the bar period
     * @param highPrice  the highest price of the bar period
     * @param lowPrice   the lowest price of the bar period
     * @param closePrice the close price of the bar period
     * @param volume     the volume of the bar period
     * @param amount     the amount of the bar period
     */
    public BaseBar(Duration timePeriod, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice,
                   double closePrice, double volume, long amount) {
        this(timePeriod, endTime, openPrice, highPrice, lowPrice, closePrice, volume, (double) amount, 0d);
    }


    /**
     * Constructor.
     * 
     * @param timePeriod the time period
     * @param endTime    the end time of the bar period
     * @param openPrice  the open price of the bar period
     * @param highPrice  the highest price of the bar period
     * @param lowPrice   the lowest price of the bar period
     * @param closePrice the close price of the bar period
     * @param volume     the volume of the bar period
     * @param amount     the amount of the bar period
     */
    public BaseBar(Duration timePeriod, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice,
                   double closePrice, double volume, double amount) {
        this(timePeriod, endTime, openPrice, highPrice, lowPrice, closePrice, volume, amount, 0d);
    }

    /**
     * Constructor.
     * 
     * @param timePeriod the time period
     * @param endTime    the end time of the bar period
     * @param openPrice  the open price of the bar period
     * @param highPrice  the highest price of the bar period
     * @param lowPrice   the lowest price of the bar period
     * @param closePrice the close price of the bar period
     * @param volume     the volume of the bar period
     * @param amount     the amount of the bar period
     * @param trades     the trades count of the bar period
     */
    public BaseBar(Duration timePeriod, ZonedDateTime endTime, double openPrice, double highPrice, double lowPrice,
                   double closePrice, double volume, double amount, double trades) {
        checkTimeArguments(timePeriod, endTime);
        this.timePeriod = timePeriod;
        this.endTime = endTime;
        this.beginTime = endTime.minus(timePeriod);
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.volume = volume;
        this.amount = amount;
        this.trades = trades;
    }

    /**
     * Returns BaseBarBuilder
     * 
     * @return builder of class BaseBarBuilder
     */
    public static BaseBarBuilder builder() {
        return new BaseBarBuilder();
    }

    /**
     * @return the open price of the period
     */
    public double getOpenPrice() {
        return openPrice;
    }

    /**
     * @return the low price of the period
     */
    public double getLowPrice() {
        return lowPrice;
    }

    /**
     * @return the high price of the period
     */
    public double getHighPrice() {
        return highPrice;
    }

    /**
     * @return the close price of the period
     */
    public double getClosePrice() {
        return closePrice;
    }

    /**
     * @return the whole traded volume in the period
     */
    public double getVolume() {
        return volume;
    }

    /**
     * @return the number of trades in the period
     */
    public double getTrades() {
        return trades;
    }

    /**
     * @return the whole traded amount (tradePrice x tradeVolume) of the period
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @return the time period of the bar
     */
    public Duration getTimePeriod() {
        return timePeriod;
    }

    /**
     * @return the begin timestamp of the bar period
     */
    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    /**
     * @return the end timestamp of the bar period
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Adds a trade at the end of bar period.
     * 
     * @param tradeVolume the traded volume
     * @param tradePrice  the price
     */
    @Override
    public void addTrade(long tradeVolume, double tradePrice) {
        addPrice(tradePrice);

        volume = volume + (tradeVolume);
        amount = amount + (tradeVolume * tradePrice);
        trades++;
    }

    @Override
    public void addPrice(double price) {
        if (openPrice == -1) {
            openPrice = price;
        }
        closePrice = price;
        if (highPrice < price) {
            highPrice = price;
        }
        if (lowPrice > (price)) {
            lowPrice = price;
        }
    }

    @Override
    public String toString() {
        return String.format(
                "{end time: %1s, close price: %2$f, open price: %3$f, low price: %4$f, high price: %5$f, volume: %6$f}",
                endTime.withZoneSameInstant(ZoneId.systemDefault()), closePrice, openPrice,
                lowPrice, highPrice, volume);
    }

    /**
     * @param timePeriod the time period
     * @param endTime    the end time of the bar
     * @throws IllegalArgumentException if one of the arguments is null
     */
    private static void checkTimeArguments(Duration timePeriod, ZonedDateTime endTime) {
        if (timePeriod == null) {
            throw new IllegalArgumentException("Time period cannot be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginTime, endTime, timePeriod, openPrice, highPrice, lowPrice, closePrice, volume, amount,
                trades);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BaseBar))
            return false;
        final BaseBar other = (BaseBar) obj;
        return Objects.equals(beginTime, other.beginTime) && Objects.equals(endTime, other.endTime)
                && Objects.equals(timePeriod, other.timePeriod) && Objects.equals(openPrice, other.openPrice)
                && Objects.equals(highPrice, other.highPrice) && Objects.equals(lowPrice, other.lowPrice)
                && Objects.equals(closePrice, other.closePrice) && Objects.equals(volume, other.volume)
                && Objects.equals(amount, other.amount) && trades == other.trades;
    }
}
