package jjalsel.be_playground;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BePlaygroundApplication {

    public static void main(String[] args) {

        // .env 파일 로드
        Dotenv dotenv = Dotenv.configure()
                .directory("./")  // .env 파일 경로
                .load();

        // 환경 변수를 시스템 속성에 설정
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        SpringApplication.run(BePlaygroundApplication.class, args);
    }

}
