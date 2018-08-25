package wethinkcode.fixme.router.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private Selector selector;
    private InetSocketAddress listenAddress;
    private final int port;
    private ByteBuffer buffer;
    private ServerSocketChannel serverChannel;

    /**
     * Initializing the server
     *
     * @param address
     * @param port
     * @throws IOException
     */
    public Server(String address, int port) throws IOException {
        this.port = port;
        this.listenAddress = new InetSocketAddress(address, this.port);
        this.selector = Selector.open();
        this.serverChannel = ServerSocketChannel.open();
        this.serverChannel.configureBlocking(false);
        this.serverChannel.socket().bind(this.listenAddress);
        this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * Checks through the keys for when the incoming key is
     * Valid, Acceptable, Readable, Writable
     *
     * @throws IOException
     */
    private void startServer() throws IOException {

        System.out.println("Server started on port >> " + this.port);
        while (true) {
            if ( this.selector.select() == 0)
                continue;
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (!key.isValid())
                    continue;
                if (key.isAcceptable())
                    this.accept(key);
                if (key.isReadable())
                    this.read(key);
                if (key.isWritable())
                    this.writeToClient(key, "BOOM. ITS ACTUALLY WORKING!");
            }
        }
    }

    /**
     * The server creates the socket between client and the server
     * Sets the socket to non-blocking
     * Sends the client a unique id
     *
     * @param key
     * @throws IOException
     */

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);
        this.writeToClient(key, IDGenerator.getIdGenerator().generateId());
    }

    /**
     * The server gets the socket from the key
     * Checks whether the is something to read, if so prints it out.
     * (here more works needs to be done to the message.--> Validate and so forth)
     * Sets the option on the selector to write
     *
     * @param key
     * @throws IOException
     */

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = -1;
        numRead = channel.read(buffer);

        if (numRead == -1) {
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println("Connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        System.out.println("Got: " + new String(data));
        channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    /**
     * The server gets the socket from the key
     * Writes to the client via the buffer
     * Sets the option on the selector to read
     *
     * @param key
     * @param message
     * @throws IOException
     */

    public void writeToClient(SelectionKey key, String message) throws  IOException{

        SocketChannel channel = (SocketChannel)key.channel();
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(message.getBytes());
        this.buffer.flip();
        channel.write(buffer);
        this.buffer.clear();
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws Exception {
        try {
            new Server("localhost", 19000).startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}










































/*public class Server {

    private static final String POISON_PILL = "killall";
    private static String IDNumber = ""; //this should not be a class attribute
    private static String message;
    private static Router router;
    private static Map<String, SocketChannel> clientIdentifier = new HashMap<String, SocketChannel>();

    public Server(){
        //TODO :Create a new router as the server is created on its port and update its Routing table
        router = new Router();
    }

    public static void main(String[] args) throws Exception {

        InetAddress ipAdress = InetAddress.getByName("localhost");
        int port = 19000;
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(ipAdress, port));
        channel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(256);
        while (true){

            if (selector.select() == 0)
                continue;

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            /**
             * I think the iterator should only be used find the appropriate key of the client currently
             * sending the message
             * (this suggestion might cause blocking im not sure)
             */
       /*     while (iter.hasNext()){

                SelectionKey key = iter.next();

                if (key.isAcceptable()){
                    IDNumber = IDGenerator.getIdGenerator().generateId();
                    register(selector, channel, key);
                }

                if (key.isReadable()){
                    //TODO:: Message Validation
                   answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }

    }

    private static void register(Selector selector, ServerSocketChannel serverSocket, SelectionKey key)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.write(ByteBuffer.wrap(IDNumber.getBytes()));
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        clientIdentifier.put(IDNumber, client);
        //TODO :: Add connection to routing list NOte I don't know what we will use to identify a client so I am ...
        // TODO - Experimenting with the Selection key as the Identifier
        //this will be vital later when trying to send a message to the correct destination
        //{
      //  router.addToRoutingTable(++counter, IDNumber, key);
        //} if it doesn't work this will be the problem
    }*/



    /*private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {

       // SocketChannel client = (SocketChannel) key.channel();
        //client.read(buffer);
       // buffer.compact();
        SocketChannel client = clientIdentifier.get("000001");
        client.write(ByteBuffer.wrap(IDNumber.getBytes()));


        //client.write(ByteBuffer.wrap(msg.getBytes()));
        //client.read(buffer);
        /*message = new String(buffer.array()).trim();
        client.write(ByteBuffer.wrap(IDNumber.getBytes()));
        System.out.println(message);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        buffer.clear();*/
  //  }

  /*  private static void  messageValidation(ByteBuffer buffer, SelectionKey key)
    throws IOException{

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        message = new String(buffer.array()).trim();

    }
}
*/