package mttsner.fujitsu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FujitsuApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FujitsuApplication.class, args);
    }

}
