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
package org.ta4j.core.indicators.helpers;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;

/**
 * Simple boolean transform indicator.
 *
 * Transforms any decimal indicator to a boolean indicator by using common
 * logical operators.
 */
public class BooleanTransformIndicator extends CachedIndicator<Boolean> {

    /**
     * Select the type for transformation.
     */
    public enum BooleanTransformType {

        /**
         * Transforms the decimal indicator to a boolean indicator by
         * indicator.equals(coefficient).
         */
        equals,

        /**
         * Transforms the decimal indicator to a boolean indicator by indicator>
         * (coefficient).
         */
        isGreaterThan,

        /**
         * Transforms the decimal indicator to a boolean indicator by indicator >=
         * (coefficient).
         */
        isGreaterThanOrEqual,

        /**
         * Transforms the decimal indicator to a boolean indicator by indicator <
         * (coefficient).
         */
        isLessThan,

        /**
         * Transforms the decimal indicator to a boolean indicator by
         * indicator<=(coefficient).
         */
        isLessThanOrEqual
    }

    /**
     * Select the type for transformation.
     */
    public enum BooleanTransformSimpleType {
        /**
         * Transforms the decimal indicator to a boolean indicator by indicator.isNaN().
         */
        isNaN,

        /**
         * Transforms the decimal indicator to a boolean indicator by
         * indicator.isNegative().
         */
        isNegative,

        /**
         * Transforms the decimal indicator to a boolean indicator by
         * indicator.isNegativeOrZero().
         */
        isNegativeOrZero,

        /**
         * Transforms the decimal indicator to a boolean indicator by indicator > 0.
         */
        isPositive,

        /**
         * Transforms the decimal indicator to a boolean indicator by
         * indicator.isPositiveOrZero().
         */
        isPositiveOrZero,

        /**
         * Transforms the decimal indicator to a boolean indicator by indicator == 0.
         */
        isZero
    }

    private final Indicator<Double> indicator;
    private Double coefficient;
    private BooleanTransformType type;
    private BooleanTransformSimpleType simpleType;

    /**
     * Constructor.
     * 
     * @param indicator   the indicator
     * @param coefficient the value for transformation
     * @param type        the type of the transformation
     */
    public BooleanTransformIndicator(Indicator<Double> indicator, Double coefficient, BooleanTransformType type) {
        super(indicator);
        this.indicator = indicator;
        this.coefficient = coefficient;
        this.type = type;
    }

    /**
     * Constructor.
     * 
     * @param indicator the indicator
     * @param type      the type of the transformation
     */
    public BooleanTransformIndicator(Indicator<Double> indicator, BooleanTransformSimpleType type) {
        super(indicator);
        this.indicator = indicator;
        this.simpleType = type;
    }

    @Override
    protected Boolean calculate(int index) {

        Double val = indicator.getValue(index);

        if (type != null) {
            switch (type) {
            case equals:
                return val.equals(coefficient);
            case isGreaterThan:
                return val > (coefficient);
            case isGreaterThanOrEqual:
                return val >= (coefficient);
            case isLessThan:
                return val < (coefficient);
            case isLessThanOrEqual:
                return val <= (coefficient);
            default:
                break;
            }
        }

        else if (simpleType != null) {
            switch (simpleType) {
            case isNaN:
                return val.isNaN();
            case isNegative:
                return val < 0;
            case isNegativeOrZero:
                return val <= 0;
            case isPositive:
                return val > 0;
            case isPositiveOrZero:
                return val >= 0;
            case isZero:
                return val == 0;
            default:
                break;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        if (type != null) {
            return getClass().getSimpleName() + " Coefficient: " + coefficient + " Transform(" + type.name() + ")";
        }
        return getClass().getSimpleName() + "Transform(" + simpleType.name() + ")";
    }
}
