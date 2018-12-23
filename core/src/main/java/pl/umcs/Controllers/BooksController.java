package pl.umcs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.Services.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService){
        this.bookService = bookService;
    }

    //api.crud

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping("/{id}")
    public Book find(@PathVariable Long id) {
        return bookService.find(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Book update(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    //api.business.logic

    @PutMapping("/{id}/change-avail/off")
    public void disableFromLending(@PathVariable Long id){
        bookService.disableBookFromLending(id);
    }

    @PutMapping("/{id}/change-avail/read")
    public void readForLending(@PathVariable Long id){
        bookService.readForLending(id);
    }

    @PutMapping("/{id}/change-avail/on")
    public void ableLending(@PathVariable Long id){
        bookService.ableLending(id);
    }

    @GetMapping("/{id}/base-days")
    public int getBaseDaysToReturn(@PathVariable Long id) {
        return bookService.getBaseDaysToReturn(id);
    }

    @GetMapping(value = "/filter/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooksByAuthor(@PathVariable String author){
        return bookService.findByAuthor(author);
    }
}
