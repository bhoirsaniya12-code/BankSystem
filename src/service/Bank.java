package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Account;
import model.SavingsAccount;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.Transaction;

import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;

/**
 * Manager class that holds all accounts and performs banking operations.
 *
 * <p><b>Experiment 1:</b> Classes and Objects — Uses {@code HashMap} to
 * store accounts, with looping (for-each) and branching (if-else).</p>
 *
 * <p><b>Experiment 2:</b> Method Overloading —
 * {@code searchAccount()} x3 and {@code transfer()} x2.</p>
 *
 * <p><b>Experiment 3:</b> Exception Handling — Uses {@code throws} to
 * propagate exceptions and {@code try-catch-finally} where needed.</p>
 *
 * @author Student
 * @version 1.0
 */
public class Bank {

    // ─── Private field: stores all accounts keyed by account number ──
    private final Map<Integer, Account> accounts;

    /**
     * Creates a new Bank instance with an empty account store.
     */
    public Bank() {
        this.accounts = new HashMap<>();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ACCOUNT CREATION
    // ═══════════════════════════════════════════════════════════════

    /**
     * Creates a new Savings account and stores it in the bank.
     *
     * @param name           holder name
     * @param initialDeposit opening balance
     * @param interestRate   annual interest rate
     * @return the created SavingsAccount
     * @throws InvalidAmountException if initial deposit is below minimum balance
     */
    public SavingsAccount createSavingsAccount(String name, double initialDeposit,
                                                double interestRate)
            throws InvalidAmountException {

        // [Experiment 1] Branching — if-else validation
        if (initialDeposit < SavingsAccount.getMinBalance()) {
            throw new InvalidAmountException(
                    String.format("Savings account requires minimum deposit of Rs.%.2f. " +
                            "You provided Rs.%.2f",
                            SavingsAccount.getMinBalance(), initialDeposit));
        }

        SavingsAccount acc = new SavingsAccount(name, initialDeposit, interestRate);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  [OK] Savings Account #%d created for %s.%n",
                acc.getAccountNumber(), name);
        return acc;
    }

