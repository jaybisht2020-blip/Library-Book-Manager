package library.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow books.
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private List<String> borrowedIsbns; // ISBNs of currently borrowed books
    private static final int MAX_BORROW_LIMIT = 3;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedIsbns = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<String> getBorrowedIsbns() { return borrowedIsbns; }

    public boolean canBorrow() {
        return borrowedIsbns.size() < MAX_BORROW_LIMIT;
    }

    public void borrowBook(String isbn) {
        borrowedIsbns.add(isbn);
    }

    public void returnBook(String isbn) {
        borrowedIsbns.remove(isbn);
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %-20s | Email: %-25s | Books Borrowed: %d/%d",
                memberId, name, email, borrowedIsbns.size(), MAX_BORROW_LIMIT);
    }
}
