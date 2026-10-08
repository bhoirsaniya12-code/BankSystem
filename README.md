# Bank Account Management System

## Project Description

A console-based **Bank Account Management System** built with **Core Java (JDK 17+)**.  
This project is a lab mini-project for second-year engineering students that demonstrates three core Java topics:

1. **Experiment 1** — Classes and Objects with Looping and Branching  
2. **Experiment 2** — Method Overloading and Constructor Overloading  
3. **Experiment 3** — Types of Inheritance and Exception Handling  
4. **Experiment 4** — Generic and HTTP Servlets
5. **Experiment 5** — JSP & JDBC Login
6. **Experiment 8, 9, 10, 11** — JavaScript Features (Email validation, Background Colors, Stopwatch, Animation)

**Tech Stack:** Core Java, Servlets, JSP, JDBC, HTML, CSS, JavaScript (Vanilla).

---

## How to Compile and Run

### 1. Console Application (Core Java)
Open a terminal in the `BankSystem` directory and run:

```bash
javac -d out src/exceptions/*.java src/model/*.java src/service/*.java src/app/*.java
java -cp out app.Main
```

### 2. Web Application (Servlets & JSP)
**Prerequisites**: Apache Tomcat 10/11, MySQL 8+, `mysql-connector-j.jar`, `jakarta.servlet-api.jar` (or equivalent).

1. **Database Setup**: Open MySQL and run the `db_setup.sql` script to create the `labdb` database and `users` table.
2. **Compile Web Classes**:
   ```bash
   javac -cp "path/to/servlet-api.jar" -d web/WEB-INF/classes src/exceptions/*.java src/model/*.java src/service/*.java src/web/*.java
   ```
3. **Deployment**: 
   - Copy the entire `web/` folder into your Tomcat `webapps/` directory and rename it to `BankSystem`.
   - Place `mysql-connector-j.jar` in `BankSystem/WEB-INF/lib`.
4. **Run**: Start Tomcat and visit `http://localhost:8080/BankSystem`. Login with `admin` / `admin123`.

---

## Package Structure

```
BankSystem/
├── src/
│   ├── exceptions/          ← Custom checked exceptions
│   ├── model/               ← Data classes (Account hierarchy)
│   ├── service/             ← Business logic
│   ├── app/                 ← Console Entry point (Main.java)
│   └── web/                 ← Web Entry point (Servlets)
├── web/
│   ├── WEB-INF/
│   │   └── web.xml          ← Deployment Descriptor
│   ├── css/style.css        ← UI Styling (Glassmorphism)
│   ├── js/script.js         ← JavaScript logic
│   ├── login.jsp            ← Staff login
│   ├── dashboard.jsp        ← Account management UI
│   ├── create_account.jsp   ← Account creation UI
│   ├── account_details.jsp  ← Transaction UI
│   ├── error.jsp            ← Error handling UI
│   └── index.html           ← Redirect
├── db_setup.sql             ← MySQL Schema
├── out/                     ← Compiled .class files for console app
├── README.md
└── TEST_CASES.md
```

---

## Feature-to-Experiment Mapping

