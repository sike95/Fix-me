package wethinkcode.fixme.broker;

public class BrokerPrint {

    public static void startUpMessage(){

        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*        Welcome to your Broker, Please select one of the available     */");
        System.out.println("/*                   Markets you would like to trade in.                 */");
        System.out.println("/*                                                                       */");
        System.out.println("/*        1. CoinMarketCap                                               */");
        System.out.println("/*        2. JSE                                                         */");
        System.out.println("/*        3. YayYay                                                      */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");
    }

    public static void marketContentsMessage(int select){

        if (select == 1){
            System.out.println("/*************************************************************************/");
            System.out.println("/*************************************************************************/");
            System.out.println("/*                                                                       */");
            System.out.println("/*        CoinMarketCap Currently Contains the following Instruments,    */");
            System.out.println("/*                Select the one you would like to trade in.             */");
            System.out.println("/*        1. Bitcoin                                                     */");
            System.out.println("/*        2. Ethereum                                                    */");
            System.out.println("/*        3. XRP                                                         */");
            System.out.println("/*                                                                       */");
            System.out.println("/*                                                                       */");
            System.out.println("/*************************************************************************/");
            System.out.println("/*************************************************************************/\n");

            System.out.print("Enter Input: ");
        }
        else if (select == 2){
            System.out.println("/*************************************************************************/");
            System.out.println("/*************************************************************************/");
            System.out.println("/*                                                                       */");
            System.out.println("/*        JSE Currently Contains the following Instruments,              */");
            System.out.println("/*                Select the one you would like to trade in.             */");
            System.out.println("/*                                                                       */");
            System.out.println("/*       1. Google                                                       */");
            System.out.println("/*       2. Facebook                                                     */");
            System.out.println("/*       3. IBM                                                          */");
            System.out.println("/*                                                                       */");
            System.out.println("/*************************************************************************/");
            System.out.println("/*************************************************************************/\n");

            System.out.print("Enter Input: ");
        }
        else if (select == 3){
            System.out.println("/*************************************************************************/");
            System.out.println("/*************************************************************************/");
            System.out.println("/*                                                                       */");
            System.out.println("/*        YayYay Currently Contains the following Instruments,           */");
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
        else{
            System.out.println("Error: Invalid input. Please try again.");
        }
    }

    public static void priceEnquiry(){
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


    public static void buyOrSell(){
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
