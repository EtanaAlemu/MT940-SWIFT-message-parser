package com.cbo.mt940;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MT940Tag64 {
    private String debitCreditMark;
    private Date valueDate;
    private String currency;
    private String amount;

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
        validateDebitCreditMark(debitCreditMark);
        validateValueDate(valueDate);
        validateCurrency(currency);
        validateAmount(amount);
        this.debitCreditMark = debitCreditMark;
        this.valueDate = parseValueDate(valueDate);
        this.currency = currency;
        this.amount = parseAmount(amount);
    }

    /**
     * Validates the Debit/Credit Mark for Tag 62.
     * It should be a single character (C = Credit, D = Debit).
     *
     * @param debitCreditMark The Debit/Credit Mark to validate.
     * @throws IllegalArgumentException If the Debit/Credit Mark is invalid.
     */
    private void validateDebitCreditMark(String debitCreditMark) {
        if (debitCreditMark == null || !Pattern.matches("[CD]{1}", debitCreditMark)) {
            throw new IllegalArgumentException("Invalid Debit/Credit Mark for Tag 62");
        }
    }

    /**
     * Validates the Value Date for Tag 62.
     * It should be a string with a length of 6 characters representing YYMMDD.
     *
     * @param valueDate The Value Date to validate.
     * @throws IllegalArgumentException If the Value Date is invalid.
     */
    private void validateValueDate(String valueDate) {
        String pattern = "\\d{6}";
        if (valueDate == null || !Pattern.matches(pattern, valueDate)) {
            throw new IllegalArgumentException("Invalid Value Date for Tag 62");
        }
    }

    /**
     * Parses the Value Date from the provided string (YYMMDD format).
     *
     * @param valueDate The Value Date string to parse.
     * @return The parsed Value Date as a Date object.
     * @throws IllegalArgumentException If the Value Date is invalid.
     */
    private Date parseValueDate(String valueDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
            dateFormat.setLenient(false);
            return dateFormat.parse(valueDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Value Date for Tag 62");
        }
    }

    /**
     * Validates the Currency for Tag 62.
     * It should be a string with a length of 3 characters representing the ISO currency code.
     *
     * @param currency The Currency to validate.
     * @throws IllegalArgumentException If the Currency is invalid.
     */
    private void validateCurrency(String currency) {
        if (currency == null || !Pattern.matches("[A-Z]{3}", currency)) {
            throw new IllegalArgumentException("Invalid Currency for Tag 62");
        }
    }

    /**
     * Validates the Amount for Tag 62.
     * It should be a string representing the amount with a comma as a decimal separator.
     *
     * @param amount The Amount to validate.
     * @throws IllegalArgumentException If the Amount is invalid.
     */
    private void validateAmount(String amount) {
        String pattern = "\\d{1,3}(,\\d{3})*(\\.\\d{2})?";
        if (amount == null || !Pattern.matches(pattern, amount)) {
            throw new IllegalArgumentException("Invalid Amount for Tag 64");
        }
    }

    /**
     * Parses the Amount from the provided string (with comma as decimal separator).
     *
     * @param amount The Amount string to parse.
     * @return The parsed Amount as a formatted string.
     * @throws IllegalArgumentException If the Amount is invalid.
     */
    private String parseAmount(String amount) {
        try {

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            decimalFormat.setParseBigDecimal(true);
            double parsedValue = decimalFormat.parse(amount).doubleValue();

            // Remove commas and replace dots with commas
            return decimalFormat.format(parsedValue).replace(",", "").replace(".", ",");
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Amount for Tag 64");
        }
    }

    // Other methods as needed

    /**
     * Returns the formatted Tag 64 content.
     *
     * @return The formatted Tag 64 content.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        return String.format(":64:%s%s%s%s\n", debitCreditMark, dateFormat.format(valueDate), currency, amount);
    }

    public static void main(String[] args) {
        // Example usage
        MT940Tag64 tag64 = new MT940Tag64();
        tag64.setClosingAvailableBalance("C", "231115", "ETB", "6,461,201.00");
        System.out.println(tag64.toString());
    }
}
