package com.mycompany.strategytester;


import org.ta4j.core.Decimal;
import org.ta4j.core.Indicator;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.indicators.helpers.MaxPriceIndicator;
import org.ta4j.core.indicators.helpers.MinPriceIndicator;

public class StochasticOscillatorKIndicatorImpl extends CachedIndicator<Decimal> {
    private final Indicator<Decimal> indicator;
    private final int timeFrame;
    private MaxPriceIndicator maxPriceIndicator;
    private MinPriceIndicator minPriceIndicator;

    public StochasticOscillatorKIndicatorImpl(TimeSeries timeSeries, int timeFrame) {
        this(new ClosePriceIndicator(timeSeries), timeFrame, new MaxPriceIndicator(timeSeries), new MinPriceIndicator(timeSeries));
    }

    public StochasticOscillatorKIndicatorImpl(Indicator<Decimal> indicator, int timeFrame, MaxPriceIndicator maxPriceIndicator, MinPriceIndicator minPriceIndicator) {
        super(indicator);
        this.indicator = indicator;
        this.timeFrame = timeFrame;
        this.maxPriceIndicator = maxPriceIndicator;
        this.minPriceIndicator = minPriceIndicator;
    }

    protected Decimal calculate(int index) {
        HighestValueIndicator highestHigh = new HighestValueIndicator(this.maxPriceIndicator, this.timeFrame);
        LowestValueIndicator lowestMin = new LowestValueIndicator(this.minPriceIndicator, this.timeFrame);
        Decimal highestHighPrice = (Decimal)highestHigh.getValue(index);
        Decimal lowestLowPrice = (Decimal)lowestMin.getValue(index);
        return ((Decimal)this.indicator.getValue(index)).minus(lowestLowPrice).dividedBy(highestHighPrice.minus(lowestLowPrice)).multipliedBy(Decimal.valueOf(3000));
    }

    public String toString() {
        return this.getClass().getSimpleName() + " timeFrame: " + this.timeFrame;
    }
}