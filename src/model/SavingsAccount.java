package model;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.MinimumBalanceException;

/**
 * Savings account with an interest rate and a minimum balance rule.
 *
 * <p><b>Experiment 3: SINGLE INHERITANCE</b> —
 * {@code SavingsAccount extends Account}.</p>
 *
 * <p><b>Experiment 3: HIERARCHICAL INHERITANCE</b> —
 * Both {@code SavingsAccount} and {@code CurrentAccount} extend the
 * same parent class {@code Account}.</p>
 *
 * @author Student
 * @version 1.0
 */
public class SavingsAccount extends Account {
    // ──────────────────────────────────────────────────────────────
    //  SINGLE INHERITANCE:  SavingsAccount  →  Account
    //  HIERARCHICAL:        SavingsAccount ─┐
    //                                        ├─ Account
    //                       CurrentAccount  ─┘
    // ──────────────────────────────────────────────────────────────

    // ─── Private fields ─────────────────────────────────────────
    private double interestRate;                        // annual rate in %
    private static final double MIN_BALANCE = 1000.00;  // minimum balance rule

    /**
     * Creates a new Savings account.
     *
     * @param holderName     the name of the account holder
     * @param initialDeposit the opening deposit (must be >= MIN_BALANCE)
     * @param interestRate   the annual interest rate (e.g. 4.0 for 4%)
     */
    public SavingsAccount(String holderName, double initialDeposit,
                           double interestRate) {
        // Calls Account(String, double, String) — Constructor Overload 3
        super(holderName, initialDeposit, "Savings");
        this.interestRate = interestRate;
    }

    /**
     * Calculates and adds annual interest to the account balance.
     * Records the interest as a transaction in the history.
     */
    public void addInterest() {
        double interest = getBalance() * (interestRate / 100.0);
        setBalance(getBalance() + interest);
        getTransactions().add(new Transaction("Interest", interest,
                String.format("%.1f%% annual interest", interestRate),
                getBalance()));
        System.out.printf("  [OK] Interest of Rs.%.2f added to Account #%d. " +
                "New balance: Rs.%.2f%n",
                interest, getAccountNumber(), getBalance());
    }

    /**
     * Overridden withdrawal that enforces the minimum balance rule.
     * If the withdrawal would drop the balance below Rs.1000.00,
     * a {@link MinimumBalanceException} is thrown.
     *
     * <p><b>Experiment 3:</b> Exception Handling — demonstrates
     * {@code throw}, {@code throws}, and a custom exception hierarchy
     * ({@code MinimumBalanceException extends InsufficientFundsException}).</p>
     *
     * @param amount the amount to withdraw
     * @param remark a note for the transaction
     * @throws InvalidAmountException      if amount is zero or negative
     * @throws InsufficientFundsException  if balance is too low or minimum
     *                                      balance would be breached
     */
    @Override
    public void withdraw(double amount, String remark)
            throws InvalidAmountException, InsufficientFundsException {

        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount > getBalance()) {
            throw new InsufficientFundsException(amount, getBalance());
        }

        // [Experiment 3] Minimum balance check
        // MinimumBalanceException extends InsufficientFundsException,
        // so it satisfies the 'throws InsufficientFundsException' clause.
        if ((getBalance() - amount) < MIN_BALANCE) {
            throw new MinimumBalanceException(MIN_BALANCE, getBalance() - amount);
        }

        setBalance(getBalance() - amount);
        getTransactions().add(new Transaction("Withdrawal", amount, remark,
                getBalance()));
        System.out.printf("  [OK] Withdrew Rs.%.2f successfully. " +
                "New balance: Rs.%.2f%n", amount, getBalance());
    }

    /**
     * Displays savings-specific details in addition to common account info.
     */
    @Override
    public void displayDetails() {
        System.out.println("\n  --------- Savings Account -----------");
        System.out.printf("  Account No    : %d%n", getAccountNumber());
        System.out.printf("  Holder Name   : %s%n", getHolderName());
        System.out.printf("  Account Type  : %s%n", getAccountType());
        System.out.printf("  Balance       : Rs.%.2f%n", getBalance());
        System.out.printf("  Interest Rate : %.1f%%%n", interestRate);
        System.out.printf("  Min Balance   : Rs.%.2f%n", MIN_BALANCE);
        System.out.println("  -------------------------------------");
    }

    // ─── Getters and Setters ────────────────────────────────────
    /** @return the annual interest rate */
    public double getInterestRate()                  { return interestRate; }

    /** @param interestRate new interest rate */
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    /** @return the minimum balance required */
    public static double getMinBalance()             { return MIN_BALANCE; }
}
