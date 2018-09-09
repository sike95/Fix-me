package wethinkcode.fixme.broker.Client;

import lombok.Getter;
import lombok.Setter;
import wethinkcode.fixme.market.Commodity;

import java.util.ArrayList;

@Getter
@Setter
public class Wallet {
    private Commodity XRP;
    private Commodity Ethereum;
    private Commodity Bitcoin;
    private ArrayList<Commodity> wallet;

    public Wallet(Commodity XRP, Commodity ethereum, Commodity bitcoin) {
        this.XRP = XRP;
        this.Ethereum = ethereum;
        this.Bitcoin = bitcoin;
        this.wallet = new ArrayList<>();
        this.wallet.add(this.XRP);
        this.wallet.add(this.Ethereum);
        this.wallet.add(this.Bitcoin);

    }
}
