package za.co.momentum.service.spring;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.momentum.service.data.DataPopulator;

import java.util.List;

@Configuration
public class SpringBeans {

	@Bean
	public ApplicationRunner defaultStoreDataRunner(DataPopulator populators) {
		return args -> populators.populate();
	}
}
