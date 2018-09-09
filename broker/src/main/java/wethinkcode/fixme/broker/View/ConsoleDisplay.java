package wethinkcode.fixme.broker.View;

public class ConsoleDisplay {
    public void startUpMessage(){

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

    public void marketContentsMessage(){
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*        Market Currently Contains the following Instruments,           */");
        System.out.println("/*                Select the one you would like to trade in.             */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                 * XRP                                                 */");
        System.out.println("/*                 * Bitcoin                                             */");
        System.out.println("/*                 * Ethereum                                            */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("Enter Input: ");

    }

    public void quantityEnquiry(){
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*              Please enter valid Quantity that you would like          */");
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


    public void buyOrSell(){
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

    public void walletView(double XRP, double Ethereum, double Bitcoin){

        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/");
        System.out.println("/*                                                                       */");
        System.out.println("/*          This is currently what you own within your wallet            */");
        System.out.println("/*                                                                       */");
        System.out.println("/*                   * XRP      -> "+ XRP +"                             ");
        System.out.println("/*                   * Ethereum -> "+ Ethereum +"                        ");
        System.out.println("/*                   * Bitcoin  -> "+ Bitcoin+"                          ");
        System.out.println("/*                                                                       */");
        System.out.println("/*                                                                       */");
        System.out.println("/*************************************************************************/");
        System.out.println("/*************************************************************************/\n");

        System.out.print("\n\n");
    }
}
