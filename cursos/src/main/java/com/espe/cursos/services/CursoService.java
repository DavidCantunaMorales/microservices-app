package com.espe.cursos.services;

import com.espe.cursos.model.Estudiante;
import com.espe.cursos.model.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);

    Optional<Estudiante> addStudent(Estudiante estudiante, Long cursoId);
}
