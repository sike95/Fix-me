package wethinkcode.fixme.broker;

import wethinkcode.fixme.broker.Client.BrokerClient;
import wethinkcode.fixme.broker.FixMessage.FixMessage;
import wethinkcode.fixme.broker.FixMessage.FixMessageFactory;
import wethinkcode.fixme.broker.View.ConsoleDisplay;

import java.io.IOException;
import java.util.Scanner;

public class Broker {

    private static int market;
    private static String instrument;
    private static int quantity;
    private static int buyOrSell;
    private static String brokerID;
    private static String fixMessage;

    public static void main(String[] args) {

        ConsoleDisplay view = new ConsoleDisplay();
        BrokerClient client = new BrokerClient();


        try {
            client.startClient();
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.buyOrSell();
        setBuyOrSell();
        view.startUpMessage();
        setMarket();
        System.out.println(market);
        view.marketContentsMessage();
        setInstrument();
        System.out.println(instrument);
        view.quantityEnquiry();
        setQuantity();
        System.out.println(quantity);
        brokerID = client.getClientID();

        FixMessageFactory factory = new FixMessageFactory(brokerID, market, instrument, quantity, buyOrSell);
        fixMessage = factory.messageCreation();

    }

    private static void setQuantity(){

        try {
            Scanner sc = new Scanner(System.in);
            quantity = sc.nextInt();
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid price.");
            setQuantity();
        }
    }

    private static void setInstrument(){

        try {
            Scanner scanner = new Scanner(System.in);
            instrument = scanner.nextLine();
        }catch (Exception ex){
            System.out.println("Error: Invalid Input, Please enter valid Instrument Code.");
            setInstrument();
        }
    }

    private static void setMarket(){
        try {
            Scanner sc = new Scanner(System.in);
            market = sc.nextInt();
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
            setMarket();
        }
    }

    private static void setBuyOrSell(){
        try {
            Scanner sc = new Scanner(System.in);
            buyOrSell = sc.nextInt();

            if (buyOrSell > 2 || buyOrSell < 1)
                throw new Exception("TOO HIGH");
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
            setBuyOrSell();
        }

        if (buyOrSell == 2){
            //TODO: CREATE A WALLET AND SEND THROUGH DETAILS SO WE CAN CONSTRUCT A SELL FIX MESSAGE.
        }
    }
}
