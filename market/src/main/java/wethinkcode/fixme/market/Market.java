package wethinkcode.fixme.market;

import lombok.Getter;

@Getter
public class Market {
    private String name;
    private Commodity stock1, stock2, stock3;

    public Market(String name, Commodity stock1, Commodity stock2, Commodity stock3) {
        this.name = name;
        this.stock1 = stock1;
        this.stock2 = stock2;
        this.stock3 = stock3;
    }


}
