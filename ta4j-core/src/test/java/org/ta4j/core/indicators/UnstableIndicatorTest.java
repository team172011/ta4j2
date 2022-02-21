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

import org.junit.Before;
import org.junit.Test;
import org.ta4j.core.AbstractIndicatorTest;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.mocks.MockBarSeries;

public class UnstableIndicatorTest extends AbstractIndicatorTest {

    private int unstablePeriod;
    private UnstableIndicator unstableIndicator;

    @Before
    public void setUp() {
        unstablePeriod = 5;
        unstableIndicator = new UnstableIndicator(new ClosePriceIndicator(new MockBarSeries()),
                unstablePeriod);
    }

    @Test
    public void indicatorReturnsNanBeforeUnstablePeriod() {
        for (int i = 0; i < unstablePeriod; i++) {
            assertEquals(unstableIndicator.getValue(i), Double.NaN);
        }
    }

    @Test
    public void indicatorNotReturnsNanAfterUnstablePeriod() {
        for (int i = unstablePeriod; i < 10; i++) {
            assertNotEquals(unstableIndicator.getValue(i), Double.NaN);
        }
    }

}
