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

public abstract class AbstractIndicatorTest {
    protected final static Double EPS = 0.0001;

    protected void assertEquals(int expected, Double value) {
        Assert.assertEquals(expected, value, EPS);
    }

    protected void assertEquals(int expected, Double value, Double delta) {
        Assert.assertEquals(expected, value, delta);
    }

    protected void assertEquals(Double expected, Double value) {
        Assert.assertEquals(expected, value, EPS);
    }

    protected void assertEquals(Double expected, int value) {
        Assert.assertEquals((double) expected, (double) value, EPS);
    }

    protected void assertEquals(Double expected, Double value, Double delta) {
        Assert.assertEquals(expected, value, delta);
    }

    protected void assertEquals(int expected, double value) {
        Assert.assertEquals(expected, value, EPS);
    }

    protected void assertEquals(int expected, int value) {
        Assert.assertEquals(expected, value, EPS);
    }

    protected void assertEquals(Class<?> expected, Class<?> value) {
        Assert.assertEquals(expected, value);
    }

    protected void assertEquals(Object expected, Object value) {
        Assert.assertEquals(expected, value);
    }

    protected void assertFalse(Boolean expression) {
        Assert.assertFalse(expression);
    }

    protected void assertTrue(Boolean expression) {
        Assert.assertTrue(expression);
    }

    protected void assertNotEquals(Object expected, Object value) {
        Assert.assertNotEquals(expected, value);
    }
}
