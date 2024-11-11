package jjalsel.be_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BePlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(BePlaygroundApplication.class, args);
    }

}
