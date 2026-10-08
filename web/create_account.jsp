<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account | NovaBank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="bg-orbs"><span></span><span></span><span></span></div>

    <main class="center-page" style="align-items: flex-start; padding-top: 5vh;">
        <section class="glass card form-card">
            <div class="top-bar" style="margin-bottom: 1rem">
                <h2>Open New Account</h2>
                <a href="accounts" class="btn btn-outline btn-sm" style="width:auto">Back</a>
            </div>
            <p class="muted" style="margin-bottom:2rem">Includes JS Email Validation (Experiment 8)</p>

            <form id="create-form" action="accounts" method="post">
                <input type="hidden" name="action" value="create">

                <div class="form-group">
                    <label for="type">Account Type</label>
                    <select id="type" name="type" required onchange="toggleTypeFields()">
                        <option value="Savings">Savings Account</option>
                        <option value="Current">Current Account</option>
                        <option value="FixedDeposit">Fixed Deposit Account</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="name">Holder Name</label>
                    <input type="text" id="name" name="name" required>
                </div>

                <!-- Exp 8: Email Validation Field -->
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email">
                    <ul class="email-rules" id="email-rules-list">
                        <li id="rule-at">Must contain '@' symbol</li>
                        <li id="rule-dot">Must contain '.' after '@'</li>
                        <li id="rule-pos">Valid format (e.g. user@domain.com)</li>
                    </ul>
                </div>

                <div class="form-group">
                    <label for="deposit">Initial Deposit (Rs.)</label>
                    <input type="number" step="0.01" id="deposit" name="deposit" required>
                </div>

                <!-- Dynamic fields based on account type -->
                <div id="savings-fields" style="display:block">
                    <div class="form-group">
                        <label for="rate">Interest Rate (%)</label>
                        <input type="number" step="0.1" id="rate" name="rate" value="4.0">
                    </div>
                </div>

                <div id="current-fields" style="display:none">
                    <div class="form-group">
                        <label for="limit">Overdraft Limit (Rs.)</label>
                        <input type="number" step="0.01" id="limit" name="limit" value="10000.00">
                    </div>
                </div>

                <div id="fd-fields" style="display:none">
                    <div class="form-group">
                        <label for="tenure">Tenure (Months)</label>
                        <input type="number" id="tenure" name="tenure" value="12">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Create Account</button>
            </form>
        </section>
    </main>

    <script src="js/script.js"></script>
    <script>
        function toggleTypeFields() {
            const type = document.getElementById('type').value;
            document.getElementById('savings-fields').style.display = type === 'Savings' || type === 'FixedDeposit' ? 'block' : 'none';
            document.getElementById('current-fields').style.display = type === 'Current' ? 'block' : 'none';
            document.getElementById('fd-fields').style.display = type === 'FixedDeposit' ? 'block' : 'none';
            
            // FixedDeposit uses the rate field from savings-fields
        }
    </script>
</body>
</html>
