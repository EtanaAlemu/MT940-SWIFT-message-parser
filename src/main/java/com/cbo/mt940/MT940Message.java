package com.cbo.mt940;

import com.cbo.mt940.MT940Transaction;

import java.util.List;

public class MT940Message {
    private String transactionReferenceNumber;
    private String relatedReference;
    private String accountIdentification;
    private String statementNumber;
    private String openingBalance;
    private List<MT940Transaction> statementLines;
    private String informationToAccountOwner1;
    private String closingBalanceBookedFunds;
    private String closingAvailableBalance;
    private String forwardAvailableBalance;
    private String informationToAccountOwner2;

    // Constructors, getters, and setters

    // Override toString() to format the entire MT940 message
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Tag 20 - Transaction Reference Number
        appendField(sb, "20", transactionReferenceNumber);

        // Tag 21 - Related Reference
        appendField(sb, "21", relatedReference);

        // Tag 25 - Account Identification
        appendField(sb, "25", accountIdentification);

        // Tag 28C - Statement Number/Sequence Number
        appendField(sb, "28C", statementNumber);

        // Tag 60a - Opening Balance
        appendField(sb, "60a", openingBalance);

        // Tag 61 - Statement Line
        if (statementLines != null && !statementLines.isEmpty()) {
            for (MT940Transaction transaction : statementLines) {
                sb.append(transaction.toString());
            }
        }

        // Tag 86 - Information to Account Owner 1
        appendField(sb, "86", informationToAccountOwner1);

        // Tag 62a - Closing Balance (Booked Funds)
        appendField(sb, "62a", closingBalanceBookedFunds);

        // Tag 64 - Closing Available Balance (Available Funds)
        appendField(sb, "64", closingAvailableBalance);

        // Tag 65 - Forward Available Balance
        appendField(sb, "65", forwardAvailableBalance);

        // Tag 86 - Information to Account Owner 2
        appendField(sb, "86", informationToAccountOwner2);

        return sb.toString();
    }

    // Other methods as needed

    private void appendField(StringBuilder sb, String tag, String value) {
        if (value != null) {
            sb.append(":").append(tag).append(":").append(value).append("\n");
        }
    }
}
