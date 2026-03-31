package library.model;

/**
 * Represents a book in the library system.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String isbn, String title, String author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }

    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setAvailable(boolean available) { this.isAvailable = available; }

    @Override
    public String toString() {
        return String.format("ISBN: %-15s | Title: %-30s | Author: %-20s | Genre: %-15s | Status: %s",
                isbn, title, author, genre, isAvailable ? "Available" : "Borrowed");
    }
}
