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






public class BollingerBandsUpperIndicatorTest extends AbstractIndicatorTest {

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
    public void bollingerBandsUpperUsingSMAAndStandardDeviation() {

        BollingerBandsMiddleIndicator bbmSMA = new BollingerBandsMiddleIndicator(sma);
        StandardDeviationIndicator standardDeviation = new StandardDeviationIndicator(closePrice, barCount);
        BollingerBandsUpperIndicator bbuSMA = new BollingerBandsUpperIndicator(bbmSMA, standardDeviation);

        assertEquals(2, bbuSMA.getK());

        assertEquals(1, bbuSMA.getValue(0));
        assertEquals(2.5, bbuSMA.getValue(1));
        assertEquals(3.633, bbuSMA.getValue(2));
        assertEquals(4.633, bbuSMA.getValue(3));
        assertEquals(4.2761, bbuSMA.getValue(4));
        assertEquals(4.6094, bbuSMA.getValue(5));
        assertEquals(5.633, bbuSMA.getValue(6));
        assertEquals(5.2761, bbuSMA.getValue(7));
        assertEquals(5.633, bbuSMA.getValue(8));
        assertEquals(4.2761, bbuSMA.getValue(9));

        BollingerBandsUpperIndicator bbuSMAwithK = new BollingerBandsUpperIndicator(bbmSMA, standardDeviation,
                (1.5));

        assertEquals(1.5, bbuSMAwithK.getK());

        assertEquals(1, bbuSMAwithK.getValue(0));
        assertEquals(2.25, bbuSMAwithK.getValue(1));
        assertEquals(3.2247, bbuSMAwithK.getValue(2));
        assertEquals(4.2247, bbuSMAwithK.getValue(3));
        assertEquals(4.0404, bbuSMAwithK.getValue(4));
        assertEquals(4.3737, bbuSMAwithK.getValue(5));
        assertEquals(5.2247, bbuSMAwithK.getValue(6));
        assertEquals(5.0404, bbuSMAwithK.getValue(7));
        assertEquals(5.2247, bbuSMAwithK.getValue(8));
        assertEquals(4.0404, bbuSMAwithK.getValue(9));
    }
}
