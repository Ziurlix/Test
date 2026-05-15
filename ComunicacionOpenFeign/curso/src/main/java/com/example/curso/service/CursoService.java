package com.example.curso.service;

import com.example.curso.client.AlumnoClient;
import com.example.curso.dto.AlumnoDTO;
import com.example.curso.dto.CursoConAlumnoDTO;
import com.example.curso.entity.Curso;
import com.example.curso.repository.CursoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private AlumnoClient alumnoClient;  // Inyectamos el cliente Feign
    
    public CursoConAlumnoDTO obtenerCursoConAlumno(Long cursoId) {
        // 1. Buscar el curso
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        
        // 2. Llamar a alumno-service usando OpenFeign (más simple que WebClient)
        AlumnoDTO alumno = alumnoClient.obtenerAlumnoPorId(curso.getAlumnoId());
        
        // 3. Combinar resultados
        return new CursoConAlumnoDTO(
            curso.getId(),
            curso.getNombre(),
            alumno
        );
    }
    
    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }
    
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }
}