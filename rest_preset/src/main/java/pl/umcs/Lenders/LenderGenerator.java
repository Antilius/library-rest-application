package pl.umcs.Lenders;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.umcs.Lenders.strategyImpl.CreateEmiliaNowak;
import pl.umcs.Lenders.strategyImpl.CreateJanKowalski;
import pl.umcs.Lenders.strategyImpl.LenderRecordCreator;
import pl.umcs.basis.lenderinfo.Lender;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

public class LenderGenerator{
    public static final String URL_TO_LOAD = "http://localhost:8081/lenders";
    private RestTemplate restTemplate;

    public LenderGenerator() {
        this.restTemplate = new RestTemplate();
    }

    public List<Lender> generateDatabaseRecords(){
        List<Lender> list = new ArrayList<Lender>();
        list.add(addLenderToDatabase(new CreateJanKowalski()));
        list.add(addLenderToDatabase(new CreateEmiliaNowak()));
        return list;
    }

    private Lender addLenderToDatabase(LenderRecordCreator lenderRecordCreator){
        Lender lender = lenderRecordCreator.createLender();
        HttpEntity<Lender> request = new HttpEntity<>(lender);
        ResponseEntity<Lender> response = restTemplate
                .exchange(URL_TO_LOAD, POST, request, Lender.class);
        return lender;
    }

}
