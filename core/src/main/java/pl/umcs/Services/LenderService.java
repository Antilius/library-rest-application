package pl.umcs.Services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.umcs.Repositories.LenderRepository;
import pl.umcs.basis.booklendinginfo.BookLending;
import pl.umcs.basis.lenderinfo.Lender;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LenderService {
    private final LenderRepository lenderRepository;
    private final BookService bookService;

    @Autowired
    public LenderService(LenderRepository lenderRepository, BookService bookService) {
        this.lenderRepository = lenderRepository;
        this.bookService = bookService;
    }

    //CRUD

    public Lender save(Lender lender){
        Lender savedLender = lenderRepository.save(lender);
        return savedLender;
    }

    public Lender find(Long id){
        Optional<Lender> foundLender = lenderRepository.findById(id);
        return (foundLender.isPresent()) ? (foundLender.get()) : (null);
    }

    public List<Lender> findAll(){
        Iterable<Lender> iterableOfLenders = lenderRepository.findAll();
        List<Lender> listOfLenders = Lists.newArrayList(iterableOfLenders);
        return listOfLenders;
    }

    public void deleteLenderById(Long id){
        lenderRepository.deleteById(id);
    }

    public Lender updateLender(Lender lender){
        Lender updatedLender = lenderRepository.save(lender);
        return updatedLender;
    }

    //BUSSINESS LOGIC

    public List<BookLending> findAllBooklendingsForLender(Long lender_id){
        return this.find(lender_id).getBookLendings();
    }

    public int getPenaltyINPLN(Long id){
        return this.find(id).getPenalty();
    }

    public void addPenaltyInPLN(Long id, int amount){
        Lender lender = this.find(id);
        lender.setPenalty(amount);
        this.save(lender);

    }

    public int getDaysOfLateWithReturnBookLendingsForLender(Long lender_id){
        int total = 0;
        for(BookLending lending:this.findAllBooklendingsForLender(lender_id)){
            if(lending.isReturned()){
                if(lending.getExpirationDate().before(lending.getReturned())){
                    long difference = lending.getReturned().getTime()-lending.getExpirationDate().getTime();
                    int days = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                    total+=days;
                }
            }
            else{
                if(lending.getExpirationDate().before(Date.from(Instant.now()))){
                    long difference = Date.from(Instant.now()).getTime()-lending.getExpirationDate().getTime();
                    int days = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                    total+=days;
                }
            }
        }
        return total;
    }

    public boolean canLendBook(Long lender_id, Long book_id){
        return (!isLimitOfActiveLendsFullyUsed(lender_id) && !isUnreturnedExpiredLendActive(lender_id) && bookService.isAvailableToLend(book_id));
    }

    private boolean isUnreturnedExpiredLendActive(Long lender_id) {
        for(BookLending lending: this.find(lender_id).getBookLendings()){
            if(lending.getExpirationDate().before(Date.from(Instant.now())) && lending.getReturned() == null){
                return true;
            }
        }
        return false;
    }

    private boolean isLimitOfActiveLendsFullyUsed(Long lender_id) {
        int counter = 5;
        for(BookLending lending: this.find(lender_id).getBookLendings()){
            if(lending.getExpirationDate().after(Date.from(Instant.now())) && lending.getReturned() == null){
                counter--;
            }
        }
        return (counter==0);
    }

    public List<BookLending> untimelyReturns(Long lender_id){
        List<BookLending> list = new ArrayList<BookLending>();
        for(BookLending lending: this.find(lender_id).getBookLendings()){
            if(lending.getReturned()!=null) {
                if (lending.getExpirationDate().before(lending.getReturned())) {
                    list.add(lending);
                }
            }
        }
        return list;
    }

    public int untimelyReturnsCount(Long lender_id){
        int counter = 0;
        for(BookLending borrow: this.untimelyReturns(lender_id)){
            counter++;
        }
        return counter;
    }

    private void addLending(Long lender_id, BookLending lending){
        this.find(lender_id).addLending(lending);
    }

    public int getDistrustLevel(Long lender_id){
        int sum = this.untimelyReturnsCount(lender_id);
        if(sum>=5){
            return 4;
        }
        else{
            if(sum>=1){
                return 2;
            }
            else{
                return 1;
            }
        }
    }

}
