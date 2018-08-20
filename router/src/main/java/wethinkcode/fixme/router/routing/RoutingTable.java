package wethinkcode.fixme.router.routing;

import wethinkcode.fixme.router.server.Server;

import java.nio.channels.SelectionKey;

public class RoutingTable {

    private String NetWorkDestination;
    private int    id;
    private SelectionKey channel;

    public RoutingTable(String netWorkDestination, int ID, SelectionKey channel){
        this.id = ID;
        this.NetWorkDestination = netWorkDestination;
        this.channel = channel;
    }


}
