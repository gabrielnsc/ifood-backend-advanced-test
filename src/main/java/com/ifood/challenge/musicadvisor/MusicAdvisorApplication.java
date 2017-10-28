package com.ifood.challenge.musicadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This is the main class of the project, it is used to start up the application
 * 
 * @author gabrielnascimento
 */
@SpringBootApplication
public class MusicAdvisorApplication extends WebMvcConfigurerAdapter {
	/**
	 * Main method
	 * <p>
	 * This method is used to start up the application
	 * 
	 * @param args an array of arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MusicAdvisorApplication.class, args);
	}

	/**
	 * Declaring the static files folder as the /doc
	 *
	 * @param registry the view controller register
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/doc").setViewName("redirect:/doc/");
		registry.addViewController("/doc/").setViewName("forward:/doc/index.html");
		super.addViewControllers(registry);
	}

	/**
	 * Declaring the Rest Template bean to be used on the network layers
	 * <p>
	 * We should declare this bean because we must auto wire it on the network
	 * layers for test purposes
	 * 
	 * @param builder the rest template builder
	 * @return a new instance of the rest template
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		// Do any additional configuration here
		return builder.build();
	}
}
