package wethinkcode.fixme.broker;

import wethinkcode.fixme.broker.Client.BrokerClient;

import java.io.IOException;
import java.util.Scanner;

public class Broker {

    private static int market;
    private static String instrument;
    private static int price;
    private static int buyOrSell;

    public static void main(String[] args) {

        BrokerPrint.buyOrSell();
        setBuyOrSell();
        BrokerPrint.startUpMessage();
        setMarket();
        System.out.println(market);
        BrokerPrint.marketContentsMessage(market);
        setInstrument();
        System.out.println(instrument);
        BrokerPrint.priceEnquiry();
        setPrice();
        System.out.println(price);


//        BrokerClient client = new BrokerClient();
//        try {
//            client.startClient();
//        } catch (IOException e ) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void setPrice(){

        try {
            Scanner sc = new Scanner(System.in);
            price = sc.nextInt();
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid price.");
            setPrice();
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

            while (sc.hasNextLine()){
                market = sc.nextInt();

                if (market > 0 && market < 4){
                    break;
                }
                else
                    System.out.println("Error: Invalid Input. Try again.");
            }

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
