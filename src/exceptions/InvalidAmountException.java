package exceptions;

/**
 * Custom checked exception thrown when a zero or negative monetary
 * amount is supplied to a deposit or withdrawal operation.
 *
 * <p><b>Experiment 3:</b> Exception Handling — Validates that all
 * monetary amounts are strictly positive.</p>
 *
 * @author Student
 * @version 1.0
 */
public class InvalidAmountException extends BankException {

    private final double amount;

    /**
     * Constructs the exception with a custom message.
     *
     * @param message the detail message
     */
    public InvalidAmountException(String message) {
        super(message);
        this.amount = 0;
    }

    /**
     * Constructs the exception with the invalid amount.
     *
     * @param amount the invalid (zero or negative) amount
     */
    public InvalidAmountException(double amount) {
        super(String.format(
                "Invalid amount: Rs.%.2f - amount must be greater than zero.", amount));
        this.amount = amount;
    }

    /** @return the invalid amount that caused the exception */
    public double getAmount() {
        return amount;
    }
}
