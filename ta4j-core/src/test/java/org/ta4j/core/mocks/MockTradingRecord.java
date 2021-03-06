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
package org.ta4j.core.mocks;

import org.ta4j.core.BaseTradingRecord;

import java.util.List;


public class MockTradingRecord extends BaseTradingRecord {

    private static final long serialVersionUID = 6220278197931451635L;

    /*
     * Constructor. Builds a TradingRecord from a list of states. Initial state
     * value is zero. Then at each index where the state value changes, the
     * TradingRecord operates at that index.
     *
     * @param states List<Double> of state values
     */
    public MockTradingRecord(List<Double> states) {
        super();
        Double lastState = 0d;
        for (int i = 0; i < states.size(); i++) {
            Double state = states.get(i);
            if (state != lastState) {
                this.operate(i);
            }
            lastState = state;
        }
    }
}
