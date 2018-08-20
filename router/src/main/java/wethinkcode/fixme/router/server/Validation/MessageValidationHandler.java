package wethinkcode.fixme.router.server.Validation;

public interface MessageValidationHandler {

    public void setNextHandler(MessageValidationHandler nextHandler);

    public void validateMessage(FixMessageValidator validMessage);
}
