package wethinkcode.fixme.router.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Slave extends Thread{
    private Socket socket;
    private static int classId;

    public Slave (Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //TODO : assign unique id to client of the server and relay back to client
            BufferedReader input  = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter output = new PrintWriter(this.socket.getOutputStream(), true);

            while (true) {
                String echoString = input.readLine();
                if (echoString.equals("exit")) {
                    break;
                }
                output.println(echoString);
            }
        }catch (IOException e) {
            System.out.println("A problem occurred whilst creating a slave process.\n\n"+ e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("A problem occurred whilst closing the slave process.\n\n" + e.getMessage());
            }
        }
    }

    private static int incrementClassId () {
        return classId++;
    }

    private String generateId () {
        int units = 0, tens = 0, hundreds = 0, thousands = 0, tenThousands = 0, hundredThousands = 0;


        if (incrementClassId() <= 9) {
            units++;
        }
        else {
            units = 0;
            tens++;
        }
        if (tens > 9) {
            tens = 0;
            hundreds++;
        }
        if (hundreds > 9) {
            hundreds = 0;
            thousands++;
        }
        if (thousands > 9) {
            thousands = 0;
            tenThousands++;
        }
        if (tenThousands > 9) {
            tenThousands = 0;
            hundredThousands++;
        }
        if (hundredThousands > 9) {
            return "error";
        }
        return hundredThousands + "" + tenThousands + "" + thousands + "" + hundreds + "" + tens + "" + units;

    }
}