| Feature | File(s) | Experiment |
|---------|---------|------------|
| Account, SavingsAccount, CurrentAccount, FixedDepositAccount classes | `model/*.java` | Exp 1 — Classes & Objects |
| Transaction class with encapsulation | `model/Transaction.java` | Exp 1 — Classes & Objects |
| `do-while` loop for main menu | `app/Main.java` | Exp 1 — Looping |
| `for-each` loops over accounts/transactions | `model/Account.java`, `service/Bank.java` | Exp 1 — Looping |
| `switch-case` for menu choices | `app/Main.java` | Exp 1 — Branching |
| `if-else` validations throughout | All classes | Exp 1 — Branching |
| 3 overloaded constructors in Account | `model/Account.java` | Exp 2 — Constructor Overloading |
| `deposit(amount)` and `deposit(amount, remark)` | `model/Account.java` | Exp 2 — Method Overloading |
| `withdraw(amount)` and `withdraw(amount, remark)` | `model/Account.java` | Exp 2 — Method Overloading |
| `searchAccount(int)`, `searchAccount(String)`, `searchAccount(String, String)` | `service/Bank.java` | Exp 2 — Method Overloading |
| `transfer(from, to, amt)` and `transfer(from, to, amt, remark)` | `service/Bank.java` | Exp 2 — Method Overloading |
| `SavingsAccount extends Account` | `model/SavingsAccount.java` | Exp 3 — Single Inheritance |
| `CurrentAccount extends Account` | `model/CurrentAccount.java` | Exp 3 — Single Inheritance |
| Both SavingsAccount & CurrentAccount extend Account | `model/SavingsAccount.java`, `model/CurrentAccount.java` | Exp 3 — Hierarchical Inheritance |
| `FixedDepositAccount extends SavingsAccount extends Account` | `model/FixedDepositAccount.java` | Exp 3 — Multilevel Inheritance |
| 4 custom checked exceptions | `exceptions/*.java` | Exp 3 — Exception Handling |
| `try-catch-finally` blocks | `app/Main.java`, `service/Bank.java` | Exp 3 — Exception Handling |
| `throw` and `throws` keywords | `model/Account.java`, `model/SavingsAccount.java`, etc. | Exp 3 — Exception Handling |
| `InputMismatchException` handling | `app/Main.java` | Exp 3 — Exception Handling |
| `MinimumBalanceException extends InsufficientFundsException` | `exceptions/MinimumBalanceException.java` | Exp 3 — Exception Hierarchy |
| GenericServlet (`BankInfoServlet.java`) | `web/BankInfoServlet.java` | Exp 4 — Generic Servlet |
| HttpServlet (`BankServlet.java`) | `web/BankServlet.java` | Exp 4 — HTTP Servlet |
| JSP & JDBC Login | `web/LoginServlet.java`, `login.jsp` | Exp 5 — JSP Login Validation |
| JavaScript Email Validation | `create_account.jsp`, `js/script.js` | Exp 8 — JS Email Validation |
| Background Color Changer | `js/script.js` | Exp 9 — DOM Background |
| Session Stopwatch | `dashboard.jsp`, `js/script.js` | Exp 10 — JavaScript Timer |
| Background Animations | `css/style.css` | Exp 11 — JS/CSS Animations |

---

## Sample Output

```
==================================================
       BANK ACCOUNT MANAGEMENT SYSTEM
       Built with Core Java (JDK 17+)
==================================================

--------------------------------------------------
                    MAIN MENU                     
--------------------------------------------------
  1. Create Account (Savings / Current / FD)
  2. Deposit
  3. Withdraw
  4. Transfer
  5. Search Account (by number / by name)
  6. View Account Details
  7. View All Accounts
  8. View Transaction History
  9. Apply Interest to Savings Accounts
  0. Exit
--------------------------------------------------
  Enter your choice: 1

  --- Create New Account ---
  1. Savings Account
  2. Current Account
  3. Fixed Deposit Account
  Choose account type (1-3): 1
  Enter holder name: Rahul Sharma
  Enter initial deposit (min Rs.1000.00): 5000
  Enter interest rate (e.g. 4.0): 4.0
  [OK] Savings Account #1001 created for Rahul Sharma.
  [INFO] Create-account operation finished.

  Enter your choice: 2
  Enter account number: 1001
  Enter deposit amount: 2000
  Enter remark (or press Enter to skip): Salary credit
  [OK] Deposited Rs.2000.00 successfully. New balance: Rs.7000.00

  Enter your choice: 3
  Enter account number: 1001
  Enter withdrawal amount: 6500
  Enter remark (or press Enter to skip):
  [ERROR] Minimum Balance Violation!
  Withdrawal denied: resulting balance Rs.500.00 would be below the minimum required balance of Rs.1000.00

  Enter your choice: 0
  Thank you for using the Bank System. Goodbye!
```

---

## Authors
- Student (Second-Year Engineering)

## License
This project is for educational purposes only.
