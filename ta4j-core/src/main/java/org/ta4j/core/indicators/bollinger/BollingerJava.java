package org.ta4j.core.indicators.bollinger;

import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;


public class BollingerJava {

    private Indicator upper;
    private Indicator lower;

    public BollingerJava() {
        upper = new SMAIndicator(null, 2);
    }
}
