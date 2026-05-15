package com.example.curso.controller;

import com.example.curso.client.AlumnoClient;
import com.example.curso.dto.AlumnoDTO;
import com.example.curso.dto.CursoConAlumnoDTO;
import com.example.curso.entity.Curso;
import com.example.curso.repository.CursoRepository;
import com.example.curso.service.CursoService;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curso")
public class CursoController {
    
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private AlumnoClient alumnoClient;
    
    @Autowired
    private CursoService cursoService;
    
    @GetMapping
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscar (@PathVariable Long id){
        return cursoRepository.findById(id)
                .map(alumno -> ResponseEntity.ok(alumno))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@Valid @RequestBody Curso curso) {
        Curso cursoCrear = cursoRepository.save(curso);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cursoCrear.getId())
                .toUri();
        return ResponseEntity.created(location).body(cursoCrear);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id) {
        if (!cursoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<CursoConAlumnoDTO> obtenerDetalleCurso(@PathVariable Long id) {
        Optional<Curso> cursoOpt = cursoRepository.findById(id);

        if (cursoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Curso curso = cursoOpt.get();
        AlumnoDTO alumnoDTO = alumnoClient.obtenerAlumnoPorId(curso.getAlumnoId());

        CursoConAlumnoDTO detalle = new CursoConAlumnoDTO();
        detalle.setCursoId(curso.getId());
        detalle.setCursoNombre(curso.getNombre());
        detalle.setAlumno(alumnoDTO);

        return ResponseEntity.ok(detalle);
    }
    
    // Endpoint que usa OpenFeign
    @GetMapping("/{id}/completo")
    public CursoConAlumnoDTO obtenerCursoCompleto(@PathVariable Long id) {
        return cursoService.obtenerCursoConAlumno(id);
    }
}