package com.example.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoConAlumnoDTO {
    private Long cursoId;
    private String cursoNombre;
    private AlumnoDTO alumno;
}