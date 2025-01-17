package com.espe.cursos.services;

import com.espe.cursos.clients.EstudianteClientRest;
import com.espe.cursos.model.Estudiante;
import com.espe.cursos.model.entities.Curso;
import com.espe.cursos.model.entities.CursoEstudiante;
import com.espe.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImp implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private EstudianteClientRest clientRest;

    @Override
    public List<Curso> findAll() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

    // AÑADIR UN ESTUDIANTE A UN CURSO
    @Override
    public Optional<Estudiante> addStudent(Estudiante estudiante, Long cursoId) {
        Optional<Curso> optionalCurso = cursoRepository.findById(cursoId);
        if (optionalCurso.isPresent()) {
            Estudiante estudianteTemp = clientRest.findById(estudiante.getId());
            Curso curso = optionalCurso.get();
            CursoEstudiante cursoEstudiante = new CursoEstudiante();
            cursoEstudiante.setEstudianteId(estudianteTemp.getId());
            curso.addCursoEstudiante(cursoEstudiante);
            cursoRepository.save(curso);
            return Optional.of(estudianteTemp);
        }
        return Optional.empty();
    }
}
