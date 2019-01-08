package pl.umcs;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/penalties")
public class PenaltiesController {

    @Autowired
    private EurekaClient eurekaClient;

    public InstanceInfo getInstanceInfoFromEurekaClient(){
        Application application = eurekaClient.getApplication("library");
        List<InstanceInfo> instances = application.getInstances();
        InstanceInfo instanceInfo = instances.iterator().next();
        return instanceInfo;
    }

    public String getHostname(){
        return this.getInstanceInfoFromEurekaClient().getHostName();
    }

    public int getPort(){
        return this.getInstanceInfoFromEurekaClient().getPort();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{lender_id}")
    public int determinePenaltyInPLN(@PathVariable Long lender_id){
        String days = restTemplate.getForObject("http://" + this.getHostname() + ":" + this.getPort() + "/lenders/" + lender_id.toString() + "/days-of-late-with-return", String.class);
        return 2*Integer.parseInt(days);
    }

    @PostMapping("/{lender_id}/charge")
    public void chargeLender(@PathVariable Long lender_id){
        Integer amount = determinePenaltyInPLN(lender_id);
        HttpEntity<Integer> request = new HttpEntity<>(amount);
        restTemplate.exchange("http://" + this.getHostname() + ":" + this.getPort() + "/lenders/" + lender_id.toString() + "/charge", HttpMethod.PUT, request, String.class);
    }
}
