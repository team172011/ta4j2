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

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.helpers.*;

/**
 * The Fisher Indicator.
 *
 * @see <a href=
 *      "http://www.tradingsystemlab.com/files/The%20Fisher%20Transform.pdf">
 *      http://www.tradingsystemlab.com/files/The%20Fisher%20Transform.pdf</a>
 * @see <a href="https://www.investopedia.com/terms/f/fisher-transform.asp">
 *      https://www.investopedia.com/terms/f/fisher-transform.asp</a>
 */
public class FisherIndicator extends RecursiveCachedIndicator<Double> {

    private static final Double VALUE_MAX = 0.999;
    private static final Double VALUE_MIN = -0.999;

    private final Indicator<Double> ref;
    private final Indicator<Double> intermediateValue;
    private final Double densityFactor;
    private final Double gamma;
    private final Double delta;

    /**
     * Constructor.
     *
     * @param series the series
     */
    public FisherIndicator(BarSeries series) {
        this(new MedianPriceIndicator(series), 10);
    }

    /**
     * Constructor (with alpha 0.33, beta 0.67, gamma 0.5, delta 0.5).
     *
     * @param price    the price indicator (usually {@link MedianPriceIndicator})
     * @param barCount the time frame (usually 10)
     */
    public FisherIndicator(Indicator<Double> price, int barCount) {
        this(price, barCount, 0.33, 0.67, 0.5, 0.5, 1d, true);
    }

    /**
     * Constructor (with gamma 0.5, delta 0.5).
     *
     * @param price    the price indicator (usually {@link MedianPriceIndicator})
     * @param barCount the time frame (usually 10)
     * @param alpha    the alpha (usually 0.33 or 0.5)
     * @param beta     the beta (usually 0.67 0.5 or)
     */
    public FisherIndicator(Indicator<Double> price, int barCount, Double alpha, Double beta) {
        this(price, barCount, alpha, beta, 0.5, 0.5, 1d, true);
    }

    /**
     * Constructor.
     *
     * @param price    the price indicator (usually {@link MedianPriceIndicator})
     * @param barCount the time frame (usually 10)
     * @param alpha    the alpha (usually 0.33 or 0.5)
     * @param beta     the beta (usually 0.67 or 0.5)
     * @param gamma    the gamma (usually 0.25 or 0.5)
     * @param delta    the delta (usually 0.5)
     */
    public FisherIndicator(Indicator<Double> price, int barCount, Double alpha, Double beta, Double gamma,
            Double delta) {
        this(price, barCount, alpha, beta, gamma, delta, 1d, true);
    }

    /**
     * Constructor (with alpha 0.33, beta 0.67, gamma 0.5, delta 0.5).
     *
     * @param ref              the indicator
     * @param barCount         the time frame (usually 10)
     * @param isPriceIndicator use true, if "ref" is a price indicator
     */
    public FisherIndicator(Indicator<Double> ref, int barCount, boolean isPriceIndicator) {
        this(ref, barCount, 0.33, 0.67, 0.5, 0.5, 1d, isPriceIndicator);
    }

    /**
     * Constructor (with alpha 0.33, beta 0.67, gamma 0.5, delta 0.5).
     *
     * @param ref              the indicator
     * @param barCount         the time frame (usually 10)
     * @param densityFactor    the density factor (usually 1.0)
     * @param isPriceIndicator use true, if "ref" is a price indicator
     */
    public FisherIndicator(Indicator<Double> ref, int barCount, Double densityFactor, boolean isPriceIndicator) {
        this(ref, barCount, 0.33, 0.67, 0.5, 0.5, densityFactor, isPriceIndicator);
    }

    /**
     * Constructor
     *
     * @param ref              the indicator
     * @param barCount         the time frame (usually 10)
     * @param alphaD           the alpha (usually 0.33 or 0.5)
     * @param betaD            the beta (usually 0.67 or 0.5)
     * @param gammaD           the gamma (usually 0.25 or 0.5)
     * @param deltaD           the delta (usually 0.5)
     * @param densityFactorD   the density factor (usually 1.0)
     * @param isPriceIndicator use true, if "ref" is a price indicator
     */
    public FisherIndicator(Indicator<Double> ref, int barCount, final Double alphaD, final Double betaD,
            final Double gammaD, final Double deltaD, Double densityFactorD, boolean isPriceIndicator) {
        super(ref);
        this.ref = ref;
        this.gamma = gammaD;
        this.delta = deltaD;
        this.densityFactor = densityFactorD;

        double alpha = alphaD;
        double beta = betaD;
        final Indicator<Double> periodHigh = new HighestValueIndicator(
                isPriceIndicator ? new HighPriceIndicator(ref.getBarSeries()) : ref, barCount);
        final Indicator<Double> periodLow = new LowestValueIndicator(
                isPriceIndicator ? new LowPriceIndicator(ref.getBarSeries()) : ref, barCount);

        intermediateValue = new RecursiveCachedIndicator<Double>(ref) {

            @Override
            protected Double calculate(int index) {
                if (index <= 0) {
                    return 0d;
                }

                // Value = (alpha * 2 * ((ref - MinL) / (MaxH - MinL) - 0.5) + beta *
                // priorValue) / densityFactor
                double currentRef = FisherIndicator.this.ref.getValue(index);
                double minL = periodLow.getValue(index);
                double maxH = periodHigh.getValue(index);
                double term1 = ((currentRef - minL) / (maxH - minL)) - 0.5;
                double term2 = alpha * 2 * term1;
                double term3 = term2 + (beta * (getValue(index - 1)));
                return term3 / FisherIndicator.this.densityFactor;
            }
        };
    }

    @Override
    protected Double calculate(int index) {
        if (index <= 0) {
            return 0d;
        }

        double value = intermediateValue.getValue(index);

        if (value > (VALUE_MAX)) {
            value = VALUE_MAX;
        } else if (value < VALUE_MIN) {
            value = VALUE_MIN;
        }

        // Fisher = gamma * Log((1 + Value) / (1 - Value)) + delta * priorFisher
        double term1 = Math.log((1 + value) / (1 - value));
        double term2 = getValue(index - 1);
        return gamma * term1 + delta * term2;
    }

}
