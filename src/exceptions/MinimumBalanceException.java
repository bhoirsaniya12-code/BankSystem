package exceptions;

/**
 * Custom checked exception thrown when a withdrawal would breach the
 * minimum balance requirement of a Savings account.
 *
 * <p><b>Experiment 3:</b> Exception Handling — Extends
 * {@link InsufficientFundsException} to form an <em>exception hierarchy</em>.
 * This allows a single {@code catch(InsufficientFundsException)} block to
 * handle both general insufficient-funds and minimum-balance violations,
 * while still permitting a more specific
 * {@code catch(MinimumBalanceException)} when needed.</p>
 *
 * @author Student
 * @version 1.0
 */
public class MinimumBalanceException extends InsufficientFundsException {

    private final double minimumBalance;
    private final double resultingBalance;

    /**
     * Constructs the exception with a custom message.
     *
     * @param message the detail message
     */
    public MinimumBalanceException(String message) {
        super(message);
        this.minimumBalance = 0;
        this.resultingBalance = 0;
    }

    /**
     * Constructs the exception with the minimum and resulting balance figures.
     *
     * @param minimumBalance   the minimum balance that must be maintained
     * @param resultingBalance the balance that would result after the withdrawal
     */
    public MinimumBalanceException(double minimumBalance, double resultingBalance) {
        super(String.format(
                "Withdrawal denied: resulting balance Rs.%.2f would be below " +
                "the minimum required balance of Rs.%.2f",
                resultingBalance, minimumBalance));
        this.minimumBalance = minimumBalance;
        this.resultingBalance = resultingBalance;
    }

    /** @return the minimum balance that must be maintained */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /** @return the balance that would have resulted from the withdrawal */
    public double getResultingBalance() {
        return resultingBalance;
    }
}
