package library.model;

import java.time.LocalDate;

/**
 * Represents a borrow/return transaction in the library.
 */
public class Transaction {
    public enum Type { BORROW, RETURN }

    private String transactionId;
    private String memberId;
    private String isbn;
    private Type type;
    private LocalDate date;

    public Transaction(String transactionId, String memberId, String isbn, Type type) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.type = type;
        this.date = LocalDate.now();
    }

    public String getTransactionId() { return transactionId; }
    public String getMemberId() { return memberId; }
    public String getIsbn() { return isbn; }
    public Type getType() { return type; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return String.format("TxID: %-8s | Member: %-10s | ISBN: %-15s | Type: %-8s | Date: %s",
                transactionId, memberId, isbn, type, date);
    }
}
