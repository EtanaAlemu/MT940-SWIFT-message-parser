package com.cbo.mt940;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MT940Message {

    // Fields for MT940Message
    private String header;
    private String transactionReferenceNumber;
    private String accountIdentification;
    private String statementNumber ;
    private String sequenceNumber ;

    private String statementNumberSequenceNumber;
    private String openingBalance;
    private List<MT940Tag61> statementLines;
    private String closingBalanceBookedFunds;
    private String closingAvailableBalance;
    private String trailer;


    // Constructors, getters, and setters


    public MT940Message() {
        this.statementLines = new ArrayList<>();
    }

    /**
     * Sets the Header for MT940.
     *
     * @param header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Sets the Transaction Reference Number for Tag 20.
     *
     * @param transactionReferenceNumber The Transaction Reference Number to set.
     * @throws IllegalArgumentException If the provided Transaction Reference Number is invalid.
     */
    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        validateField("20", transactionReferenceNumber, "\\d{8}-\\d{3}");
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    /**
     * Sets the Account Identification for Tag 25.
     *
     * @param accountIdentification The Account Identification to set.
     * @throws IllegalArgumentException If the provided Account Identification is invalid.
     */
    public void setAccountIdentification(String accountIdentification) {
        validateField("25", accountIdentification, "[A-Z0-9]{1,35}");
        this.accountIdentification = accountIdentification;
    }

    /**
     * Sets the Statement Number / Sequence Number for Tag 28C.
     *
     * @param statementNumberSequenceNumber The Statement Number to set.
     * @throws IllegalArgumentException If the provided Statement Number is invalid.
     */
    public void setStatementNumberSequenceNumber(String statementNumberSequenceNumber) {
        validateField("28C", statementNumberSequenceNumber, "\\d{1,5}/\\d{1,5}");
        this.statementNumberSequenceNumber = statementNumberSequenceNumber;
    }


    /**
     * Sets the Statement Number for Tag 28C.
     *
     * @param statementNumber The Statement Number to set.
     * @throws IllegalArgumentException If the provided Statement Number is invalid.
     */
    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }


    /**
     * Sets the Sequence Number for Tag 28C.
     *
     * @param sequenceNumber The Sequence Number to set.
     * @throws IllegalArgumentException If the provided Sequence Number is invalid.
     */
    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Sets the Statement Line for Tag 61.
     *
     * @param valueDate             The value date in the format YYMMDD.
     * @param entryDate             The entry date in the format MMDD (optional).
     * @param debitCreditMark       The debit/credit mark (C = Credit, D = Debit, RD = Reverse Debit, RC = Reverse Credit).
     * @param fundsCode             The funds code (optional).
     * @param amount                The transaction amount.
     * @param transactionTypeIDCode The transaction type ID code.
     * @param customerReference     The customer reference.
     * @param bankReference         The bank reference (optional).
     * @param supplementaryDetails  The supplementary details (optional).
     * @throws IllegalArgumentException If the provided parameters are invalid.
     */
    public void setStatementLines(String valueDate, String entryDate, String debitCreditMark, String fundsCode, String amount, String transactionTypeIDCode, String customerReference, String bankReference, String supplementaryDetails)  {
        MT940Tag61 transaction = new MT940Tag61();

        transaction.setValueDate(valueDate);
        transaction.setEntryDate(entryDate);
        transaction.setDebitCreditMark(debitCreditMark);
        transaction.setFundsCode(fundsCode);
        transaction.setAmount(amount);
        transaction.setTransactionTypeIDCode(transactionTypeIDCode);
        transaction.setCustomerReference(customerReference);
        transaction.setBankReference(bankReference);
        transaction.setSupplementaryDetails(supplementaryDetails);

        if (statementLines == null) {
            throw new IllegalArgumentException("Statement lines cannot be null");
        }
        this.statementLines.add(transaction);
    }

    /**
     * Sets the Opening Balance for Tag 60F.
     *
     * @param debitCreditMark The Debit/Credit Mark to set (C = Credit, D = Debit).
     * @param statementDate   The Statement Date to set (format: YYMMDD).
     * @param currency        The Currency to set (ISO currency code).
     * @param amount          The Amount to set (with comma as decimal separator).
     * @throws IllegalArgumentException If the provided parameters are invalid.
     */
    public void setOpeningBalance(String debitCreditMark, String statementDate, String currency, String amount) {
       MT940Tag60F tag60F = new MT940Tag60F();
       tag60F.setOpeningBalance(debitCreditMark,  statementDate, currency, amount);
       this.openingBalance = tag60F.toString();
    }

    /**
     * Sets the Closing Balance (Booked Funds) for Tag 62F.
     *
     * @param debitCreditMark The Debit/Credit Mark to set (C = Credit, D = Debit).
     * @param valueDate       The Value Date to set (format: YYMMDD).
     * @param currency        The Currency to set (ISO currency code).
     * @param amount          The Amount to set (with comma as decimal separator).
     * @throws IllegalArgumentException If the provided parameters are invalid.
     */
    public void setClosingBalanceBookedFunds(String debitCreditMark, String valueDate, String currency, String amount) {
        MT940Tag62F tag62F = new MT940Tag62F();
        tag62F.setClosingBalance(debitCreditMark,valueDate,currency,amount);
        this.closingBalanceBookedFunds = tag62F.toString();
    }

    /**
     * Sets the Closing Available Balance (Available Funds) for Tag 64.
     *
     * @param debitCreditMark The Debit/Credit Mark to set (C = Credit, D = Debit).
     * @param valueDate       The Value Date to set (format: YYMMDD).
     * @param currency        The Currency to set (ISO currency code).
     * @param amount          The Amount to set (with comma as decimal separator).
     * @throws IllegalArgumentException If the provided parameters are invalid.
     */
    public void setClosingAvailableBalance(String debitCreditMark, String valueDate, String currency, String amount) {
        MT940Tag64 tag64 = new MT940Tag64();
        tag64.setClosingAvailableBalance(debitCreditMark,valueDate,currency,amount);
        this.closingAvailableBalance = tag64.toString();
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getHeader() {
        return header;
    }

    public String getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public String getAccountIdentification() {
        return accountIdentification;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public String getStatementNumberSequenceNumber() {
        return statementNumberSequenceNumber;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public List<MT940Tag61> getStatementLines() {
        return statementLines;
    }

    public String getClosingBalanceBookedFunds() {
        return closingBalanceBookedFunds;
    }

    public String getClosingAvailableBalance() {
        return closingAvailableBalance;
    }

    public String getTrailer() {
        return trailer;
    }

    /**
     * Parses an MT940 message from the provided string.
     *
     * @param mt940String The MT940 message string to parse.
     * @return The parsed MT940 message.
     * @throws IllegalArgumentException If the provided MT940 string is invalid.
     */
    public static MT940Message parseMT940Message(String mt940String) {
        MT940Message mt940Message = new MT940Message();

        // Use regular expressions to extract values from the MT940 string

        // Define the header pattern
        Pattern headerPattern = Pattern.compile("(\\{1:(.*?)\\}\\{2:(.*?)\\}\\{3:(.*?)\\}\\{4:)\n");
        Matcher headerMatcher = headerPattern.matcher(mt940String);
        if (headerMatcher.find()) {
            // Extract the matched parts
            String headerField1 = headerMatcher.group(1);
            String headerField2 = headerMatcher.group(2);
            String headerField3 = headerMatcher.group(3);

            // Print the results
//            System.out.println("Header Field 1: " + headerField1);
//            System.out.println("Header Field 2: " + headerField2);
//            System.out.println("Header Field 3: " + headerField3);
            mt940Message.setHeader(headerMatcher.group(1));
        } else {
            throw new IllegalArgumentException("Missing or invalid Header in the MT940 message");
        }

        Pattern tag20Pattern = Pattern.compile(":20:(\\d{8}-\\d{3})\n");
        Matcher tag20Matcher = tag20Pattern.matcher(mt940String);
        if (tag20Matcher.find()) {
            mt940Message.setTransactionReferenceNumber(tag20Matcher.group(1));
//            System.out.println("tag20 : " + tag20Matcher.group(1));
        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 20 in the MT940 message");
        }

        Pattern tag25Pattern = Pattern.compile(":25:([A-Z0-9]{1,35})\n");
        Matcher tag25Matcher = tag25Pattern.matcher(mt940String);
        if (tag25Matcher.find()) {
            mt940Message.setAccountIdentification(tag25Matcher.group(1));
//            System.out.println("tag25 : " + tag25Matcher.group(1));
        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 25 in the MT940 message");
        }

        Pattern tag28CPattern = Pattern.compile(":28C:(\\d{1,5})/(\\d{1,5})\n");
        Matcher tag28CMatcher = tag28CPattern.matcher(mt940String);
        if (tag28CMatcher.find()) {
            mt940Message.setStatementNumberSequenceNumber(tag28CMatcher.group(1)+"/"+tag28CMatcher.group(2));
//            System.out.println("tag28 : " + tag28CMatcher.group(1)+"/"+tag28CMatcher.group(2));

                mt940Message.setSequenceNumber(tag28CMatcher.group(1));

                mt940Message.setSequenceNumber(tag28CMatcher.group(1));


        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 28C in the MT940 message");
        }

        Pattern tag60FPattern = Pattern.compile(":60F:([CD])(\\d{6})([A-Z]{3})(\\d{1,15})((,\\d{2})?)\n");
        Matcher tag60FMatcher = tag60FPattern.matcher(mt940String);
        if (tag60FMatcher.find()) {
//            System.out.println("tag60F Field 1: " + tag60FMatcher.group(1));
//            System.out.println("tag60F Field 2: " + tag60FMatcher.group(2));
//            System.out.println("tag60F Field 3: " + tag60FMatcher.group(3));
//            System.out.println("tag60F Field 4: " + tag60FMatcher.group(4));
            mt940Message.setOpeningBalance(tag60FMatcher.group(1), tag60FMatcher.group(2), tag60FMatcher.group(3), tag60FMatcher.group(4));
        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 60 in the MT940 message");
        }


        Pattern tag61Pattern = Pattern.compile(":61:(\\d{6})(\\d{4})([CD])([B])(\\d{1,15},?\\d{2}?)([NF][A-Z]{3})([A-Z0-9]{1,16})//([A-Z0-9]{1,16})\n([A-Za-z0-9]{1,34})\n");
        Matcher tag61Matcher = tag61Pattern.matcher(mt940String);

        // Process all occurrences of Tag 61
        while (tag61Matcher.find()) {
//            System.out.println("tag61 Field 1: " + tag61Matcher.group(1));
//            System.out.println("tag61 Field 2: " + tag61Matcher.group(2));
//            System.out.println("tag61 Field 3: " + tag61Matcher.group(3));
//            System.out.println("tag61 Field 4: " + tag61Matcher.group(4));
//            System.out.println("tag61 Field 5: " + tag61Matcher.group(5));
//            System.out.println("tag61 Field 6: " + tag61Matcher.group(6));
//            System.out.println("tag61 Field 7: " + tag61Matcher.group(7));
//            System.out.println("tag61 Field 8: " + tag61Matcher.group(8));
//            System.out.println("tag61 Field 9: " + tag61Matcher.group(9));
            mt940Message.setStatementLines(tag61Matcher.group(1), tag61Matcher.group(2), tag61Matcher.group(3), tag61Matcher.group(4), tag61Matcher.group(5), tag61Matcher.group(6), tag61Matcher.group(7), tag61Matcher.group(8), tag61Matcher.group(9));
        }


        Pattern tag62FPattern = Pattern.compile(":62F:([CD])(\\d{6})([A-Z]{3})(\\d{1,15})((,\\d{2})?)\n");
        Matcher tag62FMatcher = tag62FPattern.matcher(mt940String);
        if (tag62FMatcher.find()) {
//            System.out.println("tag62F Field 1: " + tag62FMatcher.group(1));
//            System.out.println("tag62F Field 2: " + tag62FMatcher.group(2));
//            System.out.println("tag62F Field 3: " + tag62FMatcher.group(3));
//            System.out.println("tag62F Field 4: " + tag62FMatcher.group(4));
            mt940Message.setClosingBalanceBookedFunds(tag62FMatcher.group(1), tag62FMatcher.group(2), tag62FMatcher.group(3), tag62FMatcher.group(4));
        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 62F in the MT940 message");
        }


        Pattern tag64Pattern = Pattern.compile(":64:([CD])(\\d{6})([A-Z]{3})(\\d{1,15},?\\d{2}?)\n");
        Matcher tag64Matcher = tag64Pattern.matcher(mt940String);
        if (tag64Matcher.find()) {
//            System.out.println("tag64 Field 1: " + tag64Matcher.group(1));
//            System.out.println("tag64 Field 2: " + tag64Matcher.group(2));
//            System.out.println("tag64 Field 3: " + tag64Matcher.group(3));
//            System.out.println("tag64 Field 4: " + tag64Matcher.group(4));
            mt940Message.setClosingAvailableBalance(tag64Matcher.group(1), tag64Matcher.group(2), tag64Matcher.group(3), tag64Matcher.group(4));
        } else {
            throw new IllegalArgumentException("Missing or invalid Tag 64 in the MT940 message");
        }

        Pattern trailerPattern = Pattern.compile("(-\\})");
        Matcher trailerMatcher = trailerPattern.matcher(mt940String);
        if (trailerMatcher.find()) {
//            System.out.println("trailer: " + trailerMatcher.group(1));
            mt940Message.setTrailer(trailerMatcher.group(1));
        } else {
            throw new IllegalArgumentException("Missing or invalid trailer in the MT940 message");
        }
        return mt940Message;
    }

    // Override toString() to format the entire MT940 message
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(header).append("\n");

        // Tag 20 - Transaction Reference Number
        appendField(sb, "20", transactionReferenceNumber);

        // Tag 25 - Account Identification
        appendField(sb, "25", accountIdentification);

        // Tag 28C - Statement Number/Sequence Number
        appendField(sb, "28C", statementNumberSequenceNumber);

        // Tag 60a - Opening Balance
        sb.append(openingBalance.toString());

        // Tag 61 - Statement Line
        if (statementLines != null && !statementLines.isEmpty()) {
            for (MT940Tag61 statementLine : statementLines) {
                sb.append(statementLine.toString());
            }
        }

        // Tag 62 - Closing Balance (Booked Funds)
        sb.append(closingBalanceBookedFunds);

        // Tag 64 - Closing Available Balance (Available Funds)
        sb.append(closingAvailableBalance);

        sb.append(trailer);

        return sb.toString();
    }

    private void appendField(StringBuilder sb, String tag, String value) {
        if (value != null) {
            sb.append(String.format(":%s:%s\n", tag, value));
        }
    }

    private void validateField(String tag, String value, String pattern) {
        if (value == null || !Pattern.matches(pattern, value)) {
            throw new IllegalArgumentException("Invalid " + tag + " value");
        }
    }
}
