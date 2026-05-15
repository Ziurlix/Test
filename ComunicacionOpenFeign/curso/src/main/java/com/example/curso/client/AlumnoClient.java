package com.example.curso.client;

import com.example.curso.dto.AlumnoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "alumno-service", url = "http://localhost:8081")
public interface AlumnoClient {
    
    @GetMapping("/alumnos/{id}")
    AlumnoDTO obtenerAlumnoPorId(@PathVariable("id") Long id);


}