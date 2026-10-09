package web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Bank staff login.
 *
 * <p><b>Experiment 5 - JSP + JDBC login validation.</b></p>
 * <pre>
 *   login.jsp --POST--> LoginServlet --JDBC--> MySQL (bankdb.users)
 *                            |
 *              valid  ------>+------> /accounts  (dashboard, redirect)
 *              invalid ----->+------> error.jsp  (forward)
 * </pre>
 *
 * <p>Database settings are read from context parameters in WEB-INF/web.xml
 * so they can be changed without recompiling.</p>
 *
 * Mapped to URL: <code>/login</code>
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Session timeout in seconds; also drives the JS session stopwatch [Experiment 10]. */
    public static final int SESSION_TIMEOUT_SECONDS = 15 * 60;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    /** Loads the JDBC driver and reads DB settings once at startup. */
    @Override
    public void init() throws ServletException {
        ServletContext ctx = getServletContext();
        dbUrl      = ctx.getInitParameter("dbUrl");
        dbUser     = ctx.getInitParameter("dbUser");
        dbPassword = ctx.getInitParameter("dbPassword");

        try {
            // Step 1 of JDBC: load the driver class
            Class.forName(ctx.getInitParameter("dbDriver"));
        } catch (ClassNotFoundException e) {
            throw new ServletException(
                    "JDBC driver not found. Copy mysql-connector-j.jar into "
                    + "WEB-INF/lib or Tomcat's lib folder.", e);
        }
    }

    /** Processes the login form. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Server-side validation: never trust the browser alone
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            fail(request, response, "Username and password are required.");
            return;
        }
        username = username.trim();

        try {
            String fullName = authenticate(username, password);

            if (fullName != null) {
                // Valid login: start a fresh session and redirect (PRG pattern)
                HttpSession old = request.getSession(false);
                if (old != null) {
                    old.invalidate();             // prevents session fixation
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("fullName", fullName);
                session.setAttribute("loginTime", System.currentTimeMillis());
                session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);
                response.sendRedirect("accounts");
            } else {
                fail(request, response, "Invalid username or password.");
            }

        } catch (SQLException e) {
            log("Database error during login", e);
            fail(request, response, "Database error: " + e.getMessage());
        }
    }

    /** GET /login?action=logout ends the session; any other GET shows the form. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String target = "login.jsp";
        if ("logout".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            target = "timeout".equals(request.getParameter("reason"))
                    ? "login.jsp?msg=timeout" : "login.jsp?msg=logout";
        }
        response.sendRedirect(target);
    }

    /**
     * Checks the credentials against the database.
     *
     * @return the user's full name if valid, otherwise {@code null}
     */
    private String authenticate(String username, String password) throws SQLException {
        try {
            // PreparedStatement with ? placeholders prevents SQL injection
            String sql = "SELECT full_name FROM users WHERE username = ? AND password_hash = ?";

            // try-with-resources closes Connection, Statement and ResultSet automatically
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, username);
                ps.setString(2, sha256(password));

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? rs.getString("full_name") : null;
                }
            }
        } catch (SQLException e) {
            log("Database connection failed. Using fallback authentication.", e);
            // Fallback for Railway/demo without a DB
            if ("admin".equals(username) && "admin123".equals(password)) {
                return "Administrator (Fallback)";
            }
            if ("student".equals(username) && "student123".equals(password)) {
                return "Test Student (Fallback)";
            }
            if ("ravi".equals(username) && "ravi@2024".equals(password)) {
                return "Ravi Kumar (Fallback)";
            }
            return null; // Invalid credentials
        }
    }

    /** Forwards to error.jsp with a message (forward keeps the request attributes). */
    private void fail(HttpServletRequest request, HttpServletResponse response,
                      String message) throws ServletException, IOException {
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

    /** Hashes the password with SHA-256 so plain-text passwords are never stored. */
    private static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b & 0xff));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
