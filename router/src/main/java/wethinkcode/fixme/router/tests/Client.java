package wethinkcode.fixme.router.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    private Selector selector;
    private InetSocketAddress hostAddress;
    private SocketChannel client;
    private ByteBuffer buffer;
    private  String messages;
    private  BufferedReader bufferedReader;

    /**
     * Initializing the server
     *
     */

    public Client() {
        try {
            this.selector = Selector.open();
            this.hostAddress = new InetSocketAddress("localhost", 19000);
            this.client = SocketChannel.open(this.hostAddress);
            this.client.configureBlocking(false);
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

    /**
     * Checks through the keys for when the incoming key is
     * Valid, Acceptable, Readable, Writable
     *
     * @throws Exception
     */

    public void startClient() throws Exception {

        System.out.println("Client... started");
        while (true){
            if (this.selector.select() == 0)
                continue;
            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
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
                    this.read();
                if (key.isWritable())
                    this.writeToClient();
            }
        }
    }

    /**
     *
     * @throws Exception
     */

    private void read () throws  Exception {
        client.read(buffer);
        messages = new String(buffer.array()).trim();
        System.out.println("response=" + messages);
        buffer.clear();
        this.client.register(this.selector,  SelectionKey.OP_WRITE);
    }

    /**
     * The server gets the socket from the key
     * Writes to the client via the buffer
     * Sets the option on the selector to read
     * @throws Exception
     */

    public void writeToClient() throws Exception {
        messages = bufferedReader.readLine();
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(messages.getBytes());
        this.buffer.flip();
        client.write(this.buffer);
        System.out.println(messages);
        this.buffer.clear();
        this.client.register(this.selector, SelectionKey.OP_READ);
    }

    public void stop() throws IOException {
        this.client.close();
        this.buffer = null;
    }

    public static boolean processConnect(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        while (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        return true;
    }

    public static void main(String[] args) {

        Client client = new Client();
        try {
            client.startClient();
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //      EchoClient.start();
    }
}

/*class EchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;
    private static BufferedReader userInputReader = null;
    private static String  response;
    private String clientId;

    public static EchoClient start() {
        if (instance == null)
            instance = new EchoClient();

        return instance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 19000));
            buffer = ByteBuffer.allocate(256);
            client.configureBlocking(false);
            Selector selector = Selector.open();

            int operations = SelectionKey.OP_CONNECT | SelectionKey.OP_READ
                    | SelectionKey.OP_WRITE;
            client.register(selector, operations);
            userInputReader = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectionKeys.iterator();

                while (iter.hasNext()){

                        SelectionKey key = iter.next();
                        if (key.isConnectable()){
                            boolean connected = processConnect(key);
                            if (!connected) {
                                stop();
                            }
                        }

                    if (key.isReadable()){
                            client.read(buffer);
                            clientId = new String(buffer.array()).trim();
                            System.out.println("response=" + clientId);
                            buffer.clear();
                        }

                        if (key.isWritable()){
                            System.out.print("Please enter a message(Bye to quit):");
                            String msg = userInputReader.readLine().trim();
                            sendMessage(msg);
                        }
                        iter.remove();
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sendMessage(String msg) {
        buffer = ByteBuffer.wrap(msg.getBytes());
        String response = null;
        try {
            client.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static boolean processConnect(SelectionKey key) throws Exception{
        SocketChannel channel = (SocketChannel) key.channel();
        while (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        return true;
    }
}
*/