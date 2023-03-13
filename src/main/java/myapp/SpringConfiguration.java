package myapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des services logiciels déployés par Spring
 */
@Configuration
public class SpringConfiguration {
	
	@Bean
	public String bye() {
		return "Bye.";
	}

}

