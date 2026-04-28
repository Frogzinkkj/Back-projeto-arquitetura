package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.DisciplinaRequestDTO;
import com.alunos.controle.sistema.viki.dto.DisciplinaResponseDTO;
import com.alunos.controle.sistema.viki.entity.Disciplina;
import com.alunos.controle.sistema.viki.entity.Escola;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.DisciplinaRepository;
import com.alunos.controle.sistema.viki.repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private EscolaRepository escolaRepository;

  public DisciplinaResponseDTO register(DisciplinaRequestDTO request) {
    Escola escola = escolaRepository.findById(request.getEscolaId())
        .orElseThrow(() -> new RuntimeException("Escola not found with ID: " + request.getEscolaId()));

    Disciplina newDisciplina = new Disciplina();
    newDisciplina.setSigla(request.getSigla());
    newDisciplina.setDescricao(request.getDescricao());
    newDisciplina.setCargaHoraria(request.getCargaHoraria());
    newDisciplina.setEscola(escola);
    newDisciplina.setDataCadastro(LocalDate.now());
    newDisciplina.setStatus(Status.ATIVO);

    Disciplina savedDisciplina = disciplinaRepository.save(newDisciplina);
    return convertToResponse(savedDisciplina);
  }

  public List<DisciplinaResponseDTO> listActives() {
    List<Disciplina> activeDisciplines = disciplinaRepository.findAllByStatus(Status.ATIVO);
    return activeDisciplines.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public List<DisciplinaResponseDTO> listAll() {
    List<Disciplina> allDisciplines = disciplinaRepository.findAll();
    return allDisciplines.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public List<DisciplinaResponseDTO> listByStatus(Status status) {
    List<Disciplina> disciplines = disciplinaRepository.findAllByStatus(status);
    return disciplines.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public DisciplinaResponseDTO getById(Long id) {
    Disciplina disciplina = disciplinaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Disciplina not found with ID: " + id));
    return convertToResponse(disciplina);
  }

  public void inactivate(Long id) {
    Disciplina disciplina = disciplinaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Disciplina not found with ID: " + id));
    disciplina.setStatus(Status.INATIVO);
    disciplinaRepository.save(disciplina);
  }

  public void activate(Long id) {
    Disciplina disciplina = disciplinaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Disciplina not found with ID: " + id));
    disciplina.setStatus(Status.ATIVO);
    disciplinaRepository.save(disciplina);
  }

  private DisciplinaResponseDTO convertToResponse(Disciplina disciplina) {
    DisciplinaResponseDTO response = new DisciplinaResponseDTO();
    response.setId(disciplina.getId());
    response.setSigla(disciplina.getSigla());
    response.setDescricao(disciplina.getDescricao());
    response.setCargaHoraria(disciplina.getCargaHoraria());
    response.setEscolaNome(disciplina.getEscola().getNome());
    response.setDataCadastro(disciplina.getDataCadastro());
    response.setStatus(disciplina.getStatus());
    return response;
  }
}
