package com.espe.cursos.clients;

import com.espe.cursos.model.Estudiante;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estudiantes", url = "http://localhost:8003/api/estudiantes")
public interface EstudianteClientRest {
    @GetMapping("/{id}")
    Estudiante findById(@PathVariable Long id);
}
