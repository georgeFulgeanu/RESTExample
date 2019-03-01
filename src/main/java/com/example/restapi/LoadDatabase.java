package com.example.restapi;

import com.example.restapi.entities.Doctor;
import com.example.restapi.repositories.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(DoctorRepository doctorRepository){
        return args -> {
            log.info(" Preloading " + doctorRepository.save(new Doctor("Roxana Stanciu", "gastroenterologist")));
            log.info(" Preloading " + doctorRepository.save(new Doctor("Anita Mezei", "gastroenterologist")));

        };
    }
}
