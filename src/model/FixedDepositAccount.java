package model;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

/**
 * Fixed Deposit account with a tenure period and maturity calculation.
 * Withdrawals and additional deposits are <b>not allowed</b> before maturity.
 *
 * <p><b>Experiment 3: MULTILEVEL INHERITANCE</b> —
 * {@code FixedDepositAccount → SavingsAccount → Account}.<br>
 * This class is <em>two levels</em> below the base class, demonstrating
 * multilevel inheritance.</p>
 *
 * @author Student
 * @version 1.0
 */
public class FixedDepositAccount extends SavingsAccount {
    // ──────────────────────────────────────────────────────────────
    //  MULTILEVEL INHERITANCE:
    //     FixedDepositAccount  →  SavingsAccount  →  Account
    //
    //  The constructor chain flows through all three levels:
    //    FixedDepositAccount() → super() → SavingsAccount() →
    //    super() → Account()
    // ──────────────────────────────────────────────────────────────

    // ─── Private fields ─────────────────────────────────────────
    private int tenureMonths;         // lock-in period in months
    private double maturityAmount;    // principal + interest at maturity

    /**
     * Creates a new Fixed Deposit account.
     *
     * @param holderName    the name of the account holder
     * @param depositAmount the fixed deposit principal
     * @param interestRate  the annual interest rate (e.g. 7.0 for 7%)
     * @param tenureMonths  the tenure in months (e.g. 12)
     */
    public FixedDepositAccount(String holderName, double depositAmount,
                                double interestRate, int tenureMonths) {
        // Calls SavingsAccount(String, double, double)
        //   which calls Account(String, double, String)
        //   — demonstrating multilevel constructor chaining
        super(holderName, depositAmount, interestRate);
        setAccountType("Fixed Deposit");   // override the "Savings" type
        this.tenureMonths   = tenureMonths;
        this.maturityAmount = calculateMaturity();
    }

    /**
     * Calculates the maturity amount using simple interest.
     * <br>Formula: {@code Principal + (Principal * Rate * Tenure / 12 / 100)}
     *
     * @return the maturity amount
     */
    public double calculateMaturity() {
        double principal = getBalance();
        double rate      = getInterestRate();
        double interest  = principal * rate * tenureMonths / 12.0 / 100.0;
        return principal + interest;
    }

    /**
     * Withdrawal is <b>not allowed</b> on Fixed Deposit accounts
     * before maturity.  Always throws an exception.
     *
     * <p><b>Experiment 3:</b> Demonstrates restricting a superclass
     * operation by always throwing an exception.</p>
     *
     * @param amount (ignored) the amount attempted
     * @param remark (ignored) the remark
     * @throws InsufficientFundsException always — FD cannot be withdrawn early
     */
    @Override
    public void withdraw(double amount, String remark)
            throws InvalidAmountException, InsufficientFundsException {
        throw new InsufficientFundsException(
                "Withdrawal not allowed on Fixed Deposit accounts before maturity. " +
                "Tenure: " + tenureMonths + " months.");
    }

    /**
     * Additional deposits are <b>not allowed</b> on Fixed Deposit accounts.
     *
     * @param amount (ignored)
     * @param remark (ignored)
     * @throws InvalidAmountException always — FD does not accept additional deposits
     */
    @Override
    public void deposit(double amount, String remark)
            throws InvalidAmountException {
        throw new InvalidAmountException(
                "Additional deposits are not allowed on Fixed Deposit accounts.");
    }

    /**
     * Displays fixed-deposit-specific details including tenure and maturity.
     */
    @Override
    public void displayDetails() {
        System.out.println("\n  ------- Fixed Deposit Account --------");
        System.out.printf("  Account No    : %d%n", getAccountNumber());
        System.out.printf("  Holder Name   : %s%n", getHolderName());
        System.out.printf("  Account Type  : %s%n", getAccountType());
        System.out.printf("  Principal     : Rs.%.2f%n", getBalance());
        System.out.printf("  Interest Rate : %.1f%%%n", getInterestRate());
        System.out.printf("  Tenure        : %d months%n", tenureMonths);
        System.out.printf("  Maturity Amt  : Rs.%.2f%n", maturityAmount);
        System.out.println("  -------------------------------------");
    }

    // ─── Getters ────────────────────────────────────────────────
    /** @return the tenure in months */
    public int getTenureMonths()       { return tenureMonths; }

    /** @return the calculated maturity amount */
    public double getMaturityAmount()  { return maturityAmount; }
}
