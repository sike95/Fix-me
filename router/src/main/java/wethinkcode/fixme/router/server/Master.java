package wethinkcode.fixme.router.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Master {
    private ServerSocket serverListener;
    private int portNumber;

    public Master (int portNumber) {
        this.portNumber = portNumber;
        try {
            this.serverListener = new ServerSocket(this.portNumber);
        } catch (IOException e) {
            System.out.println("A problem occurred while trying to connect to server.\n\n" + e.getMessage());
        }
    }

    private void spawnSlave () {
        try {
            Socket slaveSocket = this.serverListener.accept();
        } catch (IOException e) {
            System.out.println("A problem occurred while trying to connect to server.\n\n" + e.getMessage());
        }


    }

    public void masterListener () {
        while (true)
            this.spawnSlave();
    }


}
