package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.CursoRequestDTO;
import com.alunos.controle.sistema.viki.dto.CursoResponseDTO;
import com.alunos.controle.sistema.viki.entity.Curso;
import com.alunos.controle.sistema.viki.entity.Escola;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.CursoRepository;
import com.alunos.controle.sistema.viki.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private EscolaRepository escolaRepository;

  public CursoResponseDTO register(CursoRequestDTO request) {
    Escola school = escolaRepository.findById(request.getEscolaId())
        .orElseThrow(() -> new RuntimeException("Escola not found with ID: " + request.getEscolaId()));

    Curso newCurso = new Curso();
    newCurso.setSigla(request.getSigla());
    newCurso.setDescricao(request.getDescricao());
    newCurso.setTurno(request.getTurno());
    newCurso.setCoordenador(request.getCoordenador());
    newCurso.setEscola(school);
    newCurso.setDataCadastro(LocalDate.now());
    newCurso.setStatus(Status.ATIVO);

    Curso savedCurso = cursoRepository.save(newCurso);
    return conversorToResponse(savedCurso);
  }

  public List<CursoResponseDTO> listActives() {
    List<Curso> activeCourses = cursoRepository.findAllByStatus(Status.ATIVO);
    return activeCourses.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }

  public List<CursoResponseDTO> listAll() {
    List<Curso> allCourses = cursoRepository.findAll();
    return allCourses.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }

  public List<CursoResponseDTO> listByStatus(Status status) {
    List<Curso> courses = cursoRepository.findAllByStatus(status);
    return courses.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }

  public void inactivate(Long id) {
    Curso curso = cursoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Curso not found with ID: " + id));
    curso.setStatus(Status.INATIVO);
    cursoRepository.save(curso);
  }

  private CursoResponseDTO conversorToResponse(Curso curso) {
    CursoResponseDTO response = new CursoResponseDTO();
    response.setId(curso.getId());
    response.setSigla(curso.getSigla());
    response.setDescricao(curso.getDescricao());
    response.setTurno(curso.getTurno());
    response.setCoordenador(curso.getCoordenador());
    response.setDataCadastro(curso.getDataCadastro());
    response.setStatus(curso.getStatus());

    if (curso.getEscola() != null) {
      response.setEscolaId(curso.getEscola().getId());
      response.setEscolaNome(curso.getEscola().getNome());
    }
    return response;
  }
}