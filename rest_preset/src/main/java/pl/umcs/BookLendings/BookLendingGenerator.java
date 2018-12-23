package pl.umcs.BookLendings;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.umcs.BookLendings.strategyImpl.CreateBookLendingUnreturnedPreset;
import pl.umcs.BookLendings.strategyImpl.CreateBookLendingExpiredPreset;
import pl.umcs.BookLendings.strategyImpl.LenderUpdatingStrategy;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.booklendinginfo.BookLending;
import pl.umcs.basis.lenderinfo.Lender;

import static org.springframework.http.HttpMethod.PUT;

public class BookLendingGenerator{

    public static final String URL_TO_LOAD_LENDERS = "http://localhost:8081/lenders";
    private RestTemplate restTemplate;

    public BookLendingGenerator() {
        this.restTemplate = new RestTemplate();
    }

    public void generateDatabaseRecords(Lender lender, Book book1, Book book2) {
        this.putBookLendingToLenderAndBook(lender, book1, new CreateBookLendingUnreturnedPreset());
        this.putBookLendingToLenderAndBook(lender, book2, new CreateBookLendingExpiredPreset());
        this.putLenderWithUpdatedBookLendingToDatabase(lender);
    }

    private void putLenderWithUpdatedBookLendingToDatabase(Lender lender){
        HttpEntity<Lender> request = new HttpEntity<>(lender);
        ResponseEntity<BookLending> response = restTemplate
                .exchange(URL_TO_LOAD_LENDERS, PUT, request, BookLending.class);
    }

    private void putBookLendingToLenderAndBook(Lender lender, Book book, LenderUpdatingStrategy updatingStrategy){
        updatingStrategy.addBookLending(lender,book);
    }

}
