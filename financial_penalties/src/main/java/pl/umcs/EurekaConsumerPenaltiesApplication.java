package pl.umcs;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
@RequestMapping("/penalties")
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class EurekaConsumerPenaltiesApplication {

    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{lender_id}")
    public int determinePenaltyInPLN(@PathVariable Long lender_id){
        Application application = eurekaClient.getApplication("library");
        List<InstanceInfo> instances = application.getInstances();
        InstanceInfo instanceInfo = instances.iterator().next();
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
        String days = restTemplate.getForObject("http://" + hostname + ":" + port + "/lenders/" + lender_id.toString() + "/days-of-late-with-return", String.class);
        return 2*Integer.parseInt(days);
    }

    @PostMapping("/{lender_id}/charge")
    public void chargeLender(@PathVariable Long lender_id){
        Application application = eurekaClient.getApplication("library");
        List<InstanceInfo> instances = application.getInstances();
        InstanceInfo instanceInfo = instances.iterator().next();
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
        Integer amount = determinePenaltyInPLN(lender_id);
        HttpEntity<Integer> request = new HttpEntity<>(amount);
        restTemplate.exchange("http://" + hostname + ":" + port + "/lenders/" + lender_id.toString() + "/charge", HttpMethod.PUT, request, String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerPenaltiesApplication.class, args);
    }
}

