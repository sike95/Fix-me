package wethinkcode.fixme.router.tests;

import java.nio.charset.StandardCharsets;

public class test {

    public static void main(String[] args) {
        String message = "8=FIX.4.1|9=61|35=A|34=1|49=EXEC|52=20121105-23:24:06|"
                + "56=BANZAI|98=0|108=30|";
        String[] tags;

        message = message.replace(" ", "");

        tags = message.trim().split("\\|");

        String chekSumTag = tags[tags.length - 1];
        int checksum = Integer.parseInt(chekSumTag.split("=")[1]);

        message = message.replace('|', '\u0001');
        byte[] messageBytes = message.getBytes(StandardCharsets.US_ASCII);
        int total = 0;

        for (int i = 0; i < message.length(); i++)
            total += messageBytes[i];

        int CalculatedChecksum = total % 256;


        System.out.print(CalculatedChecksum + "0");
//        for (String item: tags) {
//            System.out.println(item);
//        }
//
//        if (tags[0].contains("8=") && tags[tags.length - 1].contains("10=")){
//            System.out.print("YES");
//        }
    }
}
