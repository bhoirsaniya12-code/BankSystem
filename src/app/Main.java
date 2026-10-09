package app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import model.Account;

import service.Bank;

import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidAmountException;
import exceptions.MinimumBalanceException;

/**
 * Menu-driven entry point for the Bank Account Management System.
 *
 * <p><b>Experiment 1:</b> Looping ({@code do-while}) and Branching
 * ({@code switch-case}).</p>
 *
 * <p><b>Experiment 3:</b> Exception Handling — {@code try-catch-finally}
 * blocks, handling {@code InputMismatchException} for invalid menu input,
 * and catching custom exceptions.</p>
 *
 * @author Student
 * @version 1.0
 */
public class Main {

    // Bank instance and Scanner shared across methods
    private static final Bank bank = new Bank();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method — program entry point.
     * Contains the primary do-while menu loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int choice = -1;

        System.out.println("==================================================");
        System.out.println("       BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("       Built with Core Java (JDK 17+)");
        System.out.println("==================================================");

        // ═══════════════════════════════════════════════════════════
        //  [Experiment 1] DO-WHILE LOOP — keeps the menu running
        //  until the user chooses 0 (Exit).
        // ═══════════════════════════════════════════════════════════
        do {
            printMenu();

            // [Experiment 3] try-catch — handles InputMismatchException
            //                            for non-numeric menu input
            try {
                System.out.print("  Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // consume leftover newline

                // ═════════════════════════════════════════════════════
                //  [Experiment 1] SWITCH-CASE — branching on menu
                // ═════════════════════════════════════════════════════
                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> doDeposit();
                    case 3 -> doWithdraw();
                    case 4 -> doTransfer();
                    case 5 -> doSearch();
                    case 6 -> viewAccountDetails();
                    case 7 -> bank.viewAllAccounts();
                    case 8 -> viewTransactionHistory();
                    case 9 -> applyInterest();
                    case 0 -> System.out.println(
                            "\n  Thank you for using the Bank System. Goodbye!");
                    default -> System.out.println(
                            "  [ERROR] Invalid choice. Please enter 0-9.");
                }

            } catch (InputMismatchException e) {
                // [Experiment 3] Handling InputMismatchException —
                // prevents crash when user types non-numeric input
                System.out.println(
                        "  [ERROR] Please enter a valid number (0-9).");
                scanner.nextLine(); // clear the invalid input from scanner
                choice = -1;       // reset to keep loop running
            }

        } while (choice != 0);

        scanner.close();
    }

