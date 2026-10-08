package web;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import exceptions.BankException;
import model.Account;
import service.Bank;

/**
 * Main web controller for the Bank System.
 *
 * <p>
 * <b>Experiment 4 - HttpServlet:</b> Overrides {@code doGet} for viewing
 * pages and {@code doPost} for processing form submissions.
 * </p>
 *
 * Mapped to URL: <code>/accounts</code>
 */
public class BankServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }

        Bank bank = BankProvider.getBank(getServletContext());
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                request.getRequestDispatcher("create_account.jsp").forward(request, response);
            } else if ("view".equals(action)) {
                int accNo = Integer.parseInt(request.getParameter("accNo"));
                Account acc = bank.searchAccount(accNo);
                request.setAttribute("account", acc);
                request.getRequestDispatcher("account_details.jsp").forward(request, response);
            } else {
                // Default action: dashboard
                List<Account> accounts = bank.getAllAccounts();
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login");
            return;
        }

        Bank bank = BankProvider.getBank(getServletContext());
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String type = request.getParameter("type");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                double deposit = Double.parseDouble(request.getParameter("deposit"));

                Account newAcc = null;
                if ("Savings".equals(type)) {
                    double rate = Double.parseDouble(request.getParameter("rate"));
                    newAcc = bank.createSavingsAccount(name, deposit, rate);
                } else if ("Current".equals(type)) {
                    double limit = Double.parseDouble(request.getParameter("limit"));
                    newAcc = bank.createCurrentAccount(name, deposit, limit);
                } else if ("FixedDeposit".equals(type)) {
                    double rate = Double.parseDouble(request.getParameter("rate"));
                    int tenure = Integer.parseInt(request.getParameter("tenure"));
                    newAcc = bank.createFixedDepositAccount(name, deposit, rate, tenure);
                }

                if (newAcc != null && email != null && !email.trim().isEmpty()) {
                    newAcc.setEmail(email.trim());
                }
                response.sendRedirect("accounts?msg=Account+Created");

            } else if ("transaction".equals(action)) {
                String txnType = request.getParameter("txnType");
                int accNo = Integer.parseInt(request.getParameter("accNo"));
                double amount = Double.parseDouble(request.getParameter("amount"));
                String remark = request.getParameter("remark");

                if ("deposit".equals(txnType)) {
                    bank.deposit(accNo, amount, remark);
                } else if ("withdraw".equals(txnType)) {
                    bank.withdraw(accNo, amount, remark);
                } else if ("transfer".equals(txnType)) {
                    int toAccNo = Integer.parseInt(request.getParameter("toAccNo"));
                    bank.transfer(accNo, toAccNo, amount, remark);
                }
                response.sendRedirect("accounts?action=view&accNo=" + accNo + "&msg=Transaction+Successful");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
