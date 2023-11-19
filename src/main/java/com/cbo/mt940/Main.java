package com.cbo.mt940;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example for a single transaction
        MT940Message singleTransaction = new MT940Message();
        // Set transaction details
        MT940FileWriter.writeToFile(singleTransaction, "singleTransaction.mt940");

        // Example for multiple transactions
        List<MT940Message> multipleTransactions = new ArrayList<>();
        // Populate the list with transaction details
//        MT940FileWriter.writeToFile(multipleTransactions, "multipleTransactions.mt940");
    }
}