    // ═══════════════════════════════════════════════════════════════
    //  MENU DISPLAY
    // ═══════════════════════════════════════════════════════════════

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("                    MAIN MENU                     ");
        System.out.println("--------------------------------------------------");
        System.out.println("  1. Create Account (Savings / Current / FD)");
        System.out.println("  2. Deposit");
        System.out.println("  3. Withdraw");
        System.out.println("  4. Transfer");
        System.out.println("  5. Search Account (by number / by name)");
        System.out.println("  6. View Account Details");
        System.out.println("  7. View All Accounts");
        System.out.println("  8. View Transaction History");
        System.out.println("  9. Apply Interest to Savings Accounts");
        System.out.println("  0. Exit");
        System.out.println("--------------------------------------------------");
    }

    // ═══════════════════════════════════════════════════════════════
    //  1. CREATE ACCOUNT
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handles account creation with sub-menu for account type selection.
     * Uses try-catch-finally to demonstrate all three parts.
     */
    private static void createAccount() {
        System.out.println("\n  --- Create New Account ---");
        System.out.println("  1. Savings Account");
        System.out.println("  2. Current Account");
        System.out.println("  3. Fixed Deposit Account");

        // [Experiment 3] try-catch-finally block
        try {
            System.out.print("  Choose account type (1-3): ");
            int type = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("  Enter holder name: ");
            String name = scanner.nextLine().trim();

            // [Experiment 1] Branching — if-else validation
            if (name.isEmpty()) {
                System.out.println("  [ERROR] Name cannot be empty.");
                return;
            }

            // [Experiment 1] Branching — switch on account type
            switch (type) {
                case 1 -> {
                    System.out.print("  Enter initial deposit (min Rs.1000.00): ");
                    double deposit = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("  Enter interest rate (e.g. 4.0): ");
                    double rate = scanner.nextDouble();
                    scanner.nextLine();
                    bank.createSavingsAccount(name, deposit, rate);
                }
                case 2 -> {
                    System.out.print("  Enter initial deposit: ");
                    double deposit = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("  Enter overdraft limit: ");
                    double limit = scanner.nextDouble();
                    scanner.nextLine();
                    bank.createCurrentAccount(name, deposit, limit);
                }
                case 3 -> {
                    System.out.print("  Enter deposit amount: ");
                    double deposit = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("  Enter interest rate (e.g. 7.0): ");
                    double rate = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("  Enter tenure in months (e.g. 12): ");
                    int tenure = scanner.nextInt();
                    scanner.nextLine();
                    bank.createFixedDepositAccount(name, deposit, rate, tenure);
                }
                default -> System.out.println("  [ERROR] Invalid account type.");
            }

        } catch (InputMismatchException e) {
            System.out.println(
                    "  [ERROR] Invalid input. Please enter numbers where required.");
            scanner.nextLine(); // clear bad input

        } catch (InvalidAmountException e) {
            System.out.println("  [ERROR] " + e.getMessage());

        } finally {
            // [Experiment 3] finally block — always executes
            System.out.println("  [INFO] Create-account operation finished.");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  2. DEPOSIT
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handles depositing money into an account.
     * Demonstrates method overloading by choosing deposit(amount)
     * or deposit(amount, remark) based on user input.
     */
    private static void doDeposit() {
        try {
            System.out.print("\n  Enter account number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("  Enter deposit amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("  Enter remark (or press Enter to skip): ");
            String remark = scanner.nextLine().trim();

            // [Experiment 2] Method Overloading — choosing which
            //                version of deposit() to call
            if (remark.isEmpty()) {
                bank.deposit(accNo, amount);          // Overload 1
            } else {
                bank.deposit(accNo, amount, remark);  // Overload 2
            }

        } catch (InputMismatchException e) {
            System.out.println(
                    "  [ERROR] Invalid input. Please enter numbers where required.");
            scanner.nextLine();

        } catch (AccountNotFoundException | InvalidAmountException e) {
            // [Experiment 3] Multi-catch block
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  3. WITHDRAW
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handles withdrawing money from an account.
     * Catches MinimumBalanceException BEFORE InsufficientFundsException
     * to demonstrate hierarchical exception catching.
     */
    private static void doWithdraw() {
        try {
            System.out.print("\n  Enter account number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("  Enter withdrawal amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("  Enter remark (or press Enter to skip): ");
            String remark = scanner.nextLine().trim();

            if (remark.isEmpty()) {
                bank.withdraw(accNo, amount);
            } else {
                bank.withdraw(accNo, amount, remark);
            }

        } catch (InputMismatchException e) {
            System.out.println(
                    "  [ERROR] Invalid input. Please enter numbers where required.");
            scanner.nextLine();

        // [Experiment 3] Catching the SUBCLASS exception BEFORE its parent.
        // MinimumBalanceException extends InsufficientFundsException,
        // so it must be caught first to get the specific message.
        } catch (MinimumBalanceException e) {
            System.out.println("  [ERROR] Minimum Balance Violation!");
            System.out.println("  " + e.getMessage());

        } catch (AccountNotFoundException | InvalidAmountException |
                 InsufficientFundsException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  4. TRANSFER
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handles transferring money between two accounts.
     * Demonstrates transfer() method overloading.
     */
    private static void doTransfer() {
        try {
            System.out.print("\n  Enter source account number: ");
            int fromAcc = scanner.nextInt();
            scanner.nextLine();

            System.out.print("  Enter destination account number: ");
            int toAcc = scanner.nextInt();
            scanner.nextLine();

            System.out.print("  Enter transfer amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("  Enter remark (or press Enter to skip): ");
            String remark = scanner.nextLine().trim();

            // [Experiment 2] Method Overloading — transfer with/without remark
            if (remark.isEmpty()) {
                bank.transfer(fromAcc, toAcc, amount);
            } else {
                bank.transfer(fromAcc, toAcc, amount, remark);
            }

        } catch (InputMismatchException e) {
            System.out.println(
                    "  [ERROR] Invalid input. Please enter numbers where required.");
            scanner.nextLine();

        } catch (AccountNotFoundException | InvalidAmountException |
                 InsufficientFundsException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  5. SEARCH ACCOUNT
    // ═══════════════════════════════════════════════════════════════

    /**
     * Handles searching for accounts.
     * Demonstrates all three searchAccount() overloads.
     */
    private static void doSearch() {
        System.out.println("\n  --- Search Account ---");
        System.out.println("  1. Search by Account Number");
        System.out.println("  2. Search by Holder Name");
        System.out.println("  3. Search by Name and Account Type");

        try {
            System.out.print("  Choose search method (1-3): ");
            int method = scanner.nextInt();
            scanner.nextLine();

            switch (method) {
                case 1 -> {
                    // [Experiment 2] searchAccount(int) — Overload 1
                    System.out.print("  Enter account number: ");
                    int accNo = scanner.nextInt();
                    scanner.nextLine();
                    Account acc = bank.searchAccount(accNo);
                    System.out.println("  [OK] Account found:");
                    acc.displayDetails();
                }
                case 2 -> {
                    // [Experiment 2] searchAccount(String) — Overload 2
                    System.out.print("  Enter holder name: ");
                    String name = scanner.nextLine().trim();
                    List<Account> results = bank.searchAccount(name);
                    System.out.printf("  [OK] Found %d account(s):%n",
                            results.size());
                    for (Account a : results) {
                        a.displayDetails();
                    }
                }
                case 3 -> {
                    // [Experiment 2] searchAccount(String, String) — Overload 3
                    System.out.print("  Enter holder name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("  Enter account type " +
                            "(Savings/Current/Fixed Deposit): ");
                    String type = scanner.nextLine().trim();
                    List<Account> results = bank.searchAccount(name, type);
                    System.out.printf("  [OK] Found %d account(s):%n",
                            results.size());
                    for (Account a : results) {
                        a.displayDetails();
                    }
                }
                default -> System.out.println(
                        "  [ERROR] Invalid search method.");
            }

        } catch (InputMismatchException e) {
            System.out.println("  [ERROR] Invalid input. Please enter a number.");
            scanner.nextLine();

        } catch (AccountNotFoundException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  6. VIEW ACCOUNT DETAILS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Displays the details of a single account.
     */
    private static void viewAccountDetails() {
        try {
            System.out.print("\n  Enter account number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();

            Account acc = bank.searchAccount(accNo);
            acc.displayDetails();

        } catch (InputMismatchException e) {
            System.out.println("  [ERROR] Invalid input. Please enter a number.");
            scanner.nextLine();

        } catch (AccountNotFoundException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  8. VIEW TRANSACTION HISTORY
    // ═══════════════════════════════════════════════════════════════

    /**
     * Displays the transaction history of a specific account.
     */
    private static void viewTransactionHistory() {
        try {
            System.out.print("\n  Enter account number: ");
            int accNo = scanner.nextInt();
            scanner.nextLine();

            Account acc = bank.searchAccount(accNo);
            acc.displayTransactionHistory();

        } catch (InputMismatchException e) {
            System.out.println("  [ERROR] Invalid input. Please enter a number.");
            scanner.nextLine();

        } catch (AccountNotFoundException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════════
    //  9. APPLY INTEREST
    // ═══════════════════════════════════════════════════════════════

    /**
     * Applies interest to all savings accounts in the bank.
     */
    private static void applyInterest() {
        System.out.println("\n  --- Applying Interest to Savings Accounts ---");
        bank.applyInterestToAll();
    }
}
