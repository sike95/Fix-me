package wethinkcode.fixme.market;

import lombok.Getter;

@Getter
public class Commodity {
    private String name;
    private float totalAmount;
    private float price;

    public Commodity(String name, float totalAmount, float price) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.price = price;
    }
}
