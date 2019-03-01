package com.example.restapi.controllers;

import com.example.restapi.entities.Doctor;
import com.example.restapi.repositories.DoctorRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/doctors")
    Resources<Resource<Doctor>> getAllDoctors() {
        List doctors = doctorRepository.findAll().stream()
                .map(doctor -> new Resource(doctor,
                        linkTo(methodOn(DoctorController.class).getDoctor(doctor.getId())).withSelfRel(),
                        linkTo(methodOn(DoctorController.class).getAllDoctors()).withRel("doctors"))
                 )
                .collect(Collectors.toList());
        return new Resources<>(doctors,
                linkTo(methodOn(DoctorController.class).getAllDoctors()).withSelfRel());
    }

    @PostMapping("/doctors")
    Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @GetMapping("/doctors/{id}")
    Resource<Doctor> getDoctor(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException(id));
        return new Resource<>(doctor,
                linkTo(methodOn(DoctorController.class).getDoctor(id)).withSelfRel(),
                linkTo(methodOn(DoctorController.class).getAllDoctors()).withRel("doctors"));
    }

    @PutMapping("/doctors/{id}")
    private Doctor replaceDoctor(@RequestBody Doctor newDoctor, @PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                   doctor.setName(newDoctor.getName());
                   doctor.setSpecialization(newDoctor.getSpecialization());
                   return doctorRepository.save(doctor);
                })
                .orElseGet(
                        () -> {
                            newDoctor.setId(id);
                            return doctorRepository.save(newDoctor);
                        }
                );
    }

    @DeleteMapping("/doctors/{id}")
    void deleteDoctor(@PathVariable Long id){
        doctorRepository.deleteById(id);
    }
}


