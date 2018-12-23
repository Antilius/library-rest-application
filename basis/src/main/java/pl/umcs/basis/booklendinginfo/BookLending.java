package pl.umcs.basis.booklendinginfo;

import pl.umcs.basis.bookinfo.Book;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookLending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date lendStart;
    private Date expirationDate;
    private Date returned;

    @OneToOne()
    private Book book;

    public BookLending() {
    }

    public BookLending(Date lendStart, Date expirationDate) {
        this.lendStart = lendStart;
        this.expirationDate = expirationDate;
    }

    public BookLending(Long id, Date lendStart, Date expirationDate, Date returned) {
        this.id = id;
        this.lendStart = lendStart;
        this.expirationDate = expirationDate;
        this.returned = returned;
    }

    public boolean isReturned(){
        return (returned!=null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLendStart() {
        return lendStart;
    }

    public void setLendStart(Date lendStart) {
        this.lendStart = lendStart;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getReturned() {
        return returned;
    }

    public void setReturned(Date returned) {
        this.returned = returned;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
