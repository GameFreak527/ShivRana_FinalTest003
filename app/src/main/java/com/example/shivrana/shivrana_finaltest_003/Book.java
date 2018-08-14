package com.example.shivrana.shivrana_finaltest_003;

public class Book {
    int bookISBN;
    String bookName, authorName;
    double Price;

    public Book(){}

    public Book(int bookISBN, String bookName, String authorName, double price) {
        this.bookISBN = bookISBN;
        this.bookName = bookName;
        this.authorName = authorName;
        this.Price = price;
    }

    public int getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(int bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
