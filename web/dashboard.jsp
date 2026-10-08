<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Account" %>
<%@ page import="model.SavingsAccount" %>
<%@ page import="model.CurrentAccount" %>
<%@ page import="model.FixedDepositAccount" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard | NovaBank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="bg-orbs"><span></span><span></span><span></span></div>

    <main class="dashboard-page">
        <header class="top-bar glass" style="padding: 1rem 2rem; border-radius: 15px; margin-bottom: 2rem;">
            <div>
                <h2 style="margin:0">NovaBank <span class="muted small">Staff Portal</span></h2>
                <p class="small text-muted" style="margin:0">Welcome, <%= session.getAttribute("fullName") %></p>
            </div>
            
            <div style="display:flex; align-items:center; gap: 2rem;">
                <!-- Experiment 10: Stopwatch Timer -->
                <div style="text-align:right">
                    <span class="small muted" style="display:block">Session Time Left</span>
                    <span id="session-timer" class="session-timer">--:--</span>
                </div>
                <a href="login?action=logout" class="btn btn-outline btn-sm">Logout</a>
            </div>
        </header>

        <% if (request.getParameter("msg") != null) { %>
            <div class="success-msg">
                <%= request.getParameter("msg").replace("+", " ") %>
            </div>
        <% } %>

        <div class="top-bar">
            <h3>All Accounts</h3>
            <a href="accounts?action=create" class="btn btn-primary btn-sm" style="width:auto">+ Open Account</a>
        </div>

        <section class="glass card" style="padding: 1.5rem">
            <% 
                List<Account> accounts = (List<Account>) request.getAttribute("accounts");
                if (accounts == null || accounts.isEmpty()) {
            %>
                <p class="muted text-center" style="padding: 2rem">No accounts found in the system.</p>
            <% } else { %>
                <div style="overflow-x:auto">
                    <table>
                        <thead>
                            <tr>
                                <th>Acc #</th>
                                <th>Holder Name</th>
                                <th>Email</th>
                                <th>Type</th>
                                <th>Balance (Rs)</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Account acc : accounts) { %>
                                <tr>
                                    <td class="mono"><%= acc.getAccountNumber() %></td>
                                    <td><%= acc.getHolderName() %></td>
                                    <td class="muted"><%= acc.getEmail() != null ? acc.getEmail() : "-" %></td>
                                    <td><%= acc.getAccountType() %></td>
                                    <td class="mono"><%= String.format("%.2f", acc.getBalance()) %></td>
                                    <td>
                                        <a href="accounts?action=view&accNo=<%= acc.getAccountNumber() %>" class="btn btn-outline btn-sm" style="padding:0.25rem 0.5rem">View / Transact</a>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            <% } %>
        </section>
    </main>

    <script src="js/script.js"></script>
    <script>
        // Init Exp 10 session timer
        const loginTime = <%= session.getAttribute("loginTime") %>;
        const timeoutSeconds = <%= web.LoginServlet.SESSION_TIMEOUT_SECONDS %>;
        startSessionTimer(loginTime, timeoutSeconds);
    </script>
</body>
</html>
