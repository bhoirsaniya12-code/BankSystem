<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NovaBank | Staff Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <!-- Experiment 11: JS Animation (Background Orbs) -->
    <div class="bg-orbs">
        <span></span><span></span><span></span>
    </div>

    <main class="center-page">
        <section class="glass card login-card">
            <div class="text-center">
                <h1 class="brand-title">NovaBank</h1>
                <p class="subtitle">Staff Portal Login (Experiment 5)</p>
            </div>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-msg shake">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } else if ("timeout".equals(request.getParameter("msg"))) { %>
                <div class="error-msg shake">
                    Session timed out due to inactivity. Please log in again.
                </div>
            <% } else if ("logout".equals(request.getParameter("msg"))) { %>
                <div class="success-msg">
                    You have been successfully logged out.
                </div>
            <% } %>

            <form action="login" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required autocomplete="off">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Sign In</button>
            </form>
            
            <div class="text-center" style="margin-top: 1.5rem;">
                <p class="small muted">System Status: <a href="bank-info" style="color:var(--accent-primary)">View (Exp 4)</a></p>
                <p class="small muted" style="margin-top: 0.5rem">Test User: <code>admin</code> / <code>admin123</code></p>
            </div>
        </section>
    </main>
    <script src="js/script.js"></script>
</body>
</html>
