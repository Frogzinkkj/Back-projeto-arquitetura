package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.MatrizDisciplinaRequestDTO;
import com.alunos.controle.sistema.viki.dto.MatrizRequestDTO;
import com.alunos.controle.sistema.viki.dto.MatrizResponseDTO;
import com.alunos.controle.sistema.viki.entity.Curso;
import com.alunos.controle.sistema.viki.entity.Matriz;
import com.alunos.controle.sistema.viki.entity.MatrizDisciplina;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.CursoRepository;
import com.alunos.controle.sistema.viki.repository.DisciplinaRepository;
import com.alunos.controle.sistema.viki.repository.MatrizDisciplinaRepository;
import com.alunos.controle.sistema.viki.repository.MatrizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatrizService {

  @Autowired
  private MatrizRepository matrizRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @Autowired
  private MatrizDisciplinaRepository matrizDisciplinaRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  public MatrizResponseDTO register(MatrizRequestDTO request) {
    Curso curso = cursoRepository.findById(request.getCursoId())
        .orElseThrow(() -> new RuntimeException("Curso não encontrado com o ID: " + request.getCursoId()));

    Status status = request.getStatus() == null ? Status.ATIVO : request.getStatus();

    if (status == Status.ATIVO && matrizRepository.existsByCursoIdAndStatus(curso.getId(), Status.ATIVO)) {
      throw new RuntimeException("Já existe uma matriz ativa para este curso");
    }

    Matriz matriz = new Matriz();
    matriz.setNome(request.getNome());
    matriz.setDescricao(request.getDescricao());
    matriz.setCurso(curso);
    matriz.setDataCadastro(LocalDate.now());
    matriz.setStatus(status);

    Matriz matrizSalva = matrizRepository.save(matriz);
    return toResponse(matrizSalva);
  }

  public void activate(Long id) {

    Matriz matriz = matrizRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matriz não encontrada com o ID: " + id));

    if (matrizRepository.existsByCursoIdAndStatusAndIdNot(matriz.getCurso().getId(), Status.ATIVO, matriz.getId())) {
      throw new RuntimeException("Não é permitido ativar mais de uma matriz para o mesmo curso");
    }

    matriz.setStatus(Status.ATIVO);
    matrizRepository.save(matriz);
  }

  public void inactivate(Long id) {

    Matriz matriz = matrizRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matriz não encontrada com o ID: " + id));
    matriz.setStatus(Status.INATIVO);
    matrizRepository.save(matriz);
  }



  @Transactional
  public void associateDiscipline(Long matrizId, MatrizDisciplinaRequestDTO request) {

    Matriz matriz = matrizRepository.findById(matrizId)
        .orElseThrow(() -> new RuntimeException("Matriz não encontrada"));

    MatrizDisciplina matrizDisciplina = new MatrizDisciplina();
    matrizDisciplina.setMatriz(matriz);

    matrizDisciplina.setDisciplina(disciplinaRepository.findById(request.disciplinaId())
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada")));

    if (request.preRequisitoId() != null) {
      matrizDisciplina.setPreRequisito(disciplinaRepository.findById(request.preRequisitoId())
          .orElseThrow(() -> new RuntimeException("Pré-requisito não encontrado")));
    }

    matrizDisciplinaRepository.save(matrizDisciplina);
  }

  private MatrizResponseDTO toResponse(Matriz matriz) {

    MatrizResponseDTO response = new MatrizResponseDTO();

    response.setId(matriz.getId());
    response.setNome(matriz.getNome());
    response.setDescricao(matriz.getDescricao());
    response.setDataCadastro(matriz.getDataCadastro());

    if (matriz.getCurso() != null) {
      response.setCursoId(matriz.getCurso().getId());
      response.setCursoDescricao(matriz.getCurso().getSigla() + " - " + matriz.getCurso().getDescricao());

      if (matriz.getCurso().getEscola() != null) {
        response.setCursoNome(matriz.getCurso().getDescricao());
      }
    }

    response.setDataCadastro(matriz.getDataCadastro());
    response.setStatus(matriz.getStatus());
    return response;
  }

  public List<MatrizResponseDTO> listAll() {

    List<Matriz> allMatrizes = matrizRepository.findAll();
    return allMatrizes.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public List<MatrizResponseDTO> listActives() {

    List<Matriz> activeMatrizes = matrizRepository.findAllByStatus(Status.ATIVO);
    return activeMatrizes.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public List<MatrizResponseDTO> listByStatus(Status status) {

    List<Matriz> matrizes = matrizRepository.findAllByStatus(status);
    return matrizes.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

}
