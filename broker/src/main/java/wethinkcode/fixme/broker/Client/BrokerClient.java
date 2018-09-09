package wethinkcode.fixme.broker.Client;

import wethinkcode.fixme.broker.FixMessage.FixMessageFactory;
import wethinkcode.fixme.broker.View.ConsoleDisplay;
import wethinkcode.fixme.market.Commodity;

import javax.xml.namespace.QName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class BrokerClient {

    private Selector selector;
    private InetSocketAddress hostAddress;
    private SocketChannel client;
    private ByteBuffer buffer;
    private  String messages;
    private BufferedReader bufferedReader;
    private String clientID;
    private boolean idFlag;
    private static String market;
    private static String instrument;
    private static int quantity;
    private static int buyOrSell;
    protected static String brokerID;
     static String fixMessage;
    private static ConsoleDisplay view;
    private Wallet wallet;
    private boolean brokerFlag;

    public BrokerClient() {

        try {
            this.selector = Selector.open();
            this.hostAddress = new InetSocketAddress("localhost", 5000);
            this.client = SocketChannel.open(this.hostAddress);
            this.client.configureBlocking(false);
            System.out.println(this.client.getLocalAddress().toString());
            int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ;
            this.client.register(this.selector, operations);
            this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            this.buffer = ByteBuffer.allocate(1024);
            this.wallet = new Wallet(new Commodity("XRP", 0,0),
                            new Commodity("Ethereum", 0,0),
                            new Commodity("Bitcoin", 0,0));
        }
        catch (IOException e) {
            System.out.println("!------->A problem occurred whilst initializing the client<-------!");
            try {
                this.stop();
            }
            catch (IOException i) {
                System.out.println("!------->A problem occurred whilst closing the client<-------!");
            }
        }
    }

    public void startClient() throws Exception {

        System.out.println("Broker has started.");
        while (true){
            if (this.selector.select() == 0)
                continue;
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if(brokerFlag)
            Broker();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (!key.isValid())
                    continue;
                if (key.isConnectable()) {
                    boolean connected = processConnect(key);
                    if (!connected)
                        stop();
                }
                if (key.isReadable())
                {
                    this.read();
                }

                if (key.isWritable())
                    this.writeToClient();
            }
        }
    }


    private void read () throws  Exception {
        client.read(buffer);
        messages = new String(buffer.array()).trim();

        if (!this.idFlag) {
            this.clientID = messages;
            this.client.register(this.selector, SelectionKey.OP_READ);
            this.idFlag = true;
            this.brokerFlag = true;
        }
        else {

            processMessage();
        }
        System.out.println("\nRead message -> " + messages);
        buffer.clear();
        this.client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE );
    }


    public void writeToClient() throws Exception {
        //messages = bufferedReader.readLine();
        messages = fixMessage;
        messages = messages + "10=" + checkSumCalculator(messages);
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(messages.getBytes());
        this.buffer.flip();
        client.write(this.buffer);
        System.out.println(" " + messages);
        this.buffer.clear();
        this.client.register(this.selector, SelectionKey.OP_READ);
        this.brokerFlag = false;
    }

    public void processMessage () throws Exception{
        String splitMessage[] = messages.split("\\|");
        String item = "";
        int quantity = 0;

        for (String segment: splitMessage){
            if (segment.contains("55")){
                item = segment.split("=")[1];
            }
            if (segment.contains("38")){
                quantity = Integer.parseInt(segment.split("=")[1]);
            }
        }

        for (Commodity commodity: wallet.getWallet()) {
            if (commodity.getName().equals(item)){

                if (buyOrSell == 1)
                commodity.buy(quantity);
               else if (buyOrSell == 2)
               {

                  commodity.sell(quantity);
               }
                if (commodity.getName().equals("XRP")) {
                    wallet.setXRP(commodity);
                                }
                if (commodity.getName().equals("Ethereum")) {
                    wallet.setEthereum(commodity);
                }
                if (commodity.getName().equals("Bitcoin")) {
                    wallet.setBitcoin(commodity);
                }
            }
        }
       Broker();
    }

    private String checkSumCalculator(String message){
        String checkSum;
        int total = 0;
        String checkSumMessage = message.replace('|', '\u0001');
        byte[] messageBytes = checkSumMessage.getBytes(StandardCharsets.US_ASCII);

        for (int i = 0; i < message.length(); i++)
            total += messageBytes[i];

        int CalculatedChecksum = total % 256;
        checkSum = Integer.toString(CalculatedChecksum );

        return checkSum;
    }

    public void stop() throws IOException {
        this.client.close();
        this.buffer = null;
    }

    public boolean processConnect(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        while (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        return true;
    }

    public String getClientID() {
        return clientID;
    }

    private void setQuantity(){

        try {
            Scanner sc = new Scanner(System.in);
            quantity = sc.nextInt();

            if (buyOrSell == 2) {
                for (Commodity commodity : this.wallet.getWallet()) {
                    if (commodity.getName().equals(instrument) && commodity.getTotalAmount() == 0) {
                        System.out.println("The value in your current " + instrument + " wallet is 0.\n");
                        this.Broker();
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid price.");
            setQuantity();
        }
    }

    private void setInstrument(){

        try {
            Scanner scanner = new Scanner(System.in);
            instrument = scanner.nextLine();
            if (!instrument.equals("XRP") && !instrument.equals("Bitcoin") && !instrument.equals("Ethereum")){
                System.out.println("Error: Invalid Input.\nPlease enter valid Instrument Code.\n Enter Input: ");
                setInstrument();
            }
        }catch (Exception e){
            System.out.println("Error: Invalid Input.\n Please enter valid Instrument Code.");
            setInstrument();
        }
    }

    private void setMarket(){
        try {
            Scanner sc = new Scanner(System.in);
            market = sc.nextLine();
            if (market.length() != 6 && market.charAt(0) != 'M') {
                System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
                setMarket();
            }

        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
            setMarket();
        }
    }

    private void setBuyOrSell(){
        try {
            Scanner sc = new Scanner(System.in);
            buyOrSell = sc.nextInt();

            if (buyOrSell > 2 || buyOrSell < 1)
                throw new Exception("INVALID");
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
            setBuyOrSell();
        }
    }

    private void Broker() throws Exception{

        view.walletView(wallet.getXRP().getTotalAmount(),wallet.getEthereum().getTotalAmount(), wallet.getBitcoin().getTotalAmount());
        view.buyOrSell();
        setBuyOrSell();
        view.startUpMessage();
        setMarket();
        view.marketContentsMessage();
        setInstrument();
        view.quantityEnquiry();
        setQuantity();

        FixMessageFactory factory = new FixMessageFactory(clientID, market, instrument, quantity, buyOrSell);
        System.out.println("Generating FIX message. Press enter.");
        fixMessage = factory.messageCreation();
       // System.out.println("\n");

        client.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {


        view = new ConsoleDisplay();
        BrokerClient client = new BrokerClient();


        try {
            client.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
