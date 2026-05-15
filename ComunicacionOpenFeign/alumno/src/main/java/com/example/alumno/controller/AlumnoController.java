package com.example.alumno.controller;


import com.example.alumno.entity.Alumno;
import com.example.alumno.repository.AlumnoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoRepository repository;
    
    @GetMapping
    public ResponseEntity<List<Alumno>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(alumno -> ResponseEntity.ok(alumno))
                .orElse(ResponseEntity.notFound().build());
        }

    @PostMapping
    public ResponseEntity<Alumno> crear(@Valid @RequestBody Alumno alumno) {
        Alumno alumnoCrear = repository.save(alumno);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alumnoCrear.getId())
                .toUri();
        return ResponseEntity.created(location).body(alumnoCrear);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}