package com.espe.estudiantes.repositories;

import com.espe.estudiantes.model.entities.Estudiante;
import org.springframework.data.repository.CrudRepository;

public interface EstudianteRepository  extends CrudRepository<Estudiante, Long> {
}
