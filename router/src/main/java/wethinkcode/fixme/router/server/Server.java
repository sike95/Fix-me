package wethinkcode.fixme.router.server;

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
                    register(selector, channel);
                }

                if (key.isReadable()){
                    answerWithEcho(buffer, key);
                }
                iter.remove();
            }
        }

    }

    private static void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.write(ByteBuffer.wrap(IDNumber.getBytes()));
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
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

        //buffer.flip().toString();
       // client.write(buffer);
        buffer.clear();
    }
}
