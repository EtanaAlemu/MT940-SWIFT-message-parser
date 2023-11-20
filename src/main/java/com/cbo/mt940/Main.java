package com.cbo.mt940;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        buildMT940Message();
//        parseMT940Message();
    }
    public static void buildMT940Message(){

        // Create an instance of MT940Message
        MT940Message mt940Message = new MT940Message();

        // Set values for various tags
        mt940Message.setTransactionReferenceNumber("20231115-319");
        mt940Message.setAccountIdentification("1022200171931");
        mt940Message.setStatementNumberSequenceNumber("319/1");
        mt940Message.setOpeningBalance("C","231115","ETB","6994609,00");

        mt940Message.setStatementLines("231115","1115","D","B","533408,00","NMSC","NONREF","FT23319KHPN5","Transfer");
        mt940Message.setStatementLines("231115","1115","D","B","533408,00","NMSC","NONREF","FT23319KHPN5","Transfer");

        // Set values for closing balance tags
        mt940Message.setClosingBalanceBookedFunds("C", "231115", "ETB", "6461201,00");
        mt940Message.setClosingAvailableBalance("C", "231115", "ETB", "6461201,00");

        // Set header and trailer values
        mt940Message.setHeader("{1:F01CBORETAAXXXX22061ZFPHG}{2:I940XXXXXXXXXXXXN}{3:{108:22061ZFPHG97870}}{4:");
        mt940Message.setTrailer("-}");

        // Print the generated MT940 message
        System.out.println(mt940Message.toString());
    }

    public static void parseMT940Message(){
        // Example usage for parsing an MT940 message from a string
        String mt940String = "{1:F01CBORETAAXXXX22061ZFPHG}{2:I940XXXXXXXXXXXXN}{3:{108:22061ZFPHG97870}}{4:\n" +
                ":20:20231115-319\n" +
                ":25:1022200171931\n" +
                ":28C:319/1\n" +
                ":60F:C231115ETB6994609\n" +
                ":61:2311151115DB533408,00NMSCNONREF//FT23319KHPN5\nTransfer\n" +
                ":61:2311151115DB533408,00NMSCNONREF//FT23319KHPN5\nTransfer\n" +
                ":62F:C231115ETB6461201\n" +
                ":64:C231115ETB6461201\n" +
                "-}";

        MT940Message parsedMessage = MT940Message.parseMT940Message(mt940String);
        System.out.println(parsedMessage.toString());
    }
}
