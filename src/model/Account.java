package model;

import java.util.ArrayList;
import java.util.List;

import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

/**
 * Base class for all bank accounts.
 *
 * <p><b>Experiment 1:</b> Classes and Objects — Demonstrates a class with
 * private fields, getters/setters, and meaningful methods.</p>
 *
 * <p><b>Experiment 2:</b> Constructor Overloading — Three overloaded
 * constructors with different parameter lists.<br>
 * Method Overloading — {@code deposit()} and {@code withdraw()} each
 * have two versions (with and without a remark).</p>
 *
 * <p><b>Experiment 3:</b> Inheritance — This is the <em>base class</em>
 * for single, multilevel, and hierarchical inheritance.</p>
 *
 * @author Student
 * @version 1.0
 */
public class Account {

    // ─── Static counter for auto-generating unique account numbers ──
    private static int nextAccountNumber = 1001;

    // ─── Private fields (Encapsulation) ─────────────────────────────
    private int accountNumber;
    private String holderName;
    private double balance;
    private String accountType;
    private List<Transaction> transactions;   // transaction history
    private String email;                     // optional - set by the web UI [Experiment 8]

    // ═══════════════════════════════════════════════════════════════
    //  CONSTRUCTOR OVERLOADING  [Experiment 2]
    //  Three constructors with different parameter lists.
    // ═══════════════════════════════════════════════════════════════

    /**
     * <b>Constructor Overload 1:</b> Creates an account with only the
     * holder's name.  Balance defaults to 0.00 and type to "General".
     *
     * @param holderName the name of the account holder
     */
    public Account(String holderName) {
        this.accountNumber  = nextAccountNumber++;
        this.holderName     = holderName;
        this.balance        = 0.0;
        this.accountType    = "General";
        this.transactions   = new ArrayList<>();
        // No opening transaction since balance is zero
    }

    /**
     * <b>Constructor Overload 2:</b> Creates an account with a name and
     * an initial deposit.  Type defaults to "General".
     *
     * @param holderName     the name of the account holder
     * @param initialDeposit the opening deposit amount
     */
    public Account(String holderName, double initialDeposit) {
        this.accountNumber  = nextAccountNumber++;
        this.holderName     = holderName;
        this.balance        = initialDeposit;
        this.accountType    = "General";
        this.transactions   = new ArrayList<>();

        if (initialDeposit > 0) {
            transactions.add(new Transaction(
                    "Opening", initialDeposit, "Initial deposit", this.balance));
        }
    }

