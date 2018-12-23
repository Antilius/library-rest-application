package pl.umcs.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umcs.Services.LenderService;
import pl.umcs.basis.booklendinginfo.BookLending;
import pl.umcs.basis.lenderinfo.Lender;

import java.util.List;

@RestController
@RequestMapping("/lenders")
public class LendersController {

    private final LenderService lenderService;

    @Autowired
    public LendersController(LenderService lenderService){
        this.lenderService = lenderService;
    }

    //api.crud

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lender> getLenders() {
        return lenderService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Lender save(@RequestBody Lender lender) {
        return lenderService.save(lender);
    }

    @GetMapping("/{id}")
    public Lender find(@PathVariable Long id) {
        return lenderService.find(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLenderById(@PathVariable Long id) {
        lenderService.deleteLenderById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Lender update(@RequestBody Lender lender) {
        return lenderService.updateLender(lender);
    }

    //api.business.logic

    @GetMapping(value = "/{id}/untimely-returns", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookLending> getUntimelyReturnsForLender(@PathVariable Long id){
        return lenderService.untimelyReturns(id);
    }

    @GetMapping("/{id}/distrust-level")
    public int getDistrustLevelForLender(@PathVariable Long id){
        return lenderService.getDistrustLevel(id);
    }

    @GetMapping("/{id}/get-book-lendings")
    public List<BookLending> getAllBookLendingsForLender(@PathVariable Long id){
        return lenderService.findAllBooklendingsForLender(id);
    }

    @GetMapping("/{id}/days-of-late-with-return")
    public int getAllExpiredDaysForLender(@PathVariable Long id){
        return lenderService.getDaysOfLateWithReturnBookLendingsForLender(id);
    }

    @GetMapping("/{id}/penalty")
    public int getPenaltyINPLN(@PathVariable Long id){
        return lenderService.getPenaltyINPLN(id);
    }

    @PutMapping(value = "/{id}/charge", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPenaltyINPLN(@PathVariable Long id, @RequestBody Integer amount){
        lenderService.addPenaltyInPLN(id,amount);
    }
}
