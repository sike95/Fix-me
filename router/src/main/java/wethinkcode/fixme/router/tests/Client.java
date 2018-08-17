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

    public static void main(String[] args) {

        EchoClient.start();
    }
}

class EchoClient {
    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static EchoClient instance;
    private static BufferedReader userInputReader = null;
    private static String  response;

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
                            response = new String(buffer.array()).trim();
                            System.out.println("response=" + response);
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
//            client.read(buffer);
//            response = new String(buffer.array()).trim();
//            System.out.println("response=" + response);
//            buffer.clear();
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
