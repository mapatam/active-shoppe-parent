package za.co.momentum.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class SwaggerConfigurer {

	@Autowired(required = false)
	private SecurityScheme securityScheme;

	@Autowired(required = false)
	private SecurityContext securityContext;

	@Profile("active-shoppe-api")
	@Bean
	public Docket momentumApiDocket() {


		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.enable(true)
				.groupName("Momentum")
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Controller.class))
				.paths(PathSelectors.ant("/momentum/api/**"))
				.build()
				.useDefaultResponseMessages(false)
				.apiInfo(new ApiInfoBuilder().description("Active Shoppe API").build())
				.produces(new HashSet<String>(
						Arrays.asList("application/xml")
				))
				.consumes(new HashSet<String>(
						Arrays.asList("*/*")
				));

		return docket;
	}
}