    /**
     * Creates a new Current account and stores it in the bank.
     *
     * @param name           holder name
     * @param initialDeposit opening balance
     * @param overdraftLimit maximum overdraft allowed
     * @return the created CurrentAccount
     */
    public CurrentAccount createCurrentAccount(String name, double initialDeposit,
                                                double overdraftLimit) {
        CurrentAccount acc = new CurrentAccount(name, initialDeposit, overdraftLimit);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  [OK] Current Account #%d created for %s.%n",
                acc.getAccountNumber(), name);
        return acc;
    }

    /**
     * Creates a new Fixed Deposit account and stores it in the bank.
     *
     * @param name         holder name
     * @param deposit      the fixed deposit amount
     * @param interestRate annual interest rate
     * @param tenureMonths lock-in period in months
     * @return the created FixedDepositAccount
     * @throws InvalidAmountException if deposit is not positive
     */
    public FixedDepositAccount createFixedDepositAccount(String name, double deposit,
                                                          double interestRate,
                                                          int tenureMonths)
            throws InvalidAmountException {

        if (deposit <= 0) {
            throw new InvalidAmountException(deposit);
        }

        FixedDepositAccount acc = new FixedDepositAccount(name, deposit,
                interestRate, tenureMonths);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  [OK] Fixed Deposit Account #%d created for %s.%n",
                acc.getAccountNumber(), name);
        return acc;
    }

    // ═══════════════════════════════════════════════════════════════
    //  DEPOSIT & WITHDRAW  (delegates to the Account methods)
    // ═══════════════════════════════════════════════════════════════

    /**
     * Deposits money into the specified account (no remark).
     *
     * @param accountNumber the target account number
     * @param amount        the amount to deposit
     * @throws AccountNotFoundException if account does not exist
     * @throws InvalidAmountException   if amount is invalid
     */
    public void deposit(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException {
        Account acc = getAccountByNumber(accountNumber);
        acc.deposit(amount);
    }

    /**
     * Deposits money with a remark into the specified account.
     *
     * @param accountNumber the target account number
     * @param amount        the amount to deposit
     * @param remark        a note for the transaction
     * @throws AccountNotFoundException if account does not exist
     * @throws InvalidAmountException   if amount is invalid
     */
    public void deposit(int accountNumber, double amount, String remark)
            throws AccountNotFoundException, InvalidAmountException {
        Account acc = getAccountByNumber(accountNumber);
        acc.deposit(amount, remark);
    }

    /**
     * Withdraws money from the specified account (no remark).
     *
     * @param accountNumber the target account number
     * @param amount        the amount to withdraw
     * @throws AccountNotFoundException   if account does not exist
     * @throws InvalidAmountException     if amount is invalid
     * @throws InsufficientFundsException if insufficient funds
     */
    public void withdraw(int accountNumber, double amount)
            throws AccountNotFoundException, InvalidAmountException,
                   InsufficientFundsException {
        Account acc = getAccountByNumber(accountNumber);
        acc.withdraw(amount);
    }

    /**
     * Withdraws money with a remark from the specified account.
     *
     * @param accountNumber the target account number
     * @param amount        the amount to withdraw
     * @param remark        a note for the transaction
     * @throws AccountNotFoundException   if account does not exist
     * @throws InvalidAmountException     if amount is invalid
     * @throws InsufficientFundsException if insufficient funds
     */
    public void withdraw(int accountNumber, double amount, String remark)
            throws AccountNotFoundException, InvalidAmountException,
                   InsufficientFundsException {
        Account acc = getAccountByNumber(accountNumber);
        acc.withdraw(amount, remark);
    }

    // ═══════════════════════════════════════════════════════════════
    //  METHOD OVERLOADING — transfer()  [Experiment 2]
    //  Two versions: with and without a remark.
    // ═══════════════════════════════════════════════════════════════

    /**
     * <b>Transfer Overload 1:</b> Transfers money between two accounts
     * with a default remark.
     *
     * @param fromAccNo the source account number
     * @param toAccNo   the destination account number
     * @param amount    the amount to transfer
     * @throws AccountNotFoundException   if either account is not found
     * @throws InvalidAmountException     if amount is invalid
     * @throws InsufficientFundsException if source has insufficient funds
     */
    public void transfer(int fromAccNo, int toAccNo, double amount)
            throws AccountNotFoundException, InvalidAmountException,
                   InsufficientFundsException {
        // Calls the overloaded version with a default remark
        transfer(fromAccNo, toAccNo, amount, "Fund transfer");
    }

    /**
     * <b>Transfer Overload 2:</b> Transfers money with a custom remark.
     *
     * @param fromAccNo the source account number
     * @param toAccNo   the destination account number
     * @param amount    the amount to transfer
     * @param remark    a note for the transaction
     * @throws AccountNotFoundException   if either account is not found
     * @throws InvalidAmountException     if amount is invalid
     * @throws InsufficientFundsException if source has insufficient funds
     */
    public void transfer(int fromAccNo, int toAccNo, double amount, String remark)
            throws AccountNotFoundException, InvalidAmountException,
                   InsufficientFundsException {

        // [Experiment 1] Branching — validation
        if (fromAccNo == toAccNo) {
            throw new InvalidAmountException("Cannot transfer to the same account.");
        }

        Account from = getAccountByNumber(fromAccNo);
        Account to   = getAccountByNumber(toAccNo);

        // Step 1: Withdraw from source account
        from.withdraw(amount, "Transfer OUT to #" + toAccNo + " - " + remark);

        // [Experiment 3] try-catch-finally block
        try {
            // Step 2: Deposit to destination account
            to.deposit(amount, "Transfer IN from #" + fromAccNo + " - " + remark);
            System.out.printf("  [OK] Transferred Rs.%.2f from Account #%d to " +
                    "Account #%d.%n", amount, fromAccNo, toAccNo);

        } catch (InvalidAmountException e) {
            // If the deposit fails for any reason, rollback the withdrawal
            from.deposit(amount, "Rollback - failed transfer to #" + toAccNo);
            throw e;   // re-throw so the caller knows the transfer failed

        } finally {
            // [Experiment 3] finally block — always executes regardless
            // of whether the deposit succeeded or threw an exception
            System.out.println("  [INFO] Transfer operation finished.");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  METHOD OVERLOADING — searchAccount()  [Experiment 2]
    //  Three versions: by number, by name, by name + type.
    // ═══════════════════════════════════════════════════════════════

    /**
     * <b>Search Overload 1:</b> Searches for an account by account number.
     *
     * @param accountNumber the account number to search for
     * @return the matching Account
     * @throws AccountNotFoundException if no account matches
     */
    public Account searchAccount(int accountNumber)
            throws AccountNotFoundException {
        return getAccountByNumber(accountNumber);
    }

    /**
     * <b>Search Overload 2:</b> Searches for accounts by holder name.
     * Returns all accounts matching the name (case-insensitive).
     *
     * @param holderName the name to search for
     * @return a list of matching accounts
     * @throws AccountNotFoundException if no accounts match
     */
    public List<Account> searchAccount(String holderName)
            throws AccountNotFoundException {

        List<Account> results = new ArrayList<>();

        // [Experiment 1] Looping — for-each loop to search all accounts
        for (Account acc : accounts.values()) {
            // [Experiment 1] Branching — if condition with string comparison
            if (acc.getHolderName().equalsIgnoreCase(holderName)) {
                results.add(acc);
            }
        }

        if (results.isEmpty()) {
            throw new AccountNotFoundException(
                    "No accounts found for holder: " + holderName);
        }

        return results;
    }

    /**
     * <b>Search Overload 3:</b> Searches for accounts by holder name
     * AND account type.
     *
     * @param holderName  the name to search for
     * @param accountType the account type (Savings, Current, Fixed Deposit)
     * @return a list of matching accounts
     * @throws AccountNotFoundException if no accounts match
     */
    public List<Account> searchAccount(String holderName, String accountType)
            throws AccountNotFoundException {

        List<Account> results = new ArrayList<>();

        // [Experiment 1] Looping + Branching combined
        for (Account acc : accounts.values()) {
            if (acc.getHolderName().equalsIgnoreCase(holderName)
                    && acc.getAccountType().equalsIgnoreCase(accountType)) {
                results.add(acc);
            }
        }

        if (results.isEmpty()) {
            throw new AccountNotFoundException(
                    "No " + accountType + " accounts found for holder: " + holderName);
        }

        return results;
    }

    // ═══════════════════════════════════════════════════════════════
    //  VIEW ALL ACCOUNTS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Displays details of all accounts in the bank.
     *
     * <p><b>Experiment 1:</b> Looping — for-each over all accounts.<br>
     * Branching — empty-check.</p>
     */
    public void viewAllAccounts() {
        // [Experiment 1] Branching — empty check
        if (accounts.isEmpty()) {
            System.out.println("  No accounts in the system.");
            return;
        }

        System.out.println("\n  ========== All Bank Accounts ==========");
        System.out.printf("  Total accounts: %d%n", accounts.size());

        // [Experiment 1] Looping — for-each
        for (Account acc : accounts.values()) {
            acc.displayDetails();
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  APPLY INTEREST TO ALL SAVINGS ACCOUNTS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Applies interest to all Savings accounts (excludes Fixed Deposits).
     *
     * <p><b>Experiment 1:</b> Looping (for-each) + Branching
     * ({@code instanceof} check).</p>
     */
    public void applyInterestToAll() {
        boolean found = false;

        for (Account acc : accounts.values()) {
            // Apply interest only to SavingsAccount, NOT FixedDepositAccount
            // (instanceof check: FixedDepositAccount IS-A SavingsAccount,
            //  so we explicitly exclude it)
            if (acc instanceof SavingsAccount
                    && !(acc instanceof FixedDepositAccount)) {
                ((SavingsAccount) acc).addInterest();
                found = true;
            }
        }

        if (!found) {
            System.out.println("  No savings accounts found to apply interest.");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  HELPER METHOD
    // ═══════════════════════════════════════════════════════════════

    /**
     * Internal helper to retrieve an account by its number.
     *
     * @param accountNumber the account number to look up
     * @return the Account object
     * @throws AccountNotFoundException if the account does not exist
     */
    private Account getAccountByNumber(int accountNumber)
            throws AccountNotFoundException {

        Account acc = accounts.get(accountNumber);

        // [Experiment 1] Branching — null check
        if (acc == null) {
            throw new AccountNotFoundException(accountNumber);
        }

        return acc;
    }

    /**
     * Returns the total number of accounts in the bank.
     *
     * @return account count
     */
    public int getAccountCount() {
        return accounts.size();
    }

    /**
     * Returns all accounts sorted by account number.
     * Used by the web layer (JSP pages) which needs data, not console output.
     *
     * @return a new list containing every account
     */
    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>(accounts.values());
        list.sort((a, b) -> Integer.compare(a.getAccountNumber(), b.getAccountNumber()));
        return list;
    }

    /**
     * Returns the sum of all account balances.
     *
     * @return total deposits held by the bank
     */
    public double getTotalBalance() {
        double total = 0;
        for (Account acc : accounts.values()) {   // [Experiment 1] Looping
            total += acc.getBalance();
        }
        return total;
    }
}
