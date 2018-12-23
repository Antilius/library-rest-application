package pl.umcs.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.booklendinginfo.BookLending;

import java.util.List;

@Repository
public interface BookLendingRepository extends CrudRepository<BookLending,Long> {

    List<BookLending> findByBook(Book book);
}
