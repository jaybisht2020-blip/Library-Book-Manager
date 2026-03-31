package library;

import library.model.Book;
import library.model.Member;
import library.model.Transaction;
import library.service.LibraryService;
import library.util.LibraryException;

import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Library Book Manager application.
 * Provides an interactive command-line interface for all library operations.
 *
 * @author Student
 * @version 1.0
 */
public class Main {

    private static final LibraryService libraryService = new LibraryService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║      LIBRARY BOOK MANAGER v1.0       ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Sample data loaded. Welcome!\n");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> manageBooks();
                case 2 -> manageMembers();
                case 3 -> borrowReturnMenu();
                case 4 -> viewReports();
                case 5 -> { System.out.println("Goodbye!"); running = false; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // ─── Menus ───────────────────────────────────────────────────────────────

    private static void printMainMenu() {
        System.out.println("\n─────────── MAIN MENU ───────────");
        System.out.println("1. Manage Books");
        System.out.println("2. Manage Members");
        System.out.println("3. Borrow / Return Books");
        System.out.println("4. View Reports");
        System.out.println("5. Exit");
        System.out.println("─────────────────────────────────");
    }

    private static void manageBooks() {
        System.out.println("\n── Book Management ──");
        System.out.println("1. View All Books");
        System.out.println("2. View Available Books");
        System.out.println("3. Search by Title");
        System.out.println("4. Search by Author");
        System.out.println("5. Add New Book");
        System.out.println("6. Remove Book");
        System.out.println("0. Back");

        int choice = readInt("Choice: ");
        switch (choice) {
            case 1 -> printList("ALL BOOKS", libraryService.getAllBooks());
            case 2 -> printList("AVAILABLE BOOKS", libraryService.getAvailableBooks());
            case 3 -> {
                String kw = readString("Enter title keyword: ");
                printList("SEARCH RESULTS", libraryService.searchBooksByTitle(kw));
            }
            case 4 -> {
                String author = readString("Enter author name: ");
                printList("SEARCH RESULTS", libraryService.searchBooksByAuthor(author));
            }
            case 5 -> addBook();
            case 6 -> removeBook();
            case 0 -> {}
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageMembers() {
        System.out.println("\n── Member Management ──");
        System.out.println("1. View All Members");
        System.out.println("2. Register New Member");
        System.out.println("0. Back");

        int choice = readInt("Choice: ");
        switch (choice) {
            case 1 -> printList("ALL MEMBERS", libraryService.getAllMembers());
            case 2 -> registerMember();
            case 0 -> {}
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void borrowReturnMenu() {
        System.out.println("\n── Borrow / Return ──");
        System.out.println("1. Borrow a Book");
        System.out.println("2. Return a Book");
        System.out.println("0. Back");

        int choice = readInt("Choice: ");
        switch (choice) {
            case 1 -> borrowBook();
            case 2 -> returnBook();
            case 0 -> {}
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void viewReports() {
        System.out.println("\n── Reports ──");
        System.out.println("1. Library Summary");
        System.out.println("2. Transaction History");
        System.out.println("0. Back");

        int choice = readInt("Choice: ");
        switch (choice) {
            case 1 -> libraryService.printSummary();
            case 2 -> printList("TRANSACTION HISTORY", libraryService.getTransactionHistory());
            case 0 -> {}
        }
    }

    // ─── Operations ──────────────────────────────────────────────────────────

    private static void addBook() {
        System.out.println("\n── Add New Book ──");
        String isbn   = readString("ISBN: ");
        String title  = readString("Title: ");
        String author = readString("Author: ");
        String genre  = readString("Genre: ");
        try {
            libraryService.addBook(new Book(isbn, title, author, genre));
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeBook() {
        String isbn = readString("Enter ISBN to remove: ");
        try {
            libraryService.removeBook(isbn);
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registerMember() {
        System.out.println("\n── Register Member ──");
        String id    = readString("Member ID: ");
        String name  = readString("Name: ");
        String email = readString("Email: ");
        try {
            libraryService.registerMember(new Member(id, name, email));
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void borrowBook() {
        String memberId = readString("Member ID: ");
        String isbn     = readString("Book ISBN: ");
        try {
            libraryService.borrowBook(memberId, isbn);
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        String memberId = readString("Member ID: ");
        String isbn     = readString("Book ISBN: ");
        try {
            libraryService.returnBook(memberId, isbn);
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    private static <T> void printList(String title, List<T> items) {
        System.out.println("\n── " + title + " (" + items.size() + " items) ──");
        if (items.isEmpty()) {
            System.out.println("(No items found)");
        } else {
            items.forEach(System.out::println);
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
