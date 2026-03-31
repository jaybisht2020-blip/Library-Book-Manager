package library.service;

import library.model.Book;
import library.model.Member;
import library.model.Transaction;
import library.util.LibraryException;

import java.util.*;

/**
 * Core service class that manages all library operations.
 * Handles books, members, and transactions using OOP principles.
 */
public class LibraryService {

    private Map<String, Book> books;           // ISBN -> Book
    private Map<String, Member> members;       // MemberID -> Member
    private List<Transaction> transactions;
    private int transactionCounter;

    public LibraryService() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
        loadSampleData();
    }

    // ─── Book Operations ─────────────────────────────────────────────────────

    public void addBook(Book book) throws LibraryException {
        if (books.containsKey(book.getIsbn())) {
            throw new LibraryException("Book with ISBN " + book.getIsbn() + " already exists.");
        }
        books.put(book.getIsbn(), book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(String isbn) throws LibraryException {
        Book book = getBook(isbn);
        if (!book.isAvailable()) {
            throw new LibraryException("Cannot remove a borrowed book.");
        }
        books.remove(isbn);
        System.out.println("Book removed: " + book.getTitle());
    }

    public Book getBook(String isbn) throws LibraryException {
        Book book = books.get(isbn);
        if (book == null) throw new LibraryException("Book not found with ISBN: " + isbn);
        return book;
    }

    public List<Book> searchBooksByTitle(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.isAvailable()) result.add(b);
        }
        return result;
    }

    // ─── Member Operations ───────────────────────────────────────────────────

    public void registerMember(Member member) throws LibraryException {
        if (members.containsKey(member.getMemberId())) {
            throw new LibraryException("Member ID " + member.getMemberId() + " already exists.");
        }
        members.put(member.getMemberId(), member);
        System.out.println("Member registered: " + member.getName());
    }

    public Member getMember(String memberId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) throw new LibraryException("Member not found: " + memberId);
        return member;
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    // ─── Borrow / Return Operations ──────────────────────────────────────────

    public void borrowBook(String memberId, String isbn) throws LibraryException {
        Member member = getMember(memberId);
        Book book = getBook(isbn);

        if (!book.isAvailable()) {
            throw new LibraryException("Book \"" + book.getTitle() + "\" is currently borrowed.");
        }
        if (!member.canBorrow()) {
            throw new LibraryException("Member " + member.getName() + " has reached the borrow limit (3 books).");
        }

        book.setAvailable(false);
        member.borrowBook(isbn);

        String txId = "TX" + String.format("%04d", transactionCounter++);
        transactions.add(new Transaction(txId, memberId, isbn, Transaction.Type.BORROW));
        System.out.println("SUCCESS: \"" + book.getTitle() + "\" borrowed by " + member.getName());
    }

    public void returnBook(String memberId, String isbn) throws LibraryException {
        Member member = getMember(memberId);
        Book book = getBook(isbn);

        if (!member.getBorrowedIsbns().contains(isbn)) {
            throw new LibraryException("This member did not borrow this book.");
        }

        book.setAvailable(true);
        member.returnBook(isbn);

        String txId = "TX" + String.format("%04d", transactionCounter++);
        transactions.add(new Transaction(txId, memberId, isbn, Transaction.Type.RETURN));
        System.out.println("SUCCESS: \"" + book.getTitle() + "\" returned by " + member.getName());
    }

    // ─── Reports ─────────────────────────────────────────────────────────────

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }

    public void printSummary() {
        System.out.println("\n========== LIBRARY SUMMARY ==========");
        System.out.println("Total Books    : " + books.size());
        System.out.println("Available Books: " + getAvailableBooks().size());
        System.out.println("Borrowed Books : " + (books.size() - getAvailableBooks().size()));
        System.out.println("Total Members  : " + members.size());
        System.out.println("Transactions   : " + transactions.size());
        System.out.println("=====================================\n");
    }

    // ─── Sample Data ─────────────────────────────────────────────────────────

    private void loadSampleData() {
        try {
            addBook(new Book("978-0-06-112008-4", "To Kill a Mockingbird", "Harper Lee", "Fiction"));
            addBook(new Book("978-0-7432-7356-5", "1984",                  "George Orwell",  "Dystopian"));
            addBook(new Book("978-0-14-028329-7", "The Great Gatsby",      "F. Scott Fitzgerald", "Classic"));
            addBook(new Book("978-0-06-093546-9", "To Kill a Mockingbird", "Harper Lee",     "Fiction"));
            addBook(new Book("978-1-5011-9193-9", "Educated",              "Tara Westover",  "Memoir"));
            addBook(new Book("978-0-525-55360-5", "The Midnight Library",  "Matt Haig",      "Fiction"));

            registerMember(new Member("M001", "Alice Johnson", "alice@email.com"));
            registerMember(new Member("M002", "Bob Smith",     "bob@email.com"));
            registerMember(new Member("M003", "Carol White",   "carol@email.com"));
        } catch (LibraryException e) {
            System.err.println("Error loading sample data: " + e.getMessage());
        }
    }
}
