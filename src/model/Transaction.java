package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single transaction in an account's history.
 * Each transaction records the type, amount, remark, resulting balance,
 * and timestamp.
 *
 * <p><b>Experiment 1:</b> Classes and Objects — Encapsulates transaction
 * data with private fields and public getters.</p>
 *
 * @author Student
 * @version 1.0
 */
public class Transaction {

    // ─── Private fields (Encapsulation) ─────────────────────────
    private final String type;           // e.g. "Deposit", "Withdrawal", "Transfer"
    private final double amount;
    private final String remark;
    private final double balanceAfter;
    private final LocalDateTime timestamp;

    /**
     * Creates a new Transaction record.
     *
     * @param type         transaction type (Deposit, Withdrawal, etc.)
     * @param amount       the monetary amount involved
     * @param remark       a short note describing the transaction
     * @param balanceAfter the account balance after this transaction
     */
    public Transaction(String type, double amount, String remark, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.remark = remark;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
    }

    // ─── Getters ────────────────────────────────────────────────
    /** @return the transaction type */
    public String getType()             { return type; }

    /** @return the amount involved */
    public double getAmount()           { return amount; }

    /** @return the transaction remark */
    public String getRemark()           { return remark; }

    /** @return the account balance after this transaction */
    public double getBalanceAfter()     { return balanceAfter; }

    /** @return the timestamp when the transaction was recorded */
    public LocalDateTime getTimestamp()  { return timestamp; }

    /**
     * Returns a formatted, table-friendly string representation.
     *
     * @return formatted transaction string
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return String.format("  %-14s | Rs.%10.2f | Rs.%10.2f | %-20s | %s",
                type, amount, balanceAfter,
                (remark != null ? remark : "-"),
                timestamp.format(fmt));
    }
}
