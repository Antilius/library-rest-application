package pl.umcs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umcs.Services.BookLendingService;
import pl.umcs.basis.booklendinginfo.BookLending;

import java.util.List;

@RestController
@RequestMapping("/book-lendings")
public class BookLendingsController {
    private final BookLendingService bookLendingService;

    @Autowired
    public BookLendingsController(BookLendingService bookLendingService){
        this.bookLendingService = bookLendingService;
    }

    //api.crud

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLending> getBookLendings() {
        return bookLendingService.findAll();
    }

    @GetMapping("/{id}")
    public BookLending find(@PathVariable Long id) {
        return bookLendingService.find(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBookLendingById(@PathVariable Long id) {
        bookLendingService.deleteBookLendingById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookLending update(@RequestBody BookLending bookLending) {
        return bookLendingService.updateBookLending(bookLending);
    }
    //api.business.logic

    @PostMapping("/who/{lender_id}/what/{book_id}")
    public void lendBook(@PathVariable Long lender_id, @PathVariable Long book_id){
        bookLendingService.lendBook(lender_id,book_id);
    }

    @PutMapping("/book/{id}/return")
    public ResponseEntity returnBook(@PathVariable Long id){
        bookLendingService.returnBook(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLending> findByBook(@PathVariable Long id){
        return bookLendingService.findByBookId(id);
    }

    @GetMapping("/{id}/is-returned")
    public boolean isBookLendingReturned(@PathVariable Long id){
        return bookLendingService.isBookLendingReturned(id);
    }
}
