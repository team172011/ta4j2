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

import org.junit.Test;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Function;

import static org.ta4j.core.TestUtils.*;

public class TestUtilsTest extends AbstractIndicatorTest {

    private static final String stringDouble = "1234567890.12345";
    private static final String diffStringDouble = "1234567890.12346";
    private static final BigDecimal bigDecimalDouble = new BigDecimal(stringDouble);
    private static final BigDecimal diffBigDecimalDouble = new BigDecimal(diffStringDouble);
    private static final int aInt = 1234567890;
    private static final int diffInt = 1234567891;
    private static final Double aDouble = 1234567890.1234;
    private static final Double diffDouble = 1234567890.1235;
    private static Double numStringDouble;
    private static Double diffNumStringDouble;
    private static Double numInt;
    private static Double diffNumInt;
    private static Double numDouble;
    private static Double diffNumDouble;
    private static Indicator<Double> indicator;
    private static Indicator<Double> diffIndicator;

    public TestUtilsTest() {
        numStringDouble = bigDecimalDouble.doubleValue();
        diffNumStringDouble = diffBigDecimalDouble.doubleValue();
        numInt = (double) aInt;
        diffNumInt = (double) (diffInt);
        numDouble = (double) (aDouble);
        diffNumDouble = (double) (diffDouble);
        BarSeries series = randomSeries();
        BarSeries diffSeries = randomSeries();
        indicator = new ClosePriceIndicator(series);
        diffIndicator = new ClosePriceIndicator(diffSeries);
    }

    private BarSeries randomSeries() {
        BaseBarSeriesBuilder builder = new BaseBarSeriesBuilder();
        BarSeries series = builder.build();
        ZonedDateTime time = ZonedDateTime.of(1970, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault());
        double random;
        for (int i = 0; i < 1000; i++) {
            random = Math.random();
            time = time.plusDays(i);
            series.addBar(new BaseBar(Duration.ofDays(1), time, random, random, random, random, random, random, 0d));
        }
        return series;
    }

    @Test
    public void testIndicator() {
        assertIndicatorEquals(indicator, indicator);
        assertIndicatorNotEquals(indicator, diffIndicator);
        assertIndicatorNotEquals(diffIndicator, indicator);
        assertIndicatorEquals(diffIndicator, diffIndicator);
    }
}
