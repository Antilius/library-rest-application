package pl.umcs.Services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.Repositories.BookLendingRepository;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.booklendinginfo.BookLending;
import pl.umcs.basis.lenderinfo.Lender;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BookLendingService {

    private final BookLendingRepository bookLendingRepository;
    private final BookService bookService;
    private final LenderService lenderService;

    @Autowired
    public BookLendingService(BookLendingRepository bookLendingRepository, BookService bookService, LenderService lenderService){
        this.bookLendingRepository = bookLendingRepository;
        this.bookService = bookService;
        this.lenderService = lenderService;
    }

    //CRUD

    public BookLending save(BookLending bookLending){
        BookLending savedBookLending = bookLendingRepository.save(bookLending);
        return savedBookLending;
    }

    public BookLending find(Long id){
        Optional<BookLending> foundBookLending = bookLendingRepository.findById(id);
        return (foundBookLending.isPresent()) ? (foundBookLending.get()) : (null);
    }

    public List<BookLending> findAll(){
        Iterable<BookLending> iterableOfBookLendings = bookLendingRepository.findAll();
        List<BookLending> listOfBookLendings = Lists.newArrayList(iterableOfBookLendings);
        return listOfBookLendings;
    }

    public void deleteBookLendingById(Long id){
        bookLendingRepository.deleteById(id);
    }

    public BookLending updateBookLending(BookLending bookLending){
        BookLending updatedBookLending = bookLendingRepository.save(bookLending);
        return updatedBookLending;
    }

    //BUSINESS LOGIC

    public List<BookLending> findByBookId(Long book_id){
        Book book = bookService.find(book_id);
        return bookLendingRepository.findByBook(book);
    }

    public boolean isBookLendingReturned(Long book_lending){
        BookLending lending = this.find(book_lending);
        return lending.isReturned();
    }

    public boolean isCandidateReturned(Long book_id){
        List<BookLending> lendingsOfIndicatedBook = findByBookId(book_id);
        boolean isCandidateReturned = true;
        for(BookLending lending: lendingsOfIndicatedBook){
                isCandidateReturned = lending.isReturned();
        }
        return isCandidateReturned;
    }

    public void lendBook(Long lender_id, Long book_id){
        if(lenderService.canLendBook(lender_id, book_id)){
            if(isCandidateReturned(book_id)){
                Lender lender = lenderService.find(lender_id);
                Book book = bookService.find(book_id);
                BookLending lending = new BookLending();
                lending.setBook(book);
                lending.setLendStart(Date.from(Instant.now()));
                lending.setExpirationDate(Date.from(Instant.now().plusSeconds(3600*24*bookService.getBaseDaysToReturn(book_id)/lenderService.getDistrustLevel(lender_id))));
                this.save(lending);
                lender.addLending(lending);
                lenderService.save(lender);
                System.out.println("Utworzono wypożyczenie książki: [ID] " + book_id + ": " + book.getTitle());
            }
            else{
                System.out.println("Książka [ID]: " + book_id + " już została wypożyczona!");
            }
        }
        else{
            System.out.println("Nie ma uprawnień do wypożyczenia!");
        }
    }

    private BookLending findCandidateToReturn(Long book_id){
        List<BookLending> lendingsOfIndicatedBook = findByBookId(book_id);
        for(BookLending lending: lendingsOfIndicatedBook){
            if(!lending.isReturned()){
                return lending;
            }
        }
        return null;
    }

    public void returnBook(Long book_id){
        BookLending lending = this.findCandidateToReturn(book_id);
        if(lending != null){
            lending.setReturned(Date.from(Instant.now()));
            this.save(lending);
        }
    }

}
