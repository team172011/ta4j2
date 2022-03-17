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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Utility class for {@code Num} tests.
 */
public class TestUtils {

    /** Offset for Double equality checking */
    public static final Double GENERAL_OFFSET = 0.0001;

    private final static Logger log = LoggerFactory.getLogger(TestUtils.class);

    /**
     * Verifies that two indicators have the same size and values to an offset
     * 
     * @param expected indicator of expected values
     * @param actual   indicator of actual values
     */
    public static void assertIndicatorEquals(Indicator<Double> expected, Indicator<Double> actual) {
        org.junit.Assert.assertEquals("Size does not match,", expected.getBarSeries().getBarCount(),
                actual.getBarSeries().getBarCount());
        for (int i = 0; i < expected.getBarSeries().getBarCount(); i++) {
            assertEquals(String.format("Failed at index %s: %s", i, actual), expected.getValue(i),
                    actual.getValue(i), GENERAL_OFFSET);
        }
    }

    /**
     * Verifies that two indicators have either different size or different values
     * to an offset
     * 
     * @param expected indicator of expected values
     * @param actual   indicator of actual values
     */
    public static void assertIndicatorNotEquals(Indicator<Double> expected, Indicator<Double> actual) {
        if (expected.getBarSeries().getBarCount() != actual.getBarSeries().getBarCount())
            return;
        for (int i = 0; i < expected.getBarSeries().getBarCount(); i++) {
            if (Math.abs(expected.getValue(i) - actual.getValue(i)) > GENERAL_OFFSET)
                return;
        }
        throw new AssertionError("Indicators match to " + GENERAL_OFFSET);
    }

    /**
     * Verifies that two indicators have the same size and values
     * 
     * @param expected indicator of expected values
     * @param actual   indicator of actual values
     */
    public static void assertIndicatorEquals(Indicator<Double> expected, Indicator<Double> actual, Double delta) {
        org.junit.Assert.assertEquals("Size does not match,", expected.getBarSeries().getBarCount(),
                actual.getBarSeries().getBarCount());
        for (int i = expected.getBarSeries().getBeginIndex(); i < expected.getBarSeries().getEndIndex(); i++) {
            // convert to DecimalDouble via String (auto-precision) avoids Cast Class
            // Exception
            double exp = expected.getValue(i);
            double act = actual.getValue(i);
            double result = exp - Math.abs(act);
            if (result > (delta)) {
                log.debug("{} expected does not match", exp);
                log.debug("{} actual", act);
                log.debug("{} offset", delta);
                String expString = String.valueOf(exp);
                String actString = String.valueOf(act);
                int minLen = Math.min(expString.length(), actString.length());
                if (expString.length() > minLen)
                    expString = expString.substring(0, minLen) + "..";
                if (actString.length() > minLen)
                    actString = actString.substring(0, minLen) + "..";
                throw new AssertionError(
                        String.format("Failed at index %s: expected %s but actual was %s", i, expString, actString));
            }
        }
    }

    /**
     * Verifies that two indicators have either different size or different values
     * to an offset
     * 
     * @param expected indicator of expected values
     * @param actual   indicator of actual values
     * @param delta    Double offset to which the indicators must be different
     */
    public static void assertIndicatorNotEquals(Indicator<Double> expected, Indicator<Double> actual, Double delta) {
        if (expected.getBarSeries().getBarCount() != actual.getBarSeries().getBarCount()) {
            return;
        }
        for (int i = 0; i < expected.getBarSeries().getBarCount(); i++) {
            double exp = expected.getValue(i);
            double act = actual.getValue(i);
            double result = exp - Math.abs(act);
            if (result > (delta)) {
                return;
            }
        }
        throw new AssertionError("Indicators match to " + delta);
    }

}
