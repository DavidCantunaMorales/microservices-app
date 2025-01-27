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
    // Método para listar estudiantes de un curso
    List<Estudiante> getStudentsByCourse(Long cursoId);
    // Método para listar cursos de un estudiante
    List<Curso> findCoursesByStudent(Long estudianteId);
    // Método para revocar un estudiante
    boolean removeStudent(Long estudianteId, Long cursoId);
}
