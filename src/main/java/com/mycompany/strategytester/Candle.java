package com.mycompany.strategytester;

public class Candle {
    double open;
    double hi;
    double lo;
    public double close;
    int tick;
    String dtm;

    boolean isUp() {
        throw new RuntimeException();
    }

    Candle(double open, double close, int tick, String dtm) {
        this.open = open;
        this.close = close;
        this.tick = tick;
        this.dtm = dtm;
    }

    public Candle(double open, double hi, double lo, double close, int tick, String dtm) {
        this.open = open;
        this.hi = hi;
        this.lo = lo;
        this.close = close;
        this.tick = tick;
        this.dtm = dtm;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "open=" + open +
                ", close=" + close +
                ", tick=" + tick +
                ", dtm='" + dtm + '\'' +
                '}';
    }
}
