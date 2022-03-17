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
package org.ta4j.core.indicators.numeric;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import java.util.function.UnaryOperator;

/**
 * Objects of this class defer the evaluation of a unary operator, like sqrt().
 * 
 * There may be other unary operations on Double that could be added here.
 */
class UnaryOperation implements Indicator<Double> {

    public static UnaryOperation sqrt(Indicator<Double> operand) {
        return new UnaryOperation(Math::sqrt, operand);
    }

    public static UnaryOperation abs(Indicator<Double> operand) {
        return new UnaryOperation(Math::abs, operand);
    }

    private final UnaryOperator<Double> operator;
    private final Indicator<Double> operand;

    private UnaryOperation(UnaryOperator<Double> operator, Indicator<Double> operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public Double getValue(int index) {
        Double n = operand.getValue(index);
        return operator.apply(n);
    }

    @Override
    public BarSeries getBarSeries() {
        return operand.getBarSeries();
    }

}
