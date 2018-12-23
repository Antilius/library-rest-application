package pl.umcs;

import pl.umcs.BookLendings.BookLendingGenerator;
import pl.umcs.Lenders.LenderGenerator;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.lenderinfo.Lender;

import java.util.List;

public class PresetLoaderApplication
{
    public static void main( String[] args )
    {
        BookGenerator bookGenerator = new BookGenerator();
        LenderGenerator lenderGenerator = new LenderGenerator();
        List<Book> books = bookGenerator.generateDatabaseRecords();
        List<Lender> lenders = lenderGenerator.generateDatabaseRecords();
        BookLendingGenerator bookLendingGenerator = new BookLendingGenerator();
        bookLendingGenerator.generateDatabaseRecords(lenders.get(0), books.get(0), books.get(2));
        bookLendingGenerator.generateDatabaseRecords(lenders.get(1), books.get(1), books.get(3));
    }
}
