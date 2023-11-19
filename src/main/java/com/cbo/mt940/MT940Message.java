package com.cbo.mt940;

import com.cbo.mt940.MT940Transaction;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class MT940Message {

    private String header;
    private String transactionReferenceNumber;
    private String relatedReference;
    private String accountIdentification;
    private String statementNumber;
    private String sequenceNumber;
    private String openingBalance;
    private List<MT940Transaction> statementLines;
    private MT940Tag62 closingBalanceBookedFunds;
    private MT940Tag64 closingAvailableBalance;

    private String trailer;

    // Constructors, getters, and setters

    /**
     * Sets the Transaction Reference Number for Tag 20.
     * The Transaction Reference Number is generated based on the current date (yyyymmdd) and the current day of the year.
     *
     * @param transactionReferenceNumber The Transaction Reference Number to set.
     * @throws IllegalArgumentException If the provided Transaction Reference Number is invalid.
     */
    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        String pattern = "\\d{8}-\\d{3}";
        if (transactionReferenceNumber == null || !Pattern.matches(pattern, transactionReferenceNumber)) {
            throw new IllegalArgumentException("Invalid Transaction Reference Number for Tag 20");
        }
        try {
            // Extract the date part for additional validation
            String datePart = transactionReferenceNumber.substring(0, 8);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            dateFormat.parse(datePart);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date part in Transaction Reference Number");
        }
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    /**
     * Sets the Account Identification for Tag 25.
     *
     * @param accountIdentification The Account Identification to set.
     * @throws IllegalArgumentException If the provided Account Identification is invalid.
     */
    public void setAccountIdentification(String accountIdentification) {
        String pattern = ".{1,35}";
        if (accountIdentification == null || !Pattern.matches(pattern, accountIdentification)) {
            throw new IllegalArgumentException("Invalid Account Identification for Tag 25");
        }
        this.accountIdentification = accountIdentification;
    }

    /**
     * Sets the Statement Number and Sequence Number for Tag 28C.
     *
     * @param statementNumber The Statement Number to set.
     * @param sequenceNumber  The Sequence Number to set (optional).
     * @throws IllegalArgumentException If the provided Statement Number or Sequence Number is invalid.
     */
    public void setStatementAndSequenceNumber(String statementNumber, String sequenceNumber) {
        String pattern = "\\d{1,5}";
        if (statementNumber == null || !Pattern.matches(pattern, statementNumber)) {
            throw new IllegalArgumentException("Invalid Statement Number for Tag 28C");
        }
        if (sequenceNumber != null) {
            pattern = "\\d{1,5}";
            if (!Pattern.matches(pattern, sequenceNumber)) {
                throw new IllegalArgumentException("Invalid Sequence Number for Tag 28C");
            }
        }
        this.statementNumber = statementNumber;
        this.sequenceNumber = sequenceNumber;
    }

    // Override toString() to format the entire MT940 message
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(header);

        // Tag 20 - Transaction Reference Number
        appendField(sb, "20", transactionReferenceNumber);

        // Tag 21 - Related Reference
        appendField(sb, "21", relatedReference);

        // Tag 25 - Account Identification
        appendField(sb, "25", accountIdentification);

        // Tag 28C - Statement Number/Sequence Number
        appendField(sb, "28C", statementNumber + (sequenceNumber != null ? "/" + sequenceNumber : ""));

        // Tag 60a - Opening Balance
        appendField(sb, "60F", openingBalance);

        // Tag 61 - Statement Line
        if (statementLines != null && !statementLines.isEmpty()) {
            for (MT940Transaction transaction : statementLines) {
                sb.append(transaction.toString());
            }
        }

        // Tag 62 - Closing Balance (Booked Funds)
        sb.append(closingBalanceBookedFunds.toString());


        // Tag 64 - Closing Available Balance (Available Funds)
        sb.append(closingAvailableBalance.toString());

        sb.append(trailer);


        return sb.toString();
    }

    // Other methods as needed

    private void appendField(StringBuilder sb, String tag, String value) {
        if (value != null) {
            sb.append(":").append(tag).append(":").append(value).append("\n");
        }
    }
}
