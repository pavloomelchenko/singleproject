package com.mycompany.strategytester;

import org.junit.Test;

import java.util.Arrays;

public class MaTest {

    @Test
    public void test(){


        Candle c = new Candle(0, 14468.500000, 0, "");
        Candle c1 = new Candle(0, 14919.490234, 0, "");
        Candle c2 = new Candle(0, 13308.059570, 0, "");
        Candle c3 = new Candle(0, 13841.190430, 0, "");
        Candle c4 = new Candle(0, 14243.120117, 0, "");
        Candle c5 = new Candle(0, 13638.629883, 0, "");
        Candle c6 = new Candle(0, 13631.980469, 0, "");
        Candle c7 = new Candle(0, 11282.490234, 0, "");
        Candle c8 = new Candle(0, 11162.700195, 0, "");
        Candle c9 = new Candle(0, 11175.519531, 0, "");
        StrategyTester sut = new StrategyTester(Arrays.asList(c,c1,c2,c3,c4,c5,c6,c7,c8,c9));
        System.out.println(sut.calcMa(9, 10).value);
    }
}
