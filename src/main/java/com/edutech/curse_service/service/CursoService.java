package com.edutech.curse_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edutech.curse_service.model.Curso;
import com.edutech.curse_service.repository.CursoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service

public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http:locahost:8082/profesores";

    public List<Curso>findCursosByIdProfesor(Long idProfesor) {
        try {
            String url = USER_SERVICE_URL + idProfesor;
            restTemplate.getForObject(url,Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Profesor con ID " + idProfesor + "No encontrado en el microservicio de usuario");
        }
            List<Curso> cursos = cursoRepository.findByIdProfesor(idProfesor);
        if (cursos.isEmpty()) {
        throw new RuntimeException("No se encontraron cursos para el profesor con ID" + idProfesor);
        }
        return cursos;
    }

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso createCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    public Curso updateCurso(Long id, Curso cursoDetails) {
        Curso curso = cursoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con id: "+ id));

        curso.setNombreCurso(cursoDetails.getNombreCurso());
        curso.setDescripcion(cursoDetails.getDescripcion());
        curso.setModalidad(cursoDetails.getModalidad());
        curso.setCupoMaximo(cursoDetails.getCupoMaximo());
        curso.setIdProfesor(curso.getIdProfesor());
        curso.setIdMateria(curso.getIdMateria());

        return cursoRepository.save(curso);
    }



}
