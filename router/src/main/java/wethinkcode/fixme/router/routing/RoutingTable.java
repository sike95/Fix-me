package wethinkcode.fixme.router.routing;

import lombok.Getter;
import wethinkcode.fixme.router.server.Server;

import java.nio.channels.SelectionKey;

@Getter
public class RoutingTable {

    private String    id;
    private SelectionKey key;

    public RoutingTable(String id, SelectionKey key){
        this.id = id;
        this.key = key;
    }


}
