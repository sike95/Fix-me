package wethinkcode.fixme.router.routing;

import lombok.Getter;
import wethinkcode.fixme.router.server.Server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Router {

    private List<RoutingTable> routingTable ;
    private Server broker;
    private Server market;

    public Router(){
        this.routingTable = new ArrayList<>();
        try {
            this.broker = new Server();
            this.broker.startServer(this.routingTable);
            this.addToRoutingTable("000000", this.broker.getListenAddress().toString(), null);// why is this key null?

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToRoutingTable(String id, String destination, SelectionKey key){

        this.routingTable.add(new RoutingTable(destination, id, key));
    }



    public static void main(String[] args) {
        new Router();
    }
}
