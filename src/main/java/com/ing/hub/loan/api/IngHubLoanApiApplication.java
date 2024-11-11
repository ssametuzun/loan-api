package com.ing.hub.loan.api;


import com.ing.hub.loan.api.model.request.SignupRequest;
import com.ing.hub.loan.api.model.response.SignUpModel;
import com.ing.hub.loan.api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class IngHubLoanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngHubLoanApiApplication.class, args);
    }

    @Bean
    CommandLineRunner CommandLineRunner(AuthService authService) {
        return runner -> {
            SignupRequest adminRequest = new SignupRequest();
            adminRequest.setUsername("admin");
            adminRequest.setPassword("pass");
            adminRequest.setAdmin(true);
            SignUpModel adminSignupModel = authService.signUp(adminRequest);
            log.info("Admin Signup: {}", adminSignupModel);
        };
    }
}
