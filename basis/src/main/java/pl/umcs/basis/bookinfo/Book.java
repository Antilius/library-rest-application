package pl.umcs.basis.bookinfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.umcs.basis.booklendinginfo.BookLending;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int pages;
    @Enumerated(EnumType.STRING)
    private BookAvailability bookAvailability;
    @Enumerated(EnumType.STRING)
    private BookRarity bookRarity;

    @OneToOne(mappedBy = "book")
    @JsonIgnore
    private BookLending bookLending;


    public Book() {
    }

    public Book(String title, int pages, BookAvailability bookAvailability, BookRarity bookRarity) {
        this.title = title;
        this.pages = pages;
        this.bookAvailability = bookAvailability;
        this.bookRarity = bookRarity;
    }

    public Book(Long id, String title, String author, int pages, BookAvailability bookAvailability, BookRarity bookRarity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.bookAvailability = bookAvailability;
        this.bookRarity = bookRarity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public BookAvailability getBookAvailability() {
        return bookAvailability;
    }

    public void setBookAvailability(BookAvailability bookAvailability) {
        this.bookAvailability = bookAvailability;
    }

    public BookRarity getBookRarity() {
        return bookRarity;
    }

    public void setBookRarity(BookRarity bookRarity) {
        this.bookRarity = bookRarity;
    }

}
