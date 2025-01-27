package com.espe.cursos.services;

import com.espe.cursos.clients.EstudianteClientRest;
import com.espe.cursos.model.Estudiante;
import com.espe.cursos.model.entities.Curso;
import com.espe.cursos.model.entities.CursoEstudiante;
import com.espe.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Optional<Curso> optional = cursoRepository.findById(cursoId);
        if (optional.isPresent()) {
            Estudiante estudianteTemp = clientRest.findById(estudiante.getId());
            Curso cursoTemp = optional.get();
            CursoEstudiante cursoEstudiante = new CursoEstudiante();
            cursoEstudiante.setEstudianteId(estudianteTemp.getId());
            cursoTemp.addCursoEstudiante(cursoEstudiante);
            cursoRepository.save(cursoTemp);
            return Optional.of(estudianteTemp);
        }
        return Optional.empty();
    }

    @Override
    public List<Estudiante> getStudentsByCourse(Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        List<Estudiante> estudiantes = new ArrayList<>();
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            for (CursoEstudiante cursoEstudiante : curso.getCursoEstudiantes()) {
                Estudiante estudiante = clientRest.findById(cursoEstudiante.getEstudianteId());
                estudiantes.add(estudiante);
            }
        }
        return estudiantes;
    }

    @Override
    public List<Curso> findCoursesByStudent(Long estudianteId) {
        List<Curso> cursos = new ArrayList<>();
        List<Curso> allCursos = (List<Curso>) cursoRepository.findAll();
        for (Curso curso : allCursos) {
            for (CursoEstudiante cursoEstudiante : curso.getCursoEstudiantes()) {
                if (cursoEstudiante.getEstudianteId().equals(estudianteId)) {
                    cursos.add(curso);
                    break;
                }
            }
        }
        return cursos;
    }

    @Override
    public boolean removeStudent(Long estudianteId, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            for (CursoEstudiante cursoEstudiante : curso.getCursoEstudiantes()) {
                if (cursoEstudiante.getEstudianteId().equals(estudianteId)) {
                    curso.removeCursoEstudiante(cursoEstudiante);
                    cursoRepository.save(curso);  // Guardamos los cambios en el curso
                    return true;  // Devolvemos true si se eliminó correctamente
                }
            }
        }
        return false;  // Devolvemos false si no se encontró el estudiante o el curso
    }
}
