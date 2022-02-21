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
package org.ta4j.core.indicators.volume;

import org.ta4j.core.BarSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;


/**
 * Intraday Intensity Index
 *
 * @see <a href=
 *      "https://www.investopedia.com/terms/i/intradayintensityindex.asp">https://www.investopedia.com/terms/i/intradayintensityindex.asp</a>
 */
public class IIIIndicator extends CachedIndicator<Double> {

    private final ClosePriceIndicator closePriceIndicator;
    private final HighPriceIndicator highPriceIndicator;
    private final LowPriceIndicator lowPriceIndicator;
    private final VolumeIndicator volumeIndicator;

    public IIIIndicator(BarSeries series) {
        super(series);
        this.closePriceIndicator = new ClosePriceIndicator(series);
        this.highPriceIndicator = new HighPriceIndicator(series);
        this.lowPriceIndicator = new LowPriceIndicator(series);
        this.volumeIndicator = new VolumeIndicator(series);
    }

    @Override
    protected Double calculate(int index) {
        if (index == getBarSeries().getBeginIndex()) {
            return 0d;
        }
        final Double doubledClosePrice = 2 * closePriceIndicator.getValue(index);
        final Double high = highPriceIndicator.getValue(index);
        final Double low = lowPriceIndicator.getValue(index);
        final Double highMinusLow = high - low;
        final Double highPlusLow = high + low;

        return (doubledClosePrice - highPlusLow) / (highMinusLow * volumeIndicator.getValue(index));
    }
}
