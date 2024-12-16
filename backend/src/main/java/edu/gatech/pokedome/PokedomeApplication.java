package edu.gatech.pokedome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.gatech.pokedome.infrastructure.BeanProvider;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3001")
@Import({ BeanProvider.class })
public class PokedomeApplication {
    public static void main(final String[] args) {
        SpringApplication.run(PokedomeApplication.class, args);
    }

    // This enables all origins to call the APIs we expose in this app.
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
