package wethinkcode.fixme.market;

import lombok.Getter;

@Getter
public class Commodity {
    private String name;
    private double totalAmount;
    private double price;

    public Commodity(String name, double totalAmount, double price) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.price =  price;
    }

    public boolean buyCommodity (double quantity){
        this.totalAmount -= quantity;
        if (this.totalAmount <= 0){
            this.totalAmount += quantity;
            return false;
        }
            return true;
    }
}
