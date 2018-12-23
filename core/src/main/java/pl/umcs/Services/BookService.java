package pl.umcs.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.basis.bookinfo.BookAvailability;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.Repositories.BookRepository;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //CRUD

    public Book save(Book book){
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    public Book find(Long id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return (foundBook.isPresent()) ? (foundBook.get()) : (null);
    }

    public List<Book> findAll(){
        Iterable<Book> iterableOfBooks = bookRepository.findAll();
        List<Book> listOfBooks = Lists.newArrayList(iterableOfBooks);
        return listOfBooks;
    }

    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    public Book updateBook(Book book){
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }

    //BUSINESS LOGIC

    public void disableBookFromLending(Long book_id){
        Book book = this.find(book_id);
        book.setBookAvailability(BookAvailability.UNAVAILABLE);
        this.save(book);
    }

    public void readForLending(Long book_id){
        Book book = this.find(book_id);
        book.setBookAvailability(BookAvailability.AVAILABLE_TO_READ_ONLY);
        this.save(book);
    }

    public void ableLending(Long book_id){
        Book book = this.find(book_id);
        book.setBookAvailability(BookAvailability.AVAILABLE_TO_BORROW);
        this.save(book);
    }

    public List<Book> findByAuthor(String author){
        List<Book> list = bookRepository.findByAuthor(author);
        return list;
    }

    public boolean isAvailableToLend(Long id){
        Book book = this.find(id);
        return book.getBookAvailability().equals(BookAvailability.AVAILABLE_TO_BORROW);
    }

    public int getBaseDaysToReturn(Long id){
        Book book = this.find(id);
        if(book.getBookAvailability().equals(BookAvailability.AVAILABLE_TO_BORROW)){
            switch(book.getBookRarity()){
                case COMMON:
                    return 14;
                case RARE:
                    return 7;
                case EXOTIC:
                    return 1;
            }
        }
        return 0;
    }
}
