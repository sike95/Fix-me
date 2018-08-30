package wethinkcode.fixme.router.server.Validation;

import wethinkcode.fixme.router.routing.RoutingTable;

import java.util.List;

public class DestinationVerification implements MessageValidationHandler {

    private MessageValidationHandler nextChain;
    private List<RoutingTable> routingTables;

    public DestinationVerification(List<RoutingTable> routingTables) {
        this.routingTables = routingTables;
    }

    @Override
    public void setNextHandler(MessageValidationHandler nextHandler) {
        this.nextChain = nextHandler;
    }

    @Override
    public boolean validateMessage(FixMessageValidator validMessage) {

        String market;
        boolean flag = false;
        String validFixMessage = validMessage.getMessage();
        String [] tags = validFixMessage.split("\\|");
        market = tags[5].split("=")[1];

        for (RoutingTable item : routingTables){
            if (item.getId().equals(market)){
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        } else {
            System.out.println("destination verification works");
            nextChain.validateMessage(validMessage);
            return true;
        }
    }
}
