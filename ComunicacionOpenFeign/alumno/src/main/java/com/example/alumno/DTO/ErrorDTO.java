package com.example.alumno.DTO;

import javax.swing.*;
import java.time.LocalDateTime;

public record ErrorDTO (
    String mensaje,
    int codigo,
    LocalDateTime timestamp
) {}
