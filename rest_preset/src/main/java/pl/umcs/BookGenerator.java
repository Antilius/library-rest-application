package pl.umcs;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.umcs.basis.bookinfo.Book;
import pl.umcs.basis.bookinfo.BookAvailability;
import pl.umcs.basis.bookinfo.BookRarity;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

public class BookGenerator{
    public static final String URL_TO_LOAD = "http://localhost:8081/books";
    private RestTemplate restTemplate;

    public BookGenerator() {
        this.restTemplate = new RestTemplate();
    }

    public List<Book> generateDatabaseRecords(){
        List<Book> list = new LinkedList<Book>();
        list.add(this.addBookToDatabase(this.createKubusPuchatek()));
        list.add(this.addBookToDatabase(this.createHarryPotter()));
        list.add(this.addBookToDatabase(this.createEragon()));
        list.add(this.addBookToDatabase(this.createAlicjaWKrainieCzarow()));
        list.add(this.addBookToDatabase(this.createPotopPierwszyTom()));
        list.add(this.addBookToDatabase(this.createPotopPierwszyTomDrugi()));
        list.add(this.addBookToDatabase(this.createPotopDrugiTom()));
        list.add(this.addBookToDatabase(this.createPotopTrzeciTom()));
        list.add(this.addBookToDatabase(this.createBialyKruk()));
        return list;
    }

    private Book addBookToDatabase(Book book){
        HttpEntity<Book> request = new HttpEntity<>(book);
        ResponseEntity<Book> response = restTemplate
                .exchange(URL_TO_LOAD, POST, request, Book.class);
        System.out.println("Utworzono: " + response.getBody().getTitle());
        return book;
    }

    private Book createKubusPuchatek(){
        System.out.println("Tworzę Kubusia Puchatka");
        return new Book(1l,"Kubus Puchatek i przyjaciele", "A-A-Milne",176, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.COMMON);
    }

    private Book createHarryPotter(){
        System.out.println("Tworzę Harrego Pottera");
        return new Book(2l,"Harry Potter", "J-K-Rowling",219, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.EXOTIC);
    }

    private Book createEragon(){
        System.out.println("Tworzę Eragona");
        return new Book(3l,"Eragon", "Ch-Paolini",432, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.COMMON);
    }

    private Book createAlicjaWKrainieCzarow(){
        System.out.println("Tworzę Alice w krainie czarow");
        return new Book(4l,"Alicja w krainie czarow", "Grimm",111, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.RARE);
    }

    private Book createPotopPierwszyTom(){
        System.out.println("Tworzę Potop: tom I");
        return new Book(5l,"Potop: tom I", "H-Sienkiewicz",1387, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.EXOTIC);
    }

    private Book createPotopPierwszyTomDrugi(){
        System.out.println("Tworzę Potop: tom I");
        return new Book(6l,"Potop: tom I", "H-Sienkiewicz",1387, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.EXOTIC);
    }

    private Book createPotopDrugiTom(){
        System.out.println("Tworzę Potop: tom II");
        return new Book(7l,"Potop: tom II", "H-Sienkiewicz",1198, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.EXOTIC);
    }

    private Book createPotopTrzeciTom(){
        System.out.println("Potop: tom III");
        return new Book(8l,"Potop: tom III", "H-Sienkiewicz",1421, BookAvailability.AVAILABLE_TO_BORROW, BookRarity.EXOTIC);
    }

    private Book createBialyKruk(){
        System.out.println("Tworzę bialego kruka");
        return new Book(9l,"Pies, ktory jezdzil koleja", "R-Pisarski",1198, BookAvailability.AVAILABLE_TO_READ_ONLY, BookRarity.EXOTIC);
    }

}
