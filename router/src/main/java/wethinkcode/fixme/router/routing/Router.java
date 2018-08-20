package wethinkcode.fixme.router.routing;

import sun.swing.BakedArrayList;

import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

public class Router {

    List<RoutingTable> routingTables = new ArrayList<>();

    public Router(){
        //This first entry into the routingTable will be the server entering its self into the routing table
        // note: this not just looks cool but is also realistic
        addToRoutingTable(0, "127.0.0.1", null);
    }

    public void addToRoutingTable(int id, String destination, SelectionKey key){

        routingTables.add(new RoutingTable(destination, id, key));
    }

    public String getDestinationOfMessage(){

        String Destination = "";

        //TODO :: IDentify destination of message in the routing table
        return Destination;
    }
}
