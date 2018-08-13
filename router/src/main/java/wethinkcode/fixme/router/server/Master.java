package wethinkcode.fixme.router.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//todo :: implememnt a non-blocking socket.

public class Master {
    private ServerSocket serverListener;
    private int portNumber;

    public Master (int portNumber) {
        this.portNumber = portNumber;
        try (ServerSocket serverSocket = new ServerSocket(this.portNumber)){
            this.serverListener = serverSocket;
            while (true) {
                Socket slaveSocket = this.serverListener.accept();
                System.out.println("Client Connect");
                Slave slave = new Slave(slaveSocket);
                slave.start();
            }
        } catch (IOException e) {
            System.out.println("A problem occurred while trying to connect to server.\n\n" + e.getMessage());
        }
    }




}
