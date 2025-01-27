    package com.espe.cursos.controllers;
    
    import com.espe.cursos.model.Estudiante;
    import com.espe.cursos.model.entities.Curso;
    import com.espe.cursos.services.CursoService;
    import feign.FeignException;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.util.List;
    import java.util.Optional;
    
    @RestController
    @RequestMapping("/api/cursos")
    public class CursoController {
    
        @Autowired
        private CursoService cursoService;
    
        @PostMapping
        public ResponseEntity<Curso> create(@RequestBody Curso curso) {
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
        }
    
        @GetMapping
        public List<Curso> getAll() {
            return cursoService.findAll();
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<Curso> getById(@PathVariable Long id) {
            Optional<Curso> cursoOptional = cursoService.findById(id);
            if (cursoOptional.isPresent()) {
                return ResponseEntity.ok(cursoOptional.get());
            }
            return ResponseEntity.notFound().build();
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Curso curso) {
            Optional<Curso> cursoOptional = cursoService.findById(id);
            if (cursoOptional.isPresent()) {
                Curso cursoDB = cursoOptional.get();
                cursoDB.setNombre(curso.getNombre());
                cursoDB.setDescripcion(curso.getDescripcion());
                cursoDB.setCreditos(curso.getCreditos());
                return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(cursoDB));
            }
            return ResponseEntity.notFound().build();
        }
    
        @DeleteMapping("/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id) {
            Optional<Curso> cursoOptional = cursoService.findById(id);
            if (cursoOptional.isPresent()) {
                cursoService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        }

        // ESTUDIANTE -> CURSO
        @PostMapping("/{id}")
        public ResponseEntity<?> agregarEstudiante(@RequestBody Estudiante estudiante, @PathVariable Long id) {
            Optional<Estudiante> estudianteOptional;
            try {
                estudianteOptional = cursoService.addStudent(estudiante, id);
            } catch (FeignException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
            }
            if (estudianteOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(estudianteOptional.get());
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/estudiantes")
        public ResponseEntity<List<Estudiante>> getStudentsByCourse(@PathVariable Long id) {
            List<Estudiante> estudiantes = cursoService.getStudentsByCourse(id);
            if (!estudiantes.isEmpty()) {
                return ResponseEntity.ok(estudiantes);
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/estudiantes/{estudianteId}/cursos")
        public ResponseEntity<List<Curso>> getCoursesByStudent(@PathVariable Long estudianteId) {
            List<Curso> cursos = cursoService.findCoursesByStudent(estudianteId);
            if (!cursos.isEmpty()) {
                return ResponseEntity.ok(cursos);
            }
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/{cursoId}/estudiantes/{estudianteId}")
        public ResponseEntity<?> removeStudent(@PathVariable Long cursoId, @PathVariable Long estudianteId) {
            boolean removed = cursoService.removeStudent(estudianteId, cursoId);
            if (removed) {
                return ResponseEntity.noContent().build();  // 204 No Content si se eliminó correctamente
            }
            return ResponseEntity.notFound().build();  // 404 Not Found si no se encontró el estudiante o el curso
        }
    }
