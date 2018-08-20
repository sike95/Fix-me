package wethinkcode.fixme.router.server.Validation;

public class DestinationVerification implements MessageValidationHandler {

    private MessageValidationHandler nextChain;

    @Override
    public void setNextHandler(MessageValidationHandler nextHandler) {
        this.nextChain = nextHandler;
    }

    @Override
    public void validateMessage(FixMessageValidator validMessage) {


    }
}
