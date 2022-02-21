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
package org.ta4j.core.indicators.bollinger;

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.mocks.MockBarSeries;

import static java.lang.Double.NaN;


public class BollingerBandWidthIndicatorTest extends AbstractIndicatorTest {

    private ClosePriceIndicator closePrice;

    @Before
    public void setUp() {
        BarSeries data = new MockBarSeries(10, 12, 15, 14, 17, 20, 21, 20, 20, 19, 20, 17, 12, 12, 9, 8, 9,
                10, 9, 10);
        closePrice = new ClosePriceIndicator(data);
    }

    @Test
    public void bollingerBandWidthUsingSMAAndStandardDeviation() {

        SMAIndicator sma = new SMAIndicator(closePrice, 5);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, 5);

        BollingerBandsMiddleIndicator bbmSMA = new BollingerBandsMiddleIndicator(sma);
        BollingerBandsUpperIndicator bbuSMA = new BollingerBandsUpperIndicator(bbmSMA, standardDeviation);
        BollingerBandsLowerIndicator bblSMA = new BollingerBandsLowerIndicator(bbmSMA, standardDeviation);

        BollingerBandWidthIndicator bandwidth = new BollingerBandWidthIndicator(bbuSMA, bbmSMA, bblSMA);

        assertEquals(0.0, bandwidth.getValue(0));
        assertEquals(36.3636, bandwidth.getValue(1));
        assertEquals(66.6423, bandwidth.getValue(2));
        assertEquals(60.2443, bandwidth.getValue(3));
        assertEquals(71.0767, bandwidth.getValue(4));
        assertEquals(69.9394, bandwidth.getValue(5));
        assertEquals(62.7043, bandwidth.getValue(6));
        assertEquals(56.0178, bandwidth.getValue(7));
        assertEquals(27.683, bandwidth.getValue(8));
        assertEquals(12.6491, bandwidth.getValue(9));
        assertEquals(12.6491, bandwidth.getValue(10));
        assertEquals(24.2956, bandwidth.getValue(11));
        assertEquals(68.3332, bandwidth.getValue(12));
        assertEquals(85.1469, bandwidth.getValue(13));
        assertEquals(112.8481, bandwidth.getValue(14));
        assertEquals(108.1682, bandwidth.getValue(15));
        assertEquals(66.9328, bandwidth.getValue(16));
        assertEquals(56.5194, bandwidth.getValue(17));
        assertEquals(28.1091, bandwidth.getValue(18));
        assertEquals(32.5362, bandwidth.getValue(19));
    }
}
