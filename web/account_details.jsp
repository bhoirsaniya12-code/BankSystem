<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Account" %>
<%@ page import="model.Transaction" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<% 
    Account acc = (Account) request.getAttribute("account");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Details | NovaBank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="bg-orbs"><span></span><span></span><span></span></div>

    <main class="dashboard-page">
        <div class="top-bar">
            <h2>Account Details</h2>
            <a href="accounts" class="btn btn-outline btn-sm" style="width:auto">Back to Dashboard</a>
        </div>

        <% if (request.getParameter("msg") != null) { %>
            <div class="success-msg">
                <%= request.getParameter("msg").replace("+", " ") %>
            </div>
        <% } %>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap: 2rem;">
            <!-- Details -->
            <section class="glass card" style="padding: 1.5rem">
                <h3>Info</h3>
                <div class="stat-grid" style="gap:1rem; margin-bottom:0">
                    <div class="stat" style="padding:1rem">
                        <span class="stat-label">Account Number</span>
                        <span class="stat-value"><%= acc.getAccountNumber() %></span>
                    </div>
                    <div class="stat" style="padding:1rem">
                        <span class="stat-label">Balance</span>
                        <span class="stat-value" style="color:var(--success)">Rs.<%= String.format("%.2f", acc.getBalance()) %></span>
                    </div>
                    <div class="stat" style="padding:1rem">
                        <span class="stat-label">Type</span>
                        <span class="stat-value"><%= acc.getAccountType() %></span>
                    </div>
                    <div class="stat" style="padding:1rem">
                        <span class="stat-label">Holder</span>
                        <span class="stat-value" style="font-size:1.2rem"><%= acc.getHolderName() %></span>
                        <% if (acc.getEmail() != null) { %>
                            <span class="muted small"><%= acc.getEmail() %></span>
                        <% } %>
                    </div>
                </div>
            </section>

            <!-- Actions -->
            <section class="glass card" style="padding: 1.5rem">
                <h3>Transact</h3>
                <form action="accounts" method="post">
                    <input type="hidden" name="action" value="transaction">
                    <input type="hidden" name="accNo" value="<%= acc.getAccountNumber() %>">
                    
                    <div style="display:flex; gap:1rem; margin-bottom:1rem">
                        <div class="form-group" style="flex:1; margin:0">
                            <label>Type</label>
                            <select name="txnType" id="txnType" required onchange="toggleTransfer()">
                                <option value="deposit">Deposit</option>
                                <option value="withdraw">Withdraw</option>
                                <option value="transfer">Transfer</option>
                            </select>
                        </div>
                        <div class="form-group" style="flex:1; margin:0">
                            <label>Amount (Rs.)</label>
                            <input type="number" step="0.01" name="amount" required>
                        </div>
                    </div>
                    
                    <div class="form-group" id="transfer-group" style="display:none">
                        <label>To Account Number</label>
                        <input type="number" name="toAccNo" id="toAccNo">
                    </div>

                    <div class="form-group">
                        <label>Remark</label>
                        <input type="text" name="remark" value="Web Transaction">
                    </div>

                    <button type="submit" class="btn btn-primary">Submit Transaction</button>
                </form>
            </section>
        </div>

        <!-- History -->
        <section class="glass card" style="padding: 1.5rem; margin-top: 2rem">
            <h3>Transaction History</h3>
            <% if (acc.getTransactions().isEmpty()) { %>
                <p class="muted">No transactions found.</p>
            <% } else { %>
                <div style="overflow-x:auto">
                    <table>
                        <thead>
                            <tr>
                                <th>Date/Time</th>
                                <th>Type</th>
                                <th>Amount (Rs)</th>
                                <th>Balance After (Rs)</th>
                                <th>Remark</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Transaction t : acc.getTransactions()) { %>
                                <tr>
                                    <td class="muted"><%= t.getTimestamp().format(fmt) %></td>
                                    <td><%= t.getType() %></td>
                                    <td class="mono <%= t.getType().equals("Deposit") || t.getType().startsWith("Transfer IN") ? "text-success" : "" %>">
                                        <%= String.format("%.2f", t.getAmount()) %>
                                    </td>
                                    <td class="mono"><%= String.format("%.2f", t.getBalanceAfter()) %></td>
                                    <td class="muted"><%= t.getRemark() != null ? t.getRemark() : "-" %></td>
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
        function toggleTransfer() {
            const isTransfer = document.getElementById('txnType').value === 'transfer';
            document.getElementById('transfer-group').style.display = isTransfer ? 'block' : 'none';
            document.getElementById('toAccNo').required = isTransfer;
        }
    </script>
</body>
</html>
