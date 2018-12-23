package pl.umcs.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.basis.bookinfo.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Long> {

    List<Book> findByAuthor(String author);
}
