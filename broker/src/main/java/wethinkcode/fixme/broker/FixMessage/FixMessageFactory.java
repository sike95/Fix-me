package wethinkcode.fixme.broker.FixMessage;

public class FixMessageFactory {

    private String brokerID;
    private String market;
    private String instrument;
    private String quantity;
    private String buyOrSell;
    private String price = "100"; //todo this needs to be calculated

    public FixMessageFactory(String brokerID, int market, String instrument, int price, int buyOrSell){
        this.brokerID = brokerID.trim();
        this.market = String.valueOf(market);
        this.instrument = instrument.trim();
        this.quantity = String.valueOf(price);
        this.buyOrSell = String.valueOf(buyOrSell);
    }

    public String messageCreation(){

        String fixMessage = brokerID + "|" + "8=fix.4.4|9=len|";

        fixMessage = fixMessage.concat("35=" + buyOrSell + "|");
        fixMessage = fixMessage.concat("49=" + brokerID + "|");
        fixMessage = fixMessage.concat("56=" + market + "|");
        fixMessage = fixMessage.concat("55=" + instrument + "|");
        fixMessage = fixMessage.concat("44=" + price + "|");
        fixMessage = fixMessage.concat("38=" + quantity + "|");

        return fixMessage;
    }
}
