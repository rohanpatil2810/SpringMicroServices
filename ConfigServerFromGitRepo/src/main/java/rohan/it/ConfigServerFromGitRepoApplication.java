package rohan.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerFromGitRepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerFromGitRepoApplication.class, args);
	}

}
