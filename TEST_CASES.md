# Test Cases & Viva Q&A

## Test Scenarios

### TC-01: Create a Savings Account (Normal)
| Field | Value |
|-------|-------|
| **Input** | Menu: 1 → Type: 1 → Name: "Rahul Sharma" → Deposit: 5000 → Rate: 4.0 |
| **Expected** | Account #1001 created with balance Rs.5000.00 |
| **Concept** | Exp 1 (branching), Exp 2 (constructor overloading) |

---

### TC-02: Normal Deposit
| Field | Value |
|-------|-------|
| **Input** | Menu: 2 → Account: 1001 → Amount: 2000 → Remark: "Salary" |
| **Expected** | Balance becomes Rs.7000.00. Transaction recorded with remark "Salary". |
| **Concept** | Exp 2 (method overloading — deposit with remark) |

---

### TC-03: Normal Withdrawal from Savings
| Field | Value |
|-------|-------|
| **Input** | Menu: 3 → Account: 1001 → Amount: 3000 → Remark: (skip) |
| **Expected** | Balance becomes Rs.4000.00 (assuming previous balance Rs.7000.00). |
| **Concept** | Exp 2 (method overloading — withdraw without remark) |

---

### TC-04: Withdrawal Breaking Minimum Balance (Savings)
| Field | Value |
|-------|-------|
| **Input** | Menu: 3 → Account: 1001 → Amount: 3500 (if balance is Rs.4000) |
| **Expected** | Error: "Withdrawal denied: resulting balance Rs.500.00 would be below the minimum required balance of Rs.1000.00" |
| **Concept** | Exp 3 (MinimumBalanceException — custom exception hierarchy) |

---

### TC-05: Create a Current Account and Use Overdraft
| Field | Value |
|-------|-------|
| **Input** | Menu: 1 → Type: 2 → Name: "Priya Patel" → Deposit: 3000 → Overdraft: 5000 |
| **Expected** | Current Account created. |
| **Follow-up** | Menu: 3 → Account: 1002 → Amount: 6000 |
| **Expected** | Balance becomes Rs.-3000.00. Warning: "Account is now in overdraft!" |
| **Concept** | Exp 3 (single inheritance — overridden withdraw) |

---

### TC-06: Overdraft Limit Exceeded (Current Account)
| Field | Value |
|-------|-------|
| **Input** | Menu: 3 → Account: 1002 → Amount: 10000 (balance Rs.-3000, overdraft Rs.5000) |
| **Expected** | Error: "Withdrawal of Rs.10000.00 exceeds available funds (Balance: Rs.-3000.00 + Overdraft: Rs.5000.00 = Rs.2000.00)" |
| **Concept** | Exp 3 (InsufficientFundsException) |

---

### TC-07: Transfer to a Non-Existent Account
| Field | Value |
|-------|-------|
| **Input** | Menu: 4 → From: 1001 → To: 9999 → Amount: 500 |
| **Expected** | Error: "Account not found with number: 9999" |
| **Concept** | Exp 3 (AccountNotFoundException) |

---

### TC-08: Negative Amount
| Field | Value |
|-------|-------|
| **Input** | Menu: 2 → Account: 1001 → Amount: -500 |
| **Expected** | Error: "Invalid amount: Rs.-500.00 — amount must be greater than zero." |
| **Concept** | Exp 3 (InvalidAmountException — throw/throws) |

---

### TC-09: Non-Numeric Menu Input
| Field | Value |
|-------|-------|
| **Input** | Menu: "abc" |
| **Expected** | Error: "Please enter a valid number (0-9)." Menu re-appears. |
| **Concept** | Exp 3 (InputMismatchException handling — program does not crash) |

---

### TC-10: Create Fixed Deposit and Attempt Withdrawal
| Field | Value |
|-------|-------|
| **Input** | Menu: 1 → Type: 3 → Name: "Amit Kumar" → Deposit: 50000 → Rate: 7.0 → Tenure: 12 |
| **Expected** | FD Account created. Maturity amount displayed. |
| **Follow-up** | Menu: 3 → Account: 1003 → Amount: 10000 |
| **Expected** | Error: "Withdrawal not allowed on Fixed Deposit accounts before maturity." |
| **Concept** | Exp 3 (multilevel inheritance — overridden withdraw) |

---

### TC-11: Normal Transfer Between Accounts
| Field | Value |
|-------|-------|
| **Input** | Menu: 4 → From: 1001 → To: 1002 → Amount: 1000 → Remark: "Loan repayment" |
| **Expected** | Rs.1000 deducted from 1001, Rs.1000 added to 1002. Both transactions recorded. |
| **Concept** | Exp 2 (transfer overloading), Exp 3 (try-catch-finally) |

---

### TC-12: Apply Interest to Savings Accounts
| Field | Value |
|-------|-------|
| **Input** | Menu: 9 |
| **Expected** | Interest calculated and added ONLY to Savings accounts (not Fixed Deposit). |
| **Concept** | Exp 1 (looping + instanceof check), Exp 3 (hierarchical inheritance) |

---

### TC-13: Search by Account Number
| Field | Value |
|-------|-------|
| **Input** | Menu: 5 → Method: 1 → Account: 1001 |
| **Expected** | Displays details of account #1001. |
| **Concept** | Exp 2 (searchAccount(int) — overload 1) |

---

