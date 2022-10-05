package io.github.andrijat98.printingmachineinfoapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
		.allowedMethods("*")
		.allowedHeaders("*")
		.allowedOriginPatterns("*")
		.allowedOriginPatterns("*")
		.allowCredentials(true);
		//registry.addMapping("/login");
		//registry.addMapping("/machines/all");
		//registry.addMapping("/user/getuser/*");
		WebMvcConfigurer.super.addCorsMappings(registry);	
	}
}
