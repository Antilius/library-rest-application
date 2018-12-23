package pl.umcs.BookLendings.strategyImpl;

import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.booklendinginfo.BookLending;
import pl.umcs.basis.lenderinfo.Lender;

import java.time.Instant;
import java.util.Date;

public class CreateBookLendingUnreturnedPreset implements LenderUpdatingStrategy {

    @Override
    public void addBookLending(Lender lender, Book book) {
        BookLending lending1 = new BookLending();
        lending1.setLendStart(Date.from(Instant.now()));
        lending1.setExpirationDate((Date.from(Instant.now().plusSeconds(3600*24))));
        lending1.setBook(book);
        lender.addLending(lending1);
    }
}
