package com.espe.cursos.model.entities;
import com.espe.cursos.model.Estudiante;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int creditos;

    // RELACION CURSO -> ESTUDIANTE
    @OneToMany
    @JoinColumn(name = "curso_id")
    private List<CursoEstudiante> cursoEstudiantes = new ArrayList<>();

    @Transient
    private List<Estudiante> estudiantes = new ArrayList<>();

    public void addCursoEstudiante(CursoEstudiante cursoEstudiante) {
        cursoEstudiantes.add(cursoEstudiante);
    }

    public void removeCursoEstudiante(CursoEstudiante cursoEstudiante) {
        cursoEstudiantes.remove(cursoEstudiante);
    }

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
}
