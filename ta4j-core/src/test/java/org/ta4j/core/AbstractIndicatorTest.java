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
