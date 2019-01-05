package com.mycompany.strategytester;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.springframework.core.io.ClassPathResource;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Decimal;
import org.ta4j.core.TimeSeries;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class StrategyTester {

    private static final double STARTING_PRICE = 100;//for rand gen
    private static final int NUMBER_OF_TICKS = 1000;
    private static int TICK_TO_BEGIN = 0;
    private static final double VOLATILITY = 0.02;
    static final int MA1_SZ = 9;
    static final int MA2_SZ = 18;
    public List<Candle> data;
    private Balance balance = new Balance();
    private StatisticsGatherer sg = new StatisticsGatherer();

    public StrategyTester(List<Candle> candles) {
        this.data = candles != null ? candles : dataGen();
    }

    public void run() {
        data.stream().skip(MA2_SZ).forEach(candle -> {
            int tick = candle.tick;
            decide(sg.addMa1(calcMa(MA1_SZ, tick)),
                    sg.addMa2(calcMa(MA2_SZ, tick)),
                    calcMa(MA1_SZ, tick - 1),
                    calcMa(MA2_SZ, tick - 1),
                    candle);
            sg.addStoch(buildStoch(candle));
        });
    }

    private Decimal buildStoch(Candle candle) {
        TimeSeries series = new BaseTimeSeries("my_2017_series");
        ZonedDateTime endTime = ZonedDateTime.now();
        int[] i = {0};
        int timeframe = 5;
        data.subList(candle.tick - timeframe - 1, candle.tick - 1).forEach(c -> {
            series.addBar(new BaseBar(endTime.plusDays(i[0]++), c.open, c.hi, c.lo, c.close, 0));
        });

        return new StochasticOscillatorKIndicatorImpl(series, timeframe).calculate(timeframe - 1);
    }

    public static Candle proc(String s, int[] tick) {
        String[] split = s.split(",");
        //int tick = Integer.valueOf(split[0].replace("-", ""));
        //if (TICK_TO_BEGIN == 0) TICK_TO_BEGIN = tick;
        tick[0]++;
        Candle result = new Candle(Double.valueOf(split[1]), Double.valueOf(split[2]), Double.valueOf(split[3]), Double.valueOf(split[4]),
                tick[0], split[0]);

        return result;
    }

    public static void main(String[] args) throws Exception {
        File file = new ClassPathResource("btc-usd.historical-price.txt").getFile();
        int[] i = {0};
        List<Candle> candles = Arrays.stream(Files.readAllLines(file.toPath(), Charset.defaultCharset()).get(0).split("', '")).skip(1)
                .map(s -> StrategyTester.proc(s, i)).collect(toList());
        StrategyTester tester = new StrategyTester(candles);
        tester.run();
        StrategyTester.plot(tester.data.stream().map(c -> c.close).collect(Collectors.toList()),
                tester.sg);
    }

    private void decide(Ma ma1, Ma ma2, Ma prevMa1, Ma prevMa2, Candle candle) {
        Ma smallerMa = lowerMaBySize(ma1, ma2);
        Ma biggerMa = higherMaBySize(ma1, ma2);
        Ma prevSmallerMa = lowerMaBySize(prevMa1, prevMa2);
        Ma prevBiggerMa = higherMaBySize(prevMa1, prevMa2);

        if (smallerMa.above(biggerMa)) {
            if (prevBiggerMa.above(prevSmallerMa)) {
                balance.buy(candle, 1);
            }
        }

        if (smallerMa.underneath(biggerMa)) {
            if (prevBiggerMa.underneath(prevSmallerMa)) {
                balance.sell(candle, 1);
            }
        }
    }

    Ma calcMa(int maSize, int tick) {

        double value = new Mean().evaluate(ArrayUtils.toPrimitive(data.stream().map(c -> c.close).toArray(Double[]::new)),
                /*substrDate(tick, maSize + TICK_TO_BEGIN)*/tick - maSize,
                maSize);
        //System.out.println("MA calc " + maSize + " " + tick + " " + value);
        return new Ma(maSize, value);
    }

    private static Ma higherMaBySize(Ma ma1, Ma ma2) {
        throwIfEqual(ma1, ma2);
        if (ma1.size > ma2.size) return ma1;
        return ma2;
    }

    private static Ma lowerMaBySize(Ma ma1, Ma ma2) {
        throwIfEqual(ma1, ma2);
        if (ma1.size < ma2.size) return ma1;
        return ma2;
    }


    private static Candle randomCandle(int i, double old_price) {
        Random r = new Random();
        double rnd = r.nextDouble(); // generate number, 0 <= x < 1.0
        double change_percent = 2 * VOLATILITY * rnd;
        if (change_percent > VOLATILITY)
            change_percent -= (2 * VOLATILITY);
        double change_amount = old_price * change_percent;
        double new_price = old_price + change_amount;
        return new Candle(new_price, new_price, i, "");//TODO open=close, its wrong
    }


    private static List<Candle> dataGen() {
        List<Candle> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TICKS; i++) {
            Candle candle;
            if (i != 0)
                candle = StrategyTester.randomCandle(i, result.get(i - 1).close);
            else
                candle = StrategyTester.randomCandle(i, STARTING_PRICE);
            result.add(candle);
        }
        return result;
        //return IntStream.range(0, 1000).boxed().map(i -> randomCandle(i, result.get(i - 1))).collect(toList());
    }

    private static void throwIfEqual(Ma ma1, Ma ma2) {
        if (ma1.size.equals(ma2.size)) throw new RuntimeException("Currently not implemented");
    }

    private static void plot(List<Double> doubles, StatisticsGatherer statistics) {
        Plot plt = Plot.create();
        plt.plot()
                .add(doubles)
                .label("label");
        plt.plot()
                .add(statistics.ma1)
                .label("9d");
        plt.plot()
                .add(statistics.ma2)
                .label("18d");
        plt.plot()
                .add(statistics.stoch)
                .label("stoch");
        plt.plot()
                .add(Arrays.asList(100, 100.5, 100, 99.5, 100), Arrays.asList(100.5, 100, 99.5, 100, 100.5))
                .label("dot");
        //.linestyle("--");
        //plt.xscale(ScaleBuilder.Scale.log);
        //plt.yscale(ScaleBuilder.Scale.log);
        plt.xlabel("xlabel");
        plt.ylabel("ylabel");
        plt.text(0.5, 0.2, "text");
        plt.title("Title!");
        plt.legend();
        try {
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e) {
            e.printStackTrace();
        }
    }


}
