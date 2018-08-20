package wethinkcode.fixme.router.server.Validation;

import java.nio.charset.StandardCharsets;

public class CheckSumValidator implements MessageValidationHandler {

    private MessageValidationHandler nextChain;

    @Override
    public void setNextHandler(MessageValidationHandler nextHandler) {
        this.nextChain = nextHandler;
    }

    @Override
    public void validateMessage(FixMessageValidator validMessage) {

       String message = validMessage.getMessage().replace(" ", "");
       String[] tags = message.split("\\|");
       String chekSumTag = tags[tags.length - 1];

       String checksum = chekSumTag.split("=")[1];
       String calculatedCheckSum = checkSumCalculator(message);

        // We will go to the next calculation of the chain else we will send out an error message to the
        // Client or Merchant telling them their message is invalid.
       if (checksum.contentEquals(calculatedCheckSum)) {
           nextChain.validateMessage(validMessage);
       }
       else {
           //TODO Handle error messages on request
       }
    }

    private String checkSumCalculator(String message){

        //How to get check sum is basically the ASCII value of the message % 256
        //basic C# representation is found at (http://gigi.nullneuron.net/gigilabs/calculating-the-checksum-of-a-fix-message/)

        String checkSum;
        String checkSumMessage = message.replace('|', '\u0001');
        byte[] messageBytes = checkSumMessage.getBytes(StandardCharsets.US_ASCII);
        int total = 0;

        for (int i = 0; i < message.length(); i++)
            total += messageBytes[i];

        int CalculatedChecksum = total % 256;
        checkSum = Integer.toString(CalculatedChecksum * 10);

        return checkSum;
    }
}
