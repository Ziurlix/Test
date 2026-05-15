package com.example.curso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients  
public class CursoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CursoApplication.class, args);
    }
}

/* 

PS C:\Users\al-Docente> Invoke-RestMethod -Uri "http://localhost:8081/alumnos" -Method Post -ContentType "application/json" -Body '{"nombre":"Ana Perez","email":"ana@email.com"}'

id nombre    email
-- ------    -----
 1 Ana Perez ana@email.com


PS C:\Users\al-Docente> Invoke-RestMethod -Uri "http://localhost:8082/cursos" -Method Post -ContentType "application/json" -Body '{"nombre":"Spring Cloud Feign","alumnoId":1}'

id nombre             alumnoId
-- ------             --------
 1 Spring Cloud Feign        1


PS C:\Users\al-Docente> Invoke-RestMethod -Uri "http://localhost:8082/cursos/1/completo"

cursoId cursoNombre        alumno
------- -----------        ------
      1 Spring Cloud Feign @{id=1; nombre=Ana Perez; email=ana@email.com}

      */