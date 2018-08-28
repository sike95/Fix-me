package wethinkcode.fixme.router.routing;

import lombok.Getter;
import wethinkcode.fixme.router.server.Server;

import java.nio.channels.SelectionKey;

@Getter
public class RoutingTable {

    private String netWorkDestination;
    private String    id;
    private SelectionKey key;

    public RoutingTable(String netWorkDestination, String id, SelectionKey key){
        this.id = id;
        this.netWorkDestination = netWorkDestination;
        this.key = key;
    }


}
