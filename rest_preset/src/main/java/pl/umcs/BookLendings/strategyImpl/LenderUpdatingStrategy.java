package pl.umcs.BookLendings.strategyImpl;

import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.lenderinfo.Lender;

public interface LenderUpdatingStrategy {
    public void addBookLending(Lender lender, Book book);
}
