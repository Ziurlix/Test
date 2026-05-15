package com.example.curso.dto;

import java.time.LocalDateTime;

public record ErrorDTO (
        String mensaje,
        int codigo,
        LocalDateTime timestamp
) { }
