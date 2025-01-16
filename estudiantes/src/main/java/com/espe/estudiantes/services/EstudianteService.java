package com.espe.estudiantes.services;

import com.espe.estudiantes.model.entities.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteService {
    List<Estudiante> findAll();
    Optional<Estudiante> findById(Long id);
    Estudiante save(Estudiante estudiante);
    void deleteById(Long id);
}
