package wethinkcode.fixme.router.server;

import wethinkcode.fixme.router.routing.Router;
import wethinkcode.fixme.router.server.Validation.MessageValidationHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Server {
    // allows a single thread to examine I/O events on multiple channels
    private Selector selector;
    private InetSocketAddress listenAddress;
    private final static int PORT = 19000;
    private ByteBuffer buffer;

    public Server(String address, int port) throws IOException {
        listenAddress = new InetSocketAddress(address, this.PORT);
    }


    private void startServer() throws IOException {
        //allows the server to find multiple connections that are ready any I/0
        this.selector = Selector.open();
        //the serverChannel is only responsible for accepting new connections
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //sets the serverChannel to a NIO
        serverChannel.configureBlocking(false);
        // bind server socket channel to port
        serverChannel.socket().bind(this.listenAddress);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port >> " + this.PORT);

        while (true) {
            //checks if there any available channels
            int readyCount = this.selector.select();
            if (readyCount == 0) {
                continue;
            }

            // process selected keys...
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                // Remove key from set so we don't process it twice
                iterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) { // Accept client connections
                    System.out.println("Connected man");
                    this.accept(key);
                }
                if (key.isReadable()) { // Read from client
                    System.out.println("Reading man");
                    this.read(key);
                }
                if (key.isWritable()) {
                    System.out.println("Writing man");
                    // write data to client...
                  //  writeToClient(key);
                }
            }
        }
    }

    // accept client connection
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);

        String id = IDGenerator.getIdGenerator().generateId();
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(id.getBytes());
        this.buffer.flip();
        channel.write(buffer);
        //System.out.println(messages);
        this.buffer.clear();

        /**
         * Register channel with selector for further IO (record it for read/write
         * operations, here we have used read operation)
         *
         * change this later to allow writing as well
         */
        channel.register(this.selector, SelectionKey.OP_READ);
    }

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
    }

    public void writeToClient(SelectionKey key) throws  IOException{

        SocketChannel channel = (SocketChannel)key.channel();
        String messages = "this is the real world.";
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(messages.getBytes());
        this.buffer.flip();
        channel.write(buffer);
        //System.out.println(messages);
        this.buffer.clear();
       // channel.close();
    }

    public static void main(String[] args) throws Exception {
        try {
            new Server("localhost", 9093).startServer();
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