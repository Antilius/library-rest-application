package pl.umcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LibraryAppEurekaServer
{
    public static void main( String[] args )
    {
        SpringApplication.run(LibraryAppEurekaServer.class, args);
    }
}
