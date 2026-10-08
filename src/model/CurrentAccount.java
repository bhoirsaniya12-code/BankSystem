package model;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

/**
 * Current account with an overdraft facility.
 *
 * <p><b>Experiment 3: SINGLE INHERITANCE</b> —
 * {@code CurrentAccount extends Account}.</p>
 *
 * <p><b>Experiment 3: HIERARCHICAL INHERITANCE</b> —
 * Both {@code SavingsAccount} and {@code CurrentAccount} share the
 * same parent class {@code Account}.</p>
 *
 * @author Student
 * @version 1.0
 */
public class CurrentAccount extends Account {
    // ──────────────────────────────────────────────────────────────
    //  SINGLE INHERITANCE:  CurrentAccount  →  Account
    //  HIERARCHICAL:        SavingsAccount ─┐
    //                                        ├─ Account
    //                       CurrentAccount  ─┘
    // ──────────────────────────────────────────────────────────────

    // ─── Private fields ─────────────────────────────────────────
    private double overdraftLimit;   // maximum overdraft allowed

    /**
     * Creates a new Current account with an overdraft facility.
     *
     * @param holderName     the name of the account holder
     * @param initialDeposit the opening deposit
     * @param overdraftLimit the maximum overdraft allowed (e.g. 5000.00)
     */
    public CurrentAccount(String holderName, double initialDeposit,
                           double overdraftLimit) {
        // Calls Account(String, double, String) — Constructor Overload 3
        super(holderName, initialDeposit, "Current");
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Overdraft-aware withdrawal.  Allows the balance to go negative
     * up to the overdraft limit.
     *
     * <p><b>Experiment 3:</b> Exception Handling — uses {@code throw}
     * and {@code throws} with custom exceptions.</p>
     *
     * @param amount the amount to withdraw
     * @param remark a note for the transaction
     * @throws InvalidAmountException      if amount is zero or negative
     * @throws InsufficientFundsException  if overdraft limit would be exceeded
     */
    @Override
    public void withdraw(double amount, String remark)
            throws InvalidAmountException, InsufficientFundsException {

        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        // Allow withdrawal up to (balance + overdraftLimit)
        if (amount > (getBalance() + overdraftLimit)) {
            throw new InsufficientFundsException(
                    String.format("Withdrawal of Rs.%.2f exceeds available funds " +
                            "(Balance: Rs.%.2f + Overdraft: Rs.%.2f = Rs.%.2f)",
                            amount, getBalance(), overdraftLimit,
                            getBalance() + overdraftLimit));
        }

        setBalance(getBalance() - amount);
        getTransactions().add(new Transaction("Withdrawal", amount, remark,
                getBalance()));
        System.out.printf("  [OK] Withdrew Rs.%.2f successfully. " +
                "New balance: Rs.%.2f%n", amount, getBalance());

        // Warn user if account is now in overdraft (negative balance)
        if (getBalance() < 0) {
            System.out.printf("  [WARNING] Account is now in overdraft! " +
                    "Balance: Rs.%.2f%n", getBalance());
        }
    }

    /**
     * Displays current-account-specific details including overdraft info.
     */
    @Override
    public void displayDetails() {
        System.out.println("\n  --------- Current Account -----------");
        System.out.printf("  Account No    : %d%n", getAccountNumber());
        System.out.printf("  Holder Name   : %s%n", getHolderName());
        System.out.printf("  Account Type  : %s%n", getAccountType());
        System.out.printf("  Balance       : Rs.%.2f%n", getBalance());
        System.out.printf("  Overdraft Lmt : Rs.%.2f%n", overdraftLimit);
        System.out.printf("  Available     : Rs.%.2f%n",
                getBalance() + overdraftLimit);
        System.out.println("  -------------------------------------");
    }

    // ─── Getters and Setters ────────────────────────────────────
    /** @return the overdraft limit */
    public double getOverdraftLimit()                    { return overdraftLimit; }

    /** @param overdraftLimit new overdraft limit */
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}
