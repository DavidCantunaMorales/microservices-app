package com.espe.cursos.repositories;

import com.espe.cursos.model.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> { }
