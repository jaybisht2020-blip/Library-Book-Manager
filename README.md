# 📚 Library Book Manager

A console-based Java application to manage books, members, and borrowing/returning transactions in a small library. Built as a capstone project for the *Programming in Java* course.

---

## Features

- **Book Management** – Add, remove, search by title or author, view all/available books
- **Member Management** – Register members, view all members and their borrow status
- **Borrow & Return** – Issue books to members, return them, with borrow-limit enforcement (max 3 per member)
- **Transaction Log** – Full history of all borrow/return actions
- **Library Summary** – At-a-glance stats on books, availability, members, and transactions

---

## Project Structure

```
LibraryBookManager/
└── src/
    └── library/
        ├── Main.java                        ← Entry point & CLI menu
        ├── model/
        │   ├── Book.java                    ← Book entity
        │   ├── Member.java                  ← Member entity
        │   └── Transaction.java             ← Transaction record
        ├── service/
        │   └── LibraryService.java          ← Core business logic
        └── util/
            └── LibraryException.java        ← Custom exception
```

---

## Requirements

- **Java 17+** (uses switch expressions and text blocks)
- No external dependencies — pure Java standard library

---

## How to Run

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/LibraryBookManager.git
cd LibraryBookManager
```

### 2. Compile
```bash
javac -d out -sourcepath src src/library/Main.java
```

### 3. Run
```bash
java -cp out library.Main
```

---

## Usage

On launch, the app loads sample books and members. Navigate using numbered menu options:

```
╔══════════════════════════════════════╗
║      LIBRARY BOOK MANAGER v1.0       ║
╚══════════════════════════════════════╝

─────────── MAIN MENU ───────────
1. Manage Books
2. Manage Members
3. Borrow / Return Books
4. View Reports
5. Exit
─────────────────────────────────
```

### Example: Borrow a Book
```
Choose 3 → 1
Member ID: M001
Book ISBN: 978-0-06-112008-4
SUCCESS: "To Kill a Mockingbird" borrowed by Alice Johnson
```

### Example: Search by Author
```
Choose 1 → 3
Enter author name: Orwell
── SEARCH RESULTS (1 items) ──
ISBN: 978-0-7432-7356-5 | Title: 1984 | Author: George Orwell | ...
```

---

## Sample Data (Pre-loaded)

| ISBN | Title | Author |
|------|-------|--------|
| 978-0-06-112008-4 | To Kill a Mockingbird | Harper Lee |
| 978-0-7432-7356-5 | 1984 | George Orwell |
| 978-0-14-028329-7 | The Great Gatsby | F. Scott Fitzgerald |
| 978-1-5011-9193-9 | Educated | Tara Westover |
| 978-0-525-55360-5 | The Midnight Library | Matt Haig |

**Members:** M001 – Alice Johnson, M002 – Bob Smith, M003 – Carol White

---

## Java Concepts Demonstrated

- **OOP** – Classes, encapsulation, single responsibility principle
- **Inheritance & Enums** – `Transaction.Type` enum
- **Collections** – `HashMap`, `ArrayList`, `List`
- **Exception Handling** – Custom `LibraryException`, try-catch throughout
- **Generics** – Generic `printList()` helper method

---

## Author

Submitted as BYOP capstone for the *Programming in Java* evaluated course project.
