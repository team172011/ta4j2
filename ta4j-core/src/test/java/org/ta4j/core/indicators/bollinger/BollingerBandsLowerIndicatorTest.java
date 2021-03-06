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






public class BollingerBandsLowerIndicatorTest extends AbstractIndicatorTest {

    private int barCount;

    private ClosePriceIndicator closePrice;

    private SMAIndicator sma;

    @Before
    public void setUp() {
        BarSeries data = new MockBarSeries(1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2);
        barCount = 3;
        closePrice = new ClosePriceIndicator(data);
        sma = new SMAIndicator(closePrice, barCount);
    }

    @Test
    public void bollingerBandsLowerUsingSMAAndStandardDeviation() {

        BollingerBandsMiddleIndicator bbmSMA = new BollingerBandsMiddleIndicator(sma);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, barCount);
        BollingerBandsLowerIndicator bblSMA = new BollingerBandsLowerIndicator(bbmSMA, standardDeviation);

        assertEquals(2, bblSMA.getK());

        assertEquals(1, bblSMA.getValue(0));
        assertEquals(0.5, bblSMA.getValue(1));
        assertEquals(0.367, bblSMA.getValue(2));
        assertEquals(1.367, bblSMA.getValue(3));
        assertEquals(2.3905, bblSMA.getValue(4));
        assertEquals(2.7239, bblSMA.getValue(5));
        assertEquals(2.367, bblSMA.getValue(6));

        BollingerBandsLowerIndicator bblSMAwithK = new BollingerBandsLowerIndicator(bbmSMA, standardDeviation,
                1.5);

        assertEquals(1.5, bblSMAwithK.getK());

        assertEquals(1, bblSMAwithK.getValue(0));
        assertEquals(0.75, bblSMAwithK.getValue(1));
        assertEquals(0.7752, bblSMAwithK.getValue(2));
        assertEquals(1.7752, bblSMAwithK.getValue(3));
        assertEquals(2.6262, bblSMAwithK.getValue(4));
        assertEquals(2.9595, bblSMAwithK.getValue(5));
        assertEquals(2.7752, bblSMAwithK.getValue(6));
    }
}
