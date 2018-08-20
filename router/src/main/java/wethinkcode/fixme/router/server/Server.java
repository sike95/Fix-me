package wethinkcode.fixme.router.server;

import wethinkcode.fixme.router.routing.Router;
import wethinkcode.fixme.router.server.Validation.MessageValidationHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class Server {

    private static final String POISON_PILL = "killall";
    private static String IDNumber = "";
    private static String message;
    private static Router router;
    private static int    counter;

    public Server(){
        //Create a new router as the server is created on its port and update its Routing table
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

            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()){

                SelectionKey key = iter.next();

                if (key.isAcceptable()){
                    IDNumber = IDGenerator.getIdGenerator().generateId();
                    register(selector, channel, key);
                }

                if (key.isReadable()){
                    //TODO:: Message Validation
                   //answerWithEcho(buffer, key);
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

        //TODO :: Add connection to routing list NOte I don't know what we will use to identify a client so I am ...
        // TODO - Experimenting with the Selection key as the Identifier
        //this will be vital later when trying to send a message to the correct destination
        //{
        router.addToRoutingTable(++counter, IDNumber, key);
        //} if it doesn't work this will be the problem
    }

    private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
            throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
       // buffer.compact();
        message = new String(buffer.array()).trim();
        System.out.println(message);
        if (new String(buffer.array()).trim().equals(POISON_PILL)) {
            client.close();
            System.out.println("Not accepting client messages anymore");
        }
        buffer.clear();
    }

    private static void  messageValidation(ByteBuffer buffer, SelectionKey key)
    throws IOException{

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        message = new String(buffer.array()).trim();


    }
}
