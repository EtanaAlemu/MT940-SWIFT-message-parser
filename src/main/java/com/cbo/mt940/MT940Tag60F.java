package com.cbo.mt940;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class MT940Tag60F {
    private String debitCreditMark;
    private Date statementDate;
    private String currency;
    private String amount;

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
        validateDebitCreditMark(debitCreditMark);
        validateStatementDate(statementDate);
        validateCurrency(currency);
        validateAmount(amount);
        this.debitCreditMark = debitCreditMark;
        this.statementDate = parseStatementDate(statementDate);
        this.currency = currency;
        this.amount = parseAmount(amount);
    }


    /**
     * Sets the Debit/Credit Mark for Tag 60F.
     *
     * @param debitCreditMark The Debit/Credit Mark to set.
     * @throws IllegalArgumentException If the provided Debit/Credit Mark is invalid.
     */
    public void setDebitCreditMark(String debitCreditMark) {
        validateDebitCreditMark(debitCreditMark);
        this.debitCreditMark = debitCreditMark;
    }

    /**
     * Sets the Statement Date for Tag 60F.
     *
     * @param statementDate The Statement Date to set.
     * @throws IllegalArgumentException If the provided Statement Date is invalid.
     */
    public void setStatementDate(String statementDate) {
        validateStatementDate(statementDate);
        this.statementDate = parseStatementDate(statementDate);
    }

    /**
     * Sets the Currency for Tag 60F.
     *
     * @param currency The Currency to set.
     * @throws IllegalArgumentException If the provided Currency is invalid.
     */
    public void setCurrency(String currency) {
        validateCurrency(currency);
        this.currency = currency;
    }

    /**
     * Sets the Amount for Tag 60F.
     *
     * @param amount The Amount to set.
     * @throws IllegalArgumentException If the provided Amount is invalid.
     */
    public void setAmount(String amount) {
        validateAmount(amount);
        this.amount = parseAmount(amount);
    }

    /**
     * Gets the Debit/Credit Mark for Tag 60F.
     *
     * @return The Debit/Credit Mark.
     */
    public String getDebitCreditMark() {
        return debitCreditMark;
    }

    /**
     * Gets the Statement Date for Tag 60F.
     *
     * @return The Statement Date.
     */
    public Date getStatementDate() {
        return statementDate;
    }

    /**
     * Gets the Currency for Tag 60F.
     *
     * @return The Currency.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the Amount for Tag 60F.
     *
     * @return The Amount.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Validates the Debit/Credit Mark for Tag 60F.
     * It should be a single character (C = Credit, D = Debit).
     *
     * @param debitCreditMark The Debit/Credit Mark to validate.
     * @throws IllegalArgumentException If the Debit/Credit Mark is invalid.
     */
    private void validateDebitCreditMark(String debitCreditMark) {
        if (debitCreditMark == null || !Pattern.matches("[CD]{1}", debitCreditMark)) {
            throw new IllegalArgumentException("Invalid Debit/Credit Mark for Tag 60F");
        }
    }

    /**
     * Validates the Statement Date for Tag 60F.
     * It should be a string with a length of 6 characters representing YYMMDD.
     *
     * @param statementDate The Statement Date to validate.
     * @throws IllegalArgumentException If the Statement Date is invalid.
     */
    private void validateStatementDate(String statementDate) {
        String pattern = "\\d{6}";
        if (statementDate == null || !Pattern.matches(pattern, statementDate)) {
            throw new IllegalArgumentException("Invalid Statement Date for Tag 60F");
        }
    }

    /**
     * Parses the Statement Date from the provided string (YYMMDD format).
     *
     * @param statementDate The Statement Date string to parse.
     * @return The parsed Statement Date as a Date object.
     * @throws IllegalArgumentException If the Statement Date is invalid.
     */
    private Date parseStatementDate(String statementDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
            dateFormat.setLenient(false);
            return dateFormat.parse(statementDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Statement Date for Tag 60F");
        }
    }

    /**
     * Validates the Currency for Tag 60F.
     * It should be a string with a length of 3 characters representing the ISO currency code.
     *
     * @param currency The Currency to validate.
     * @throws IllegalArgumentException If the Currency is invalid.
     */
    private void validateCurrency(String currency) {
        if (currency == null || !Pattern.matches("[A-Z]{3}", currency)) {
            throw new IllegalArgumentException("Invalid Currency for Tag 60F");
        }
    }

    /**
     * Validates the Amount for Tag 60F.
     * It should be a string representing the amount with a comma as a decimal separator.
     *
     * @param amount The Amount to validate.
     * @throws IllegalArgumentException If the Amount is invalid.
     */
    private void validateAmount(String amount) {
        String pattern = "\\d{1,15}(,\\d{2})?";
        if (amount == null || !Pattern.matches(pattern, amount)) {
            throw new IllegalArgumentException("Invalid Amount for Tag 60F");
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
            throw new IllegalArgumentException("Invalid Amount for Tag 60F");
        }
    }

    // Other methods as needed

    /**
     * Returns the formatted Tag 60F content.
     *
     * @return The formatted Tag 60F content.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        return String.format(":60F:%s%s%s%s\n", debitCreditMark, dateFormat.format(statementDate), currency,
                amount);
    }

}
