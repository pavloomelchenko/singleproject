package com.mycompany.strategytester;

import com.google.common.primitives.Ints;
import org.ta4j.core.Decimal;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static com.mycompany.strategytester.StrategyTester.MA1_SZ;
import static com.mycompany.strategytester.StrategyTester.MA2_SZ;

public class StatisticsGatherer {
    List<Double> ma1 = new LinkedList<>();
    List<Double> ma2 = new LinkedList<>();
    List<Decimal> stoch = new LinkedList<>();

    public StatisticsGatherer() {
        IntStream.range(0, MA2_SZ).forEach(i->ma1.add((double) 0));
        IntStream.range(0, MA2_SZ).forEach(i->ma2.add((double) 0));
        IntStream.range(0, MA2_SZ).forEach(i->stoch.add(Decimal.valueOf(0)));
    }


    public Ma addMa1(Ma ma) {
        ma1.add(ma.value);
        return ma;

    }

    public Ma addMa2(Ma ma) {
        ma2.add(ma.value);
        return ma;

    }

    public Decimal addStoch(Decimal d) {
        stoch.add(d);
        return d;

    }

    public List<Double> getMa1() {
        return ma1;
    }


    public List<Double> getMa2() {
        return ma2;
    }

}
