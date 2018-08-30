package wethinkcode.fixme.router.server;

<<<<<<< HEAD
||||||| merged common ancestors
import wethinkcode.fixme.router.routing.Router;
import wethinkcode.fixme.router.server.Validation.MessageValidationHandler;

=======
import lombok.Getter;
import wethinkcode.fixme.router.routing.RoutingTable;
import wethinkcode.fixme.router.server.Validation.*;

>>>>>>> b790510f93d11a3a9f8c605966aa8c3c03679335
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
import java.util.List;
import java.util.Set;

@Getter
public class Server {
    private Selector selector;
    private InetSocketAddress listenAddress;
//    private final int port;
    private ByteBuffer buffer;
    private ServerSocketChannel serverChannel;

    /**
     * Initializing the server
     *
     * @throws IOException
     */
    public Server() throws IOException {
//        this.port = port;
//        this.listenAddress = new InetSocketAddress(address, this.port);
        this.selector = Selector.open();
//        this.serverChannel = ServerSocketChannel.open();
//        this.serverChannel.configureBlocking(false);
//        this.serverChannel.socket().bind(this.listenAddress);
//        this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * Checks through the keys for when the incoming key is
     * Valid, Acceptable, Readable, Writable
     *
     * @throws IOException
     */
    public void startServer(List<RoutingTable> routingTables) throws Exception {

//        System.out.println("Server started on port >> " + this.port);

        int[] ports = {5000, 5001};

        for (int port : ports) {

            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(port));
            server.register(selector, SelectionKey.OP_ACCEPT);
            if (port == 5001){
                System.out.println("Market connected");
            }
            else if (port == 5000){
                System.out.println("Broker connected");
            }
        }

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
                    this.accept(key, routingTables);
                if (key.isReadable())
                    this.read(key, routingTables);
                //TODO: find out why when a client is forcefully closed an exception gets thrown
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

    private void accept(SelectionKey key, List<RoutingTable> routingTables) throws Exception {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("Connected to: " + remoteAddr);
        System.out.println("Listen from port : " + socket.getLocalPort());
        String clientId =  IDGenerator.getIdGenerator().generateId(socket.getLocalPort());
        routingTables.add(new RoutingTable("girhbiharbh", clientId,  key));
        this.useSocketToWrite(channel, clientId);
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

    private void read(SelectionKey key, List<RoutingTable> routingTable) throws IOException {
       //TODO: close the port correctly
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
        String msg = new String(data);
        System.out.println(validation(msg, routingTable));
        System.out.println("Got: " + msg);
        channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    private boolean validation(String msg, List<RoutingTable> routingTable){
        // Todo implement FixValidator
        MessageValidationHandler chain1 = new CheckSumValidator();
        MessageValidationHandler chain2 = new DestinationVerification(routingTable);
        MessageValidationHandler chain3 = new MessageForwarding();

        chain1.setNextHandler(chain2);
        chain2.setNextHandler(chain3);

        FixMessageValidator request = new FixMessageValidator(msg);
        return chain1.validateMessage(request);
    }

    /**
     * Writes to client
     * Sets the option on the selector to read
     *
     * @param channel
     * @param message
     * @throws Exception
     */

    private void useSocketToWrite (SocketChannel channel, String message) throws Exception {
        this.buffer = ByteBuffer.allocate(1024);
        this.buffer.put(message.getBytes());
        this.buffer.flip();
        channel.write(buffer);
        this.buffer.clear();
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    /**
     * The server gets the socket from the key
     *
     * @param key
     * @param message
     * @throws IOException
     */

    public void writeToClient(SelectionKey key, String message) throws  Exception{

        SocketChannel channel = (SocketChannel)key.channel();
        this.useSocketToWrite(channel, message);
    }
}



































