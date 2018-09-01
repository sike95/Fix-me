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

        buyOrSell();
        setBuyOrSell();
        startUpMessage();
        setMarket();
        System.out.println(market);
        marketContentsMessage();
        setInstrument();
        System.out.println(instrument);
        priceEnquiry();
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

    private static void startUpMessage(){

        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*        Welcome to your Broker, Please select one of the available     */");
        System.out.println("/*                   Markets you would like to trade in.                 */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");
    }

    private static void marketContentsMessage(){
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*        Market Currently Contains the following Instruments,           */");
        System.out.println("/*                Select the one you would like to trade in.             */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");

    }

    private static void priceEnquiry(){
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*              Please enter valid price that you would like             */");
        System.out.println("/*                     To purchase the Instrument for.                   */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");
    }


    private static void buyOrSell(){
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*              Would you Like to purchase an instrument or              */");
        System.out.println("/*                   Sell an existing instrument that's.                 */");
        System.out.println("/*                         Currently in your wallet?                     */");
        System.out.println("/*                                                                       */");
        System.out.println("/*           1. BUY                                                      */");
        System.out.println("/*           2. SELL                                                     */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");
    }

}
