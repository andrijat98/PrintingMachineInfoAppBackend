package io.github.andrijat98.printingmachineinfoapp;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.github.andrijat98.printingmachineinfoapp.models.AppUser;
import io.github.andrijat98.printingmachineinfoapp.models.PrintingMachine;
import io.github.andrijat98.printingmachineinfoapp.models.Role;
import io.github.andrijat98.printingmachineinfoapp.repository.MachineRepository;
import io.github.andrijat98.printingmachineinfoapp.service.AppUserService;


@SpringBootApplication
public class PrintingMachineInfoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrintingMachineInfoAppApplication.class, args);
	}
	
	
	@Bean
	CommandLineRunner run(MachineRepository machineRepo, AppUserService userService) {
		return args -> {
			machineRepo.save(new PrintingMachine(null, "Heidelberg", "SM 102",
					"Ofset", "2", true, "720 x 1020 mm", "280 x 420 mm",
					"13000", "2 m", "2.5 m", "1 m", "lorem ipsum", null, null));
			machineRepo.save(new PrintingMachine(null, "KBA", "Rapida 106",
					"Duboka", "4", true, "750 x 1060 mm", "320 x 520 mm",
					"20000", "8 m", "2 m", "2.5 m", "lorem ipsum", null, null));
			machineRepo.save(new PrintingMachine(null, "Heidelberg", "S Cylinder",
					"Visoka", "1", false, "540 x 720 mm", "120 x 250 mm",
					"1200", "3.5 m", "2 m", "1.8 m", "lorem ipsum", null, null));
			machineRepo.save(new PrintingMachine(null, "Sakurai", "Maestro MS102A2 ",
					"Digitalna", "6", false, "720 x 1020 mm", "560 x 350 mm",
					"3300", "7 m", "2.5 m", "2.3 m", "lorem ipsum", null, null));
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			
			userService.saveUser(new AppUser(null, "Andrija Tomic", "andrijat98", "1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Pepe The Frog", "pepe", "1234", new ArrayList<>()));
			
			userService.addRoleToUser("andrijat98", "ROLE_ADMIN");
			userService.addRoleToUser("pepe", "ROLE_USER");
		};
	}
	/*
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers","No-auth"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}*/
}
