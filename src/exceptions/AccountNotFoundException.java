package exceptions;

/**
 * Custom checked exception thrown when the requested account does not
 * exist in the bank's records.
 *
 * <p><b>Experiment 3:</b> Exception Handling — Thrown during lookups
 * by account number or holder name.</p>
 *
 * @author Student
 * @version 1.0
 */
public class AccountNotFoundException extends BankException {

    private final int accountNumber;

    /**
     * Constructs the exception with a custom message
     * (used for name-based searches).
     *
     * @param message the detail message
     */
    public AccountNotFoundException(String message) {
        super(message);
        this.accountNumber = -1;
    }

    /**
     * Constructs the exception with the account number that was not found.
     *
     * @param accountNumber the account number that could not be located
     */
    public AccountNotFoundException(int accountNumber) {
        super("Account not found with number: " + accountNumber);
        this.accountNumber = accountNumber;
    }

    /** @return the account number that was not found, or -1 if searched by name */
    public int getAccountNumber() {
        return accountNumber;
    }
}
