package com.mycompany.strategytester;

public class Balance {

    private double money = 20000.0;
    private double prevMoney;
    private int stocks = 0;
    private Position position = Position.SOLD;

    void buy(Candle candle, int qty) {
        double price = candle.close;
//2* means short too
        if (position == Position.SOLD) {
            prevMoney = money;
            money -= /*2 **/ price * qty;
            stocks += /*2 **/ qty;

            position = Position.BOUGHT;
            System.out.println("Bought at price " + price + " tick " + candle.tick + " " + this);

        }
    }

    void sell(Candle candle, int qty) {
        double price = candle.close;
        if (position == Position.BOUGHT) {
            money += /*2 **/ price * qty;
            stocks -= /*2 **/ qty;

            position = Position.SOLD;
            colored("Sold at price " + price + " tick " + candle.tick + " " + this);
        }
    }

    private void colored(String s) {
        String color = prevMoney > money ? ANSI_RED : ANSI_GREEN;
        System.out.println(color + s + ANSI_RESET);
    }

    @Override
    public String toString() {
        return "Balance{" +
                "money=" + money +
                ", prevMoney=" + prevMoney +
                ", stocks=" + stocks +
                ", position=" + position +
                '}';
    }

    //TECHNICAL
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
}