    /**
     * <b>Constructor Overload 3:</b> Creates an account with name,
     * initial deposit, and account type.
     *
     * @param holderName     the name of the account holder
     * @param initialDeposit the opening deposit amount
     * @param accountType    the type of account (Savings, Current, etc.)
     */
    public Account(String holderName, double initialDeposit, String accountType) {
        this.accountNumber  = nextAccountNumber++;
        this.holderName     = holderName;
        this.balance        = initialDeposit;
        this.accountType    = accountType;
        this.transactions   = new ArrayList<>();

        if (initialDeposit > 0) {
            transactions.add(new Transaction(
                    "Opening", initialDeposit, "Initial deposit", this.balance));
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  METHOD OVERLOADING — deposit()  [Experiment 2]
    //  Two versions: with and without a remark.
    // ═══════════════════════════════════════════════════════════════

    /**
     * <b>Deposit Overload 1:</b> Deposits an amount with a default remark.
     *
     * @param amount the amount to deposit
     * @throws InvalidAmountException if amount is zero or negative
     */
    public void deposit(double amount) throws InvalidAmountException {
        // Calls the overloaded version with a default remark
        deposit(amount, "Cash deposit");
    }

    /**
     * <b>Deposit Overload 2:</b> Deposits an amount with a custom remark.
     *
     * @param amount the amount to deposit
     * @param remark a note describing the deposit
     * @throws InvalidAmountException if amount is zero or negative
     */
    public void deposit(double amount, String remark) throws InvalidAmountException {
        // [Experiment 3] throw keyword — throwing a custom checked exception
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }

        this.balance += amount;
        transactions.add(new Transaction("Deposit", amount, remark, this.balance));
        System.out.printf("  [OK] Deposited Rs.%.2f successfully. New balance: Rs.%.2f%n",
                amount, this.balance);
    }

    // ═══════════════════════════════════════════════════════════════
    //  METHOD OVERLOADING — withdraw()  [Experiment 2]
    //  Two versions: with and without a remark.
    // ═══════════════════════════════════════════════════════════════

    /**
     * <b>Withdraw Overload 1:</b> Withdraws an amount with a default remark.
     *
     * @param amount the amount to withdraw
     * @throws InvalidAmountException      if amount is zero or negative
     * @throws InsufficientFundsException  if balance is too low
     */
    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientFundsException {
        // Calls the overloaded version with a default remark
        withdraw(amount, "Cash withdrawal");
    }

    /**
     * <b>Withdraw Overload 2:</b> Withdraws an amount with a custom remark.
     * Subclasses override this method to add minimum-balance or overdraft logic.
     *
     * @param amount the amount to withdraw
     * @param remark a note describing the withdrawal
     * @throws InvalidAmountException      if amount is zero or negative
     * @throws InsufficientFundsException  if balance is too low
     */
    public void withdraw(double amount, String remark)
            throws InvalidAmountException, InsufficientFundsException {
        // [Experiment 3] throw keyword — throwing custom checked exceptions
        if (amount <= 0) {
            throw new InvalidAmountException(amount);
        }
        if (amount > this.balance) {
            throw new InsufficientFundsException(amount, this.balance);
        }

        this.balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount, remark, this.balance));
        System.out.printf("  [OK] Withdrew Rs.%.2f successfully. New balance: Rs.%.2f%n",
                amount, this.balance);
    }

    // ═══════════════════════════════════════════════════════════════
    //  DISPLAY METHODS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Displays the basic details of this account.
     * Subclasses override this to show additional fields.
     */
    public void displayDetails() {
        System.out.println("\n  ---------- Account Details ----------");
        System.out.printf("  Account No    : %d%n", accountNumber);
        System.out.printf("  Holder Name   : %s%n", holderName);
        System.out.printf("  Account Type  : %s%n", accountType);
        System.out.printf("  Balance       : Rs.%.2f%n", balance);
        System.out.println("  -------------------------------------");
    }

    /**
     * Displays the full transaction history for this account.
     *
     * <p><b>Experiment 1:</b> Looping — for-each loop to iterate
     * over the transaction list.</p>
     */
    public void displayTransactionHistory() {
        if (transactions.isEmpty()) {
            System.out.println("  No transactions recorded yet.");
            return;
        }
        System.out.println("\n  === Transaction History for Account #" + accountNumber + " ===");
        System.out.printf("  %-14s | %13s | %13s | %-20s | %s%n",
                "Type", "Amount", "Balance", "Remark", "Date/Time");
        System.out.println("  " + "-".repeat(90));

        // [Experiment 1] Looping — for-each loop over transactions
        for (Transaction t : transactions) {
            System.out.println(t);
        }
        System.out.println("  " + "-".repeat(90));
    }

    // ─── Getters and Setters (Encapsulation) ────────────────────
    /** @return the unique account number */
    public int getAccountNumber()            { return accountNumber; }

    /** @return the account holder's name */
    public String getHolderName()            { return holderName; }

    /** @return the current balance */
    public double getBalance()               { return balance; }

    /** @return the account type (Savings, Current, etc.) */
    public String getAccountType()           { return accountType; }

    /** @return the list of transactions */
    public List<Transaction> getTransactions() { return transactions; }

    /** @param holderName new holder name */
    public void setHolderName(String holderName)   { this.holderName = holderName; }

    /**
     * Sets the balance — protected so only subclasses can call it directly.
     * @param balance new balance
     */
    protected void setBalance(double balance)      { this.balance = balance; }

    /** @param accountType new account type label */
    public void setAccountType(String accountType) { this.accountType = accountType; }

    /** @return the holder's email (may be null for console-created accounts) */
    public String getEmail()                       { return email; }

    /** @param email holder's email, validated in the browser [Experiment 8] and the servlet */
    public void setEmail(String email)             { this.email = email; }
}
