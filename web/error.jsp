<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error | NovaBank</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="bg-orbs"><span></span><span></span><span></span></div>

    <main class="center-page">
        <section class="glass card login-card text-center">
            <h1 class="brand-title" style="color:var(--danger); -webkit-text-fill-color: var(--danger)">Error</h1>
            
            <div class="error-msg shake" style="margin: 2rem 0">
                <% 
                   String msg = (String) request.getAttribute("errorMessage");
                   if (msg == null && exception != null) {
                       msg = exception.getMessage();
                   }
                   if (msg == null) {
                       msg = "An unexpected error occurred.";
                   }
                %>
                <%= msg %>
            </div>

            <a href="javascript:history.back()" class="btn btn-primary">Go Back</a>
        </section>
    </main>
    <script src="js/script.js"></script>
</body>
</html>
