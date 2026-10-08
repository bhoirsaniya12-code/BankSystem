package exceptions;

/**
 * Custom checked exception thrown when a withdrawal amount exceeds the
 * available account balance.
 *
 * <p><b>Experiment 3:</b> Exception Handling — Custom checked exception
 * using {@code throw} and {@code throws} keywords.</p>
 *
 * @author Student
 * @version 1.0
 */
public class InsufficientFundsException extends BankException {

    private final double amount;
    private final double balance;

    /**
     * Constructs the exception with a custom message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
        this.amount = 0;
        this.balance = 0;
    }

    /**
     * Constructs the exception with the attempted amount and current balance.
     *
     * @param amount  the amount that was attempted to withdraw
     * @param balance the current account balance
     */
    public InsufficientFundsException(double amount, double balance) {
        super(String.format(
                "Insufficient funds: tried to withdraw Rs.%.2f but balance is only Rs.%.2f",
                amount, balance));
        this.amount = amount;
        this.balance = balance;
    }

    /** @return the attempted withdrawal amount */
    public double getAmount() {
        return amount;
    }

    /** @return the balance at the time of the failed withdrawal */
    public double getBalance() {
        return balance;
    }
}
