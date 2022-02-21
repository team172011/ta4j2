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
package org.ta4j.core.indicators;

import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.Bar;
import org.ta4j.core.mocks.MockBar;
import org.ta4j.core.mocks.MockBarSeries;

import java.util.ArrayList;
import java.util.List;

public class ParabolicSarIndicatorTest extends AbstractIndicatorTest {

    @Test
    public void startUpAndDownTrendTest() {
        List<Bar> bars = new ArrayList<Bar>();

        bars.add(new MockBar(74.5, 75.1, 75.11, 74.06));
        bars.add(new MockBar(75.09, 75.9, 76.030000, 74.640000));
        bars.add(new MockBar(79.99, 75.24, 76.269900, 75.060000));
        bars.add(new MockBar(75.30, 75.17, 75.280000, 74.500000));
        bars.add(new MockBar(75.16, 74.6, 75.310000, 74.540000));
        bars.add(new MockBar(74.58, 74.1, 75.467000, 74.010000));
        bars.add(new MockBar(74.01, 73.740000, 74.700000, 73.546000));
        bars.add(new MockBar(73.71, 73.390000, 73.830000, 72.720000));
        bars.add(new MockBar(73.35, 73.25, 73.890000, 72.86));
        bars.add(new MockBar(73.24, 74.36, 74.410000, 73, 26));
        bars.add(new MockBar(74.36, 76.510000, 76.830000, 74.820000));
        bars.add(new MockBar(76.5, 75.590000, 76.850000, 74.540000));
        bars.add(new MockBar(75.60, 75.910000, 76.960000, 75.510000));
        bars.add(new MockBar(75.82, 74.610000, 77.070000, 74.560000));
        bars.add(new MockBar(74.75, 75.330000, 75.530000, 74.010000));
        bars.add(new MockBar(75.33, 75.010000, 75.500000, 74.510000));
        bars.add(new MockBar(75.0, 75.620000, 76.210000, 75.250000));
        bars.add(new MockBar(75.63, 76.040000, 76.460000, 75.092800));
        bars.add(new MockBar(76.0, 76.450000, 76.450000, 75.435000));
        bars.add(new MockBar(76.45, 76.260000, 76.470000, 75.840000));
        bars.add(new MockBar(76.30, 76.850000, 77.000000, 76.190000));

        ParabolicSarIndicator sar = new ParabolicSarIndicator(new MockBarSeries(bars));

        assertEquals("NaN", sar.getValue(0).toString());
        assertEquals(74.06, sar.getValue(1));
        assertEquals(74.06, sar.getValue(2)); // start with up trend
        assertEquals(74.148396, sar.getValue(3)); // switch to downtrend
        assertEquals(74.23325616000001, sar.getValue(4)); // hold trend...
        assertEquals(76.2699, sar.getValue(5));
        assertEquals(76.22470200000001, sar.getValue(6));
        assertEquals(76.11755392, sar.getValue(7));
        assertEquals(75.9137006848, sar.getValue(8));
        assertEquals(75.72207864371201, sar.getValue(9)); // switch to up trend
        assertEquals(72.72, sar.getValue(10));// hold trend
        assertEquals(72.8022, sar.getValue(11));
        assertEquals(72.964112, sar.getValue(12));
        assertEquals(73.20386528, sar.getValue(13));
        assertEquals(73.5131560576, sar.getValue(14));
        assertEquals(73.797703572992, sar.getValue(15));
        assertEquals(74.01, sar.getValue(16));
        assertEquals(74.2548, sar.getValue(17));
        assertEquals(74.480016, sar.getValue(18));
        assertEquals(74.68721472, sar.getValue(19));
        assertEquals(74.8778375424, sar.getValue(20));
    }

    @Test
    public void startWithDownAndUpTrendTest() {
        List<Bar> bars = new ArrayList<Bar>();
        bars.add(new MockBar(4261.48, 4285.08, 4485.39, 4200.74)); // The first daily candle of BTCUSDT in
                                                                                // the Binance cryptocurrency exchange.
                                                                                // 17 Aug 2017
        bars.add(new MockBar(4285.08, 4108.37, 4371.52, 3938.77)); // starting with down trend
        bars.add(new MockBar(4108.37, 4139.98, 4184.69, 3850.00)); // hold trend...
        bars.add(new MockBar(4120.98, 4086.29, 4211.08, 4032.62));
        bars.add(new MockBar(4069.13, 4016.00, 4119.62, 3911.79));
        bars.add(new MockBar(4016.00, 4040.00, 4104.82, 3400.00));
        bars.add(new MockBar(4040.00, 4114.01, 4265.80, 4013.89));
        bars.add(new MockBar(4147.00, 4316.01, 4371.68, 4085.01)); // switch to up trend
        bars.add(new MockBar(4316.01, 4280.68, 4453.91, 4247.48)); // hold trend
        bars.add(new MockBar(4280.71, 4337.44, 4367.00, 4212.41));

        ParabolicSarIndicator sar = new ParabolicSarIndicator(new MockBarSeries(bars));

        assertEquals("NaN", sar.getValue(0).toString());
        assertEquals(4485.39000000, sar.getValue(1));
        assertEquals(4485.39000000, sar.getValue(2));
        assertEquals(4459.97440000, sar.getValue(3));
        assertEquals(4435.57542400, sar.getValue(4));
        assertEquals(4412.15240704, sar.getValue(5));
        assertEquals(4351.42326262, sar.getValue(6));
        assertEquals(3400.00000000, sar.getValue(7));
        assertEquals(3419.43360000, sar.getValue(8));
        assertEquals(3460.81265600, sar.getValue(9));
    }

}