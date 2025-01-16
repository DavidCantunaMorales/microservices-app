package com.espe.estudiantes.controllers;

import com.espe.estudiantes.model.entities.Estudiante;
import com.espe.estudiantes.services.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<Estudiante> create(@RequestBody Estudiante curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.save(curso));
    }

    @GetMapping
    public List<Estudiante> getAll() {
        return estudianteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getById(@PathVariable Long id) {
        Optional<Estudiante> estudianteOptional = estudianteService.findById(id);
        if (estudianteOptional.isPresent()) {
            return ResponseEntity.ok(estudianteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Estudiante estudiante) {
        Optional<Estudiante> estudianteOptional = estudianteService.findById(id);
        if (estudianteOptional.isPresent()) {
            Estudiante estudianteDB = estudianteOptional.get();
            estudianteDB.setNombre(estudiante.getNombre());
            estudianteDB.setApellido(estudiante.getApellido());
            estudianteDB.setEmail(estudiante.getEmail());
            estudianteDB.setEdad(estudiante.getEdad());
            return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.save(estudianteDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Estudiante> estudianteOptional = estudianteService.findById(id);
        if (estudianteOptional.isPresent()) {
            estudianteService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
