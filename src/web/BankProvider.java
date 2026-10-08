package web;

import jakarta.servlet.ServletContext;

import service.Bank;

/**
 * Gives every servlet the same {@link Bank} object.
 *
 * <p><b>Experiment 4:</b> The Bank lives in <em>application scope</em>
 * (the {@link ServletContext}). That means all requests and all logged-in
 * users share one bank, just like the console app shares one Bank in Main.</p>
 *
 * <p>Data is kept in memory, the same as the console version, so it resets
 * when Tomcat restarts.</p>
 */
public final class BankProvider {

    /** Name of the ServletContext attribute that holds the Bank. */
    public static final String ATTR = "bank";

    private BankProvider() { }

    /**
     * Returns the shared Bank, creating it the first time it is needed.
     * {@code synchronized} so two first requests cannot create two banks.
     *
     * @param ctx the web application's context
     * @return the shared Bank instance
     */
    public static synchronized Bank getBank(ServletContext ctx) {
        Bank bank = (Bank) ctx.getAttribute(ATTR);
        if (bank == null) {
            bank = new Bank();
            ctx.setAttribute(ATTR, bank);
        }
        return bank;
    }
}
