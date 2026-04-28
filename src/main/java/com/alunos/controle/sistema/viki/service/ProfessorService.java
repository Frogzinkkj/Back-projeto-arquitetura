package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.ProfessorRequestDTO;
import com.alunos.controle.sistema.viki.dto.ProfessorResponseDTO;
import com.alunos.controle.sistema.viki.entity.Escola;
import com.alunos.controle.sistema.viki.entity.Professor;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.EscolaRepository;
import com.alunos.controle.sistema.viki.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private EscolaRepository escolaRepository;

  public ProfessorResponseDTO register(ProfessorRequestDTO request) {
    Escola escola = escolaRepository.findById(request.getEscolaId())
        .orElseThrow(() -> new RuntimeException("Escola not found with ID: " + request.getEscolaId()));

    if (professorRepository.findByMatriculaAndEmail(request.getMatricula(), request.getEmail()) != null) {
      throw new RuntimeException("Professor with this registration or email already exists");
    }

    Professor newProfessor = new Professor();
    newProfessor.setMatricula(request.getMatricula());
    newProfessor.setNome(request.getNome());
    newProfessor.setEmail(request.getEmail());
    newProfessor.setTelefone(request.getTelefone());
    newProfessor.setEscola(escola);
    newProfessor.setDataCadastro(LocalDate.now());
    newProfessor.setStatus(Status.ATIVO);

    Professor savedProfessor = professorRepository.save(newProfessor);
    return convertToResponse(savedProfessor);
  }

  public List<ProfessorResponseDTO> listActives() {
    List<Professor> activeProfessors = professorRepository.findAllByStatus(Status.ATIVO);
    return activeProfessors.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public List<ProfessorResponseDTO> listAll() {
    List<Professor> allProfessors = professorRepository.findAll();
    return allProfessors.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public List<ProfessorResponseDTO> listByStatus(Status status) {
    List<Professor> professors = professorRepository.findAllByStatus(status);
    return professors.stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  public ProfessorResponseDTO getById(Long id) {
    Professor professor = professorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + id));
    return convertToResponse(professor);
  }

  public void inactivate(Long id) {
    Professor professor = professorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + id));
    professor.setStatus(Status.INATIVO);
    professorRepository.save(professor);
  }

  public void activate(Long id) {
    Professor professor = professorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + id));
    professor.setStatus(Status.ATIVO);
    professorRepository.save(professor);
  }

  private ProfessorResponseDTO convertToResponse(Professor professor) {
    ProfessorResponseDTO response = new ProfessorResponseDTO();
    response.setId(professor.getId());
    response.setMatricula(professor.getMatricula());
    response.setNome(professor.getNome());
    response.setEmail(professor.getEmail());
    response.setTelefone(professor.getTelefone());
    response.setDataCadastro(professor.getDataCadastro());
    response.setStatus(professor.getStatus());

    if (professor.getEscola() != null) {
      response.setEscolaId(professor.getEscola().getId());
      response.setEscolaNome(professor.getEscola().getNome());
    }
    return response;
  }
}
