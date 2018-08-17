package wethinkcode.fixme.router.server;

public class IDGenerator {

    private static IDGenerator idGenerator = new IDGenerator();
    private static int units = 0, tens = 0, hundreds = 0, thousands = 0, tenThousands = 0, hundredThousands = 0;

    private IDGenerator(){

    }

    public static IDGenerator getIdGenerator() {
        return idGenerator;
    }

    public static String generateId() {

        if (units < 9) {
            units++;
        }
        else {
            units = 0;
            tens++;
        }
        if (tens > 9) {
            tens = 0;
            hundreds++;
        }
        if (hundreds > 9) {
            hundreds = 0;
            thousands++;
        }
        if (thousands > 9) {
            thousands = 0;
            tenThousands++;
        }
        if (tenThousands > 9) {
            tenThousands = 0;
            hundredThousands++;
        }
        if (hundredThousands > 9) {
            return "error";
        }
        return hundredThousands + "" + tenThousands + "" + thousands + "" + hundreds + "" + tens + "" + units;

    }
}
