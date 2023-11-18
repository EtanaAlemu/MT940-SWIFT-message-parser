package com.cbo.mt940;

import java.util.regex.Pattern;

public class MT940Transaction {
    private String valueDate;
    private String entryDate;
    private String debitCreditMark;
    private String fundsCode;
    private String amount;
    private String transactionTypeIDCode;
    private String customerReference;
    private String bankReference;
    private String supplementaryDetails;

    // Constructors, getters, and setters

    /**
     * Sets the value date for the transaction.
     * @param valueDate The value date in the format YYMMDD.
     * @throws IllegalArgumentException If the value date is invalid.
     */
    public void setValueDate(String valueDate) {
        if (valueDate == null || !Pattern.matches("\\d{6}", valueDate)) {
            throw new IllegalArgumentException("Invalid value date format");
        }
        this.valueDate = valueDate;
    }

    /**
     * Sets the entry date for the transaction.
     * @param entryDate The entry date in the format MMDD.
     * @throws IllegalArgumentException If the entry date is invalid.
     */
    public void setEntryDate(String entryDate) {
        if (entryDate != null && !Pattern.matches("\\d{4}", entryDate)) {
            throw new IllegalArgumentException("Invalid entry date format");
        }
        this.entryDate = entryDate;
    }

    /**
     * Sets the debit/credit mark for the transaction.
     * @param debitCreditMark The debit/credit mark (C = Credit, D = Debit, RD = Reverse Debit, RC = Reverse Credit).
     * @throws IllegalArgumentException If the debit/credit mark is invalid.
     */
    public void setDebitCreditMark(String debitCreditMark) {
        if (debitCreditMark == null || !Pattern.matches("[CD]{1,2}", debitCreditMark)) {
            throw new IllegalArgumentException("Invalid debit/credit mark");
        }
        this.debitCreditMark = debitCreditMark;
    }

    /**
     * Sets the funds code for the transaction.
     * @param fundsCode The funds code (optional).
     * @throws IllegalArgumentException If the funds code is invalid.
     */
    public void setFundsCode(String fundsCode) {
        if (fundsCode != null && !Pattern.matches("[A-Za-z0-9 .,()/'+:?{}]{1}", fundsCode)) {
            throw new IllegalArgumentException("Invalid funds code");
        }
        this.fundsCode = fundsCode;
    }

    /**
     * Sets the amount for the transaction.
     * @param amount The transaction amount.
     * @throws IllegalArgumentException If the amount is invalid.
     */
    public void setAmount(String amount) {
        if (amount == null || !Pattern.matches("\\d{1,15},\\d{2}", amount)) {
            throw new IllegalArgumentException("Invalid amount format");
        }
        this.amount = amount;
    }

    /**
     * Sets the transaction type ID code for the transaction.
     * @param transactionTypeIDCode The transaction type ID code.
     * @throws IllegalArgumentException If the transaction type ID code is invalid.
     */
    public void setTransactionTypeIDCode(String transactionTypeIDCode) {
        if (transactionTypeIDCode == null || !Pattern.matches("[A-Za-z0-9 .,()/'+:?{}]{1,3}", transactionTypeIDCode)) {
            throw new IllegalArgumentException("Invalid transaction type ID code");
        }
        this.transactionTypeIDCode = transactionTypeIDCode;
    }

    /**
     * Sets the customer reference for the transaction.
     * @param customerReference The customer reference.
     * @throws IllegalArgumentException If the customer reference is invalid.
     */
    public void setCustomerReference(String customerReference) {
        if (customerReference == null || !Pattern.matches("[A-Za-z0-9 .,()/'+:?{}]{1,16}", customerReference)) {
            throw new IllegalArgumentException("Invalid customer reference");
        }
        this.customerReference = customerReference;
    }

    /**
     * Sets the bank reference for the transaction.
     * @param bankReference The bank reference (optional).
     * @throws IllegalArgumentException If the bank reference is invalid.
     */
    public void setBankReference(String bankReference) {
        if (bankReference != null && !Pattern.matches("[A-Za-z0-9 .,()/'+:?{}]{1,16}", bankReference)) {
            throw new IllegalArgumentException("Invalid bank reference");
        }
        this.bankReference = bankReference;
    }

    /**
     * Sets the supplementary details for the transaction.
     * @param supplementaryDetails The supplementary details (optional).
     * @throws IllegalArgumentException If the supplementary details are invalid.
     */
    public void setSupplementaryDetails(String supplementaryDetails) {
        if (supplementaryDetails != null && !Pattern.matches("[A-Za-z0-9 .,()/'+:?{}]{1,34}", supplementaryDetails)) {
            throw new IllegalArgumentException("Invalid supplementary details");
        }
        this.supplementaryDetails = supplementaryDetails;
    }

    /**
     * Gets the value date for the transaction.
     * @return The value date in the format YYMMDD.
     */
    public String getValueDate() {
        return valueDate;
    }

    /**
     * Gets the entry date for the transaction.
     * @return The entry date in the format MMDD.
     */
    public String getEntryDate() {
        return entryDate;
    }

    /**
     * Gets the debit/credit mark for the transaction.
     * @return The debit/credit mark (C = Credit, D = Debit, RD = Reverse Debit, RC = Reverse Credit).
     */
    public String getDebitCreditMark() {
        return debitCreditMark;
    }

    /**
     * Gets the funds code for the transaction.
     * @return The funds code (optional).
     */
    public String getFundsCode() {
        return fundsCode;
    }

    /**
     * Gets the amount for the transaction.
     * @return The transaction amount.
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Gets the transaction type ID code for the transaction.
     * @return The transaction type ID code.
     */
    public String getTransactionTypeIDCode() {
        return transactionTypeIDCode;
    }

    /**
     * Gets the customer reference for the transaction.
     * @return The customer reference.
     */
    public String getCustomerReference() {
        return customerReference;
    }

    /**
     * Gets the bank reference for the transaction.
     * @return The bank reference (optional).
     */
    public String getBankReference() {
        return bankReference;
    }

    /**
     * Gets the supplementary details for the transaction.
     * @return The supplementary details (optional).
     */
    public String getSupplementaryDetails() {
        return supplementaryDetails;
    }


    // Override toString() to format the Tag 61 content
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(":61:");
        sb.append(valueDate)
                .append(entryDate != null ? entryDate : "")
                .append(debitCreditMark)
                .append(fundsCode != null ? fundsCode : "")
                .append(amount)
                .append(transactionTypeIDCode)
                .append(customerReference)
                .append(bankReference != null ? "//" + bankReference : "")
                .append(supplementaryDetails != null ? "\n" + supplementaryDetails : "");

        return sb.toString();
    }



}
