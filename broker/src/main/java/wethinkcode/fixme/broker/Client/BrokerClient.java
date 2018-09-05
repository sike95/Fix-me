package wethinkcode.fixme.broker.Client;

import wethinkcode.fixme.broker.Broker;
import wethinkcode.fixme.broker.FixMessage.FixMessageFactory;
import wethinkcode.fixme.broker.View.ConsoleDisplay;

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

public class BrokerClient extends Broker {

    private Selector selector;
    private InetSocketAddress hostAddress;
    private SocketChannel client;
    private ByteBuffer buffer;
    private  String messages;
    private BufferedReader bufferedReader;
    private String clientID;
    private boolean idFlag;
    private static int market;
    private static String instrument;
    private static int quantity;
    private static int buyOrSell;
    protected static String brokerID;
    private static String fixMessage;
    private static ConsoleDisplay view;

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

        System.out.println("Broker ... started");
        while (true){
            if (this.selector.select() == 0)
                continue;
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if(idFlag)
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

        if (!this.idFlag){
            this.clientID = messages;
            this.client.register(this.selector, SelectionKey.OP_READ);
            this.idFlag = true;
        }
        System.out.println("responses=" + messages);
        buffer.clear();
        this.client.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE );
    }


    public void writeToClient() throws Exception {
        messages = bufferedReader.readLine();
        messages = fixMessage;
        messages = messages + "|10=" + checkSumCalculator(messages);
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(messages.getBytes());
        this.buffer.flip();
        client.write(this.buffer);
        System.out.println(" " + messages);
        this.buffer.clear();
        this.client.register(this.selector, SelectionKey.OP_READ);
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
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid price.");
            setQuantity();
        }
    }

    private void setInstrument(){

        try {
            Scanner scanner = new Scanner(System.in);
            instrument = scanner.nextLine();
        }catch (Exception ex){
            System.out.println("Error: Invalid Input, Please enter valid Instrument Code.");
            setInstrument();
        }
    }

    private void setMarket(){
        try {
            Scanner sc = new Scanner(System.in);
            market = sc.nextInt();
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
                throw new Exception("TOO HIGH");
        }catch (Exception e){
            System.out.println("Error: Invalid Input, Please enter valid corresponding market Index.");
            setBuyOrSell();
        }

        if (buyOrSell == 2){
            //TODO: CREATE A WALLET AND SEND THROUGH DETAILS SO WE CAN CONSTRUCT A SELL FIX MESSAGE.
        }
    }

    private void Broker(){
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


        FixMessageFactory factory = new FixMessageFactory(clientID, market, instrument, quantity, buyOrSell);
        fixMessage = factory.messageCreation();
        System.out.println(fixMessage);
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
