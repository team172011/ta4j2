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

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Sequence of {@link Bar bars} separated by a predefined period (e.g. 15
 * minutes, 1 day, etc.)
 *
 * Notably, a {@link BarSeries bar series} can be:
 * <ul>
 * <li>the base of {@link Indicator indicator} calculations
 * <li>constrained between begin and end indexes (e.g. for some backtesting
 * cases)
 * <li>limited to a fixed Double ber of bars (e.g. for actual trading)
 * </ul>
 */
public interface BarSeries extends Serializable {

    /**
     * @return the name of the series
     */
    String getName();

    /**
     * @param i an index
     * @return the bar at the i-th position
     */
    Bar getBar(int i);

    /**
     * @return the first bar of the series
     */
    default Bar getFirstBar() {
        return getBar(getBeginIndex());
    }

    /**
     * @return the last bar of the series
     */
    default Bar getLastBar() {
        return getBar(getEndIndex());
    }

    /**
     * @return the Double ber of bars in the series
     */
    int getBarCount();

    /**
     * @return true if the series is empty, false otherwise
     */
    default boolean isEmpty() {
        return getBarCount() == 0;
    }

    /**
     * Warning: should be used carefully!
     *
     * Returns the raw bar data. It means that it returns the current List object
     * used internally to store the {@link Bar bars}. It may be: - a shortened bar
     * list if a maximum bar count has been set - an extended bar list if it is a
     * constrained bar series
     *
     * @return the raw bar data
     */
    List<Bar> getBarData();

    /**
     * @return the begin index of the series
     */
    int getBeginIndex();

    /**
     * @return the end index of the series
     */
    int getEndIndex();

    /**
     * @return the description of the series period (e.g. "from 12:00 21/01/2014 to
     *         12:15 21/01/2014")
     */
    default String getSeriesPeriodDescription() {
        StringBuilder sb = new StringBuilder();
        if (!getBarData().isEmpty()) {
            Bar firstBar = getFirstBar();
            Bar lastBar = getLastBar();
            sb.append(firstBar.getEndTime().format(DateTimeFormatter.ISO_DATE_TIME))
                    .append(" - ")
                    .append(lastBar.getEndTime().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return sb.toString();
    }

    /**
     * @return the maximum Double ber of bars
     */
    int getMaximumBarCount();

    /**
     * Sets the maximum Double ber of bars that will be retained in the series.
     *
     * If a new bar is added to the series such that the Double ber of bars will
     * exceed the maximum bar count, then the FIRST bar in the series is
     * automatically removed, ensuring that the maximum bar count is not exceeded.
     *
     * @param maximumBarCount the maximum bar count
     */
    void setMaximumBarCount(int maximumBarCount);

    /**
     * @return the Double ber of removed bars
     */
    int getRemovedBarsCount();

    /**
     * Adds a bar at the end of the series.
     *
     * Begin index set to 0 if it wasn't initialized.<br>
     * End index set to 0 if it wasn't initialized, or incremented if it matches the
     * end of the series.<br>
     * Exceeding bars are removed.
     *
     * @param bar the bar to be added
     * @apiNote use #addBar(Duration, ZonedDateTime, Double , Double , Double ,
     *          Double , Double ) to add bar data directly
     * @see BarSeries#setMaximumBarCount(int)
     */
    default void addBar(Bar bar) {
        addBar(bar, false);
    }

    /**
     * Adds a bar at the end of the series.
     *
     * Begin index set to 0 if it wasn't initialized.<br>
     * End index set to 0 if it wasn't initialized, or incremented if it matches the
     * end of the series.<br>
     * Exceeding bars are removed.
     *
     * @param bar     the bar to be added
     * @param replace true to replace the latest bar. Some exchange provide
     *                continuous new bar data in the time period. (eg. 1s in 1m
     *                Duration)<br>
     * @apiNote use #addBar(Duration, ZonedDateTime, Double , Double , Double ,
     *          Double , Double ) to add bar data directly
     * @see BarSeries#setMaximumBarCount(int)
     */
    void addBar(Bar bar, boolean replace);

    /**
     * Adds a bar at the end of the series.
     *
     * @param timePeriod the {@link Duration} of this bar
     * @param endTime    the {@link ZonedDateTime end time} of this bar
     */
    void addBar(Duration timePeriod, ZonedDateTime endTime);

    default void addBar(ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
            Double volume) {
        this.addBar(endTime, openPrice, highPrice, lowPrice, closePrice, volume, 0d);
    }

    default void addBar(ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
            int volume) {
        this.addBar(endTime, openPrice, highPrice, lowPrice, closePrice, (double) volume, 0d);
    }

    /**
     * Adds a new <code>Bar</code> to the bar series.
     *
     * @param endTime    end time of the bar
     * @param openPrice  the open price
     * @param highPrice  the high/max price
     * @param lowPrice   the low/min price
     * @param closePrice the last/close price
     * @param volume     the volume (default zero)
     * @param amount     the amount (default zero)
     */
    void addBar(ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice, Double closePrice,
            Double volume, Double amount);

    /**
     * Adds a new <code>Bar</code> to the bar series.
     *
     * @param endTime    end time of the bar
     * @param openPrice  the open price
     * @param highPrice  the high/max price
     * @param lowPrice   the low/min price
     * @param closePrice the last/close price
     * @param volume     the volume (default zero)
     */
    void addBar(Duration timePeriod, ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice,
            Double closePrice, Double volume);

    /**
     * Adds a new <code>Bar</code> to the bar series.
     *
     * @param timePeriod the time period of the bar
     * @param endTime    end time of the bar
     * @param openPrice  the open price
     * @param highPrice  the high/max price
     * @param lowPrice   the low/min price
     * @param closePrice the last/close price
     * @param volume     the volume (default zero)
     * @param amount     the amount (default zero)
     */
    void addBar(Duration timePeriod, ZonedDateTime endTime, Double openPrice, Double highPrice, Double lowPrice,
            Double closePrice, Double volume, Double amount);

    /**
     * Adds a trade at the end of bar period.
     *
     * @param tradeVolume the traded volume
     * @param tradePrice  the price
     */
    void addTrade(long tradeVolume, double tradePrice);

    /**
     * Adds a price to the last bar
     *
     * @param price the price for the bar
     */
    void addPrice(double price);

    /**
     * Returns a new {@link BarSeries} instance that is a subset of this BarSeries
     * instance. It holds a copy of all {@link Bar bars} between <tt>startIndex</tt>
     * (inclusive) and <tt>endIndex</tt> (exclusive) of this BarSeries. The indices
     * of this BarSeries and the new subset BarSeries can be different. I. e. index
     * 0 of the new BarSeries will be index <tt>startIndex</tt> of this BarSeries.
     * If <tt>startIndex</tt> < this.seriesBeginIndex the new BarSeries will start
     * with the first available Bar of this BarSeries. If <tt>endIndex</tt> >
     * this.seriesEndIndex the new BarSeries will end at the last available Bar of
     * this BarSeries
     *
     * @param startIndex the startIndex (inclusive)
     * @param endIndex   the endIndex (exclusive)
     * @return a new BarSeries with Bars from startIndex to endIndex-1
     * @throws IllegalArgumentException if endIndex <= startIndex or startIndex < 0
     */
    BarSeries getSubSeries(int startIndex, int endIndex);

}
