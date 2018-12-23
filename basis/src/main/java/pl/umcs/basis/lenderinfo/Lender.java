package pl.umcs.basis.lenderinfo;

import pl.umcs.basis.booklendinginfo.BookLending;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Lender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(nullable = false)
    private int penalty;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lender_id")
    private List<BookLending> bookLendings;

    public Lender() {
        this.bookLendings = new LinkedList<>();
    }

    public Lender(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.bookLendings = new LinkedList<BookLending>();
        this.penalty = 0;
    }

    public Lender(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.bookLendings = new LinkedList<BookLending>();
        this.penalty = 0;
    }

    public Lender(Long id, String name, String surname, List<BookLending> bookLendings) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.bookLendings = bookLendings;
        this.penalty = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<BookLending> getBookLendings() {
        return bookLendings;
    }

    public void setBookLendings(List<BookLending> bookLendings) {
        this.bookLendings = bookLendings;
    }

    public void addLending(BookLending lending){
        this.bookLendings.add(lending);
    };

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