### TC-14: Search by Holder Name
| Field | Value |
|-------|-------|
| **Input** | Menu: 5 → Method: 2 → Name: "Rahul Sharma" |
| **Expected** | Lists all accounts belonging to "Rahul Sharma". |
| **Concept** | Exp 2 (searchAccount(String) — overload 2) |

---

### TC-15: Search by Name and Type
| Field | Value |
|-------|-------|
| **Input** | Menu: 5 → Method: 3 → Name: "Rahul Sharma" → Type: "Savings" |
| **Expected** | Lists only Savings accounts for "Rahul Sharma". |
| **Concept** | Exp 2 (searchAccount(String, String) — overload 3) |

---

### TC-16: Savings Account with Deposit Below Minimum
| Field | Value |
|-------|-------|
| **Input** | Menu: 1 → Type: 1 → Name: "Test" → Deposit: 500 → Rate: 3.0 |
| **Expected** | Error: "Savings account requires minimum deposit of Rs.1000.00. You provided Rs.500.00" |
| **Concept** | Exp 3 (InvalidAmountException — validation in Bank) |

---

### TC-17: View Transaction History
| Field | Value |
|-------|-------|
| **Input** | Menu: 8 → Account: 1001 |
| **Expected** | Lists all transactions for #1001 in tabular format with type, amount, balance, remark, and date. |
| **Concept** | Exp 1 (for-each loop over transactions) |

---

## Viva Q&A (10 Questions)

### Q1. What are the three types of inheritance demonstrated in this project?
**A:** 
- **Single Inheritance:** `SavingsAccount extends Account` and `CurrentAccount extends Account`.
- **Multilevel Inheritance:** `FixedDepositAccount extends SavingsAccount extends Account` — three levels.
- **Hierarchical Inheritance:** Both `SavingsAccount` and `CurrentAccount` extend the same parent `Account`.

### Q2. What is constructor overloading? How is it shown in the Account class?
**A:** Constructor overloading means having multiple constructors in the same class with **different parameter lists**. In `Account`, we have:
1. `Account(String name)` — creates account with zero balance.
2. `Account(String name, double initialDeposit)` — creates with an initial deposit.
3. `Account(String name, double initialDeposit, String accountType)` — creates with deposit and type.

Java picks the correct constructor based on the number and types of arguments passed.

### Q3. How is method overloading different from method overriding?
**A:**
- **Overloading:** Same method name, different parameter lists, in the **same class** (or inherited). Example: `deposit(double)` and `deposit(double, String)`.
- **Overriding:** Same method name AND same parameter list, in a **subclass**. Example: `SavingsAccount.withdraw()` overrides `Account.withdraw()` to add minimum balance check.

### Q4. What is the difference between `throw` and `throws`?
**A:**
- `throw` is used **inside a method** to actually throw an exception object: `throw new InvalidAmountException(amount);`
- `throws` is used **in the method signature** to declare that the method may throw certain exceptions: `public void withdraw(double amount) throws InsufficientFundsException`

### Q5. Why does MinimumBalanceException extend InsufficientFundsException?
**A:** It forms an **exception hierarchy**. A minimum-balance violation IS a special kind of insufficient-funds error. This allows:
1. The `withdraw()` method in `SavingsAccount` to throw `MinimumBalanceException` while the parent declares `throws InsufficientFundsException` (since a subclass exception is compatible with a superclass declaration).
2. The caller to catch `MinimumBalanceException` **specifically** (before the generic `InsufficientFundsException`) for a more detailed error message.

### Q6. What is the purpose of the `finally` block? Where is it used?
**A:** The `finally` block always executes, regardless of whether an exception was thrown or caught. In this project, it's used in:
- `createAccount()` — prints "Create-account operation finished" even if an exception occurs.
- `Bank.transfer()` — prints "Transfer operation finished" whether the transfer succeeds or fails.
It's commonly used for cleanup tasks like closing resources.

### Q7. How does the program handle invalid (non-numeric) menu input?
**A:** The `Scanner.nextInt()` throws a built-in `InputMismatchException` if the user types text instead of a number. We catch this exception, print a friendly error message, call `scanner.nextLine()` to clear the bad input, and the `do-while` loop continues — the program **never crashes**.

### Q8. What is hierarchical inheritance? Illustrate with this project.
**A:** Hierarchical inheritance is when **two or more classes** extend the **same parent class**:
```
          Account
         /       \
SavingsAccount   CurrentAccount
```
Both `SavingsAccount` and `CurrentAccount` inherit common fields (`accountNumber`, `balance`, etc.) and methods (`deposit()`, `withdraw()`, `displayDetails()`) from `Account`, but each adds or overrides behavior specific to its type.

### Q9. Why can't you withdraw from a FixedDepositAccount? How is this implemented?
**A:** A Fixed Deposit locks funds for a fixed tenure. The `withdraw()` method in `FixedDepositAccount` **always throws** an `InsufficientFundsException` with the message "Withdrawal not allowed on Fixed Deposit accounts before maturity." This demonstrates how a subclass can **restrict** operations inherited from a superclass by overriding the method and throwing an exception.

### Q10. What is encapsulation and where is it used in this project?
**A:** Encapsulation means making fields `private` and providing controlled access through public `getters` and `setters`. In this project:
- All fields in `Account`, `Transaction`, `SavingsAccount`, etc. are **private**.
- Access is through methods like `getBalance()`, `getHolderName()`, `setInterestRate()`.
- The `setBalance()` method is `protected`, meaning only subclasses (not outside code) can directly modify the balance — external code must use `deposit()` and `withdraw()`.
