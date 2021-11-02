package cf.beishan.micropostapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cf.beishan.micropostapi.dao")
public class MicropostApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicropostApiApplication.class, args);
	}

}
