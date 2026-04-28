package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.IesRequestDTO;
import com.alunos.controle.sistema.viki.dto.IesResponseDTO;
import com.alunos.controle.sistema.viki.entity.Ies;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.IesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IesService {

  @Autowired
  private IesRepository iesRepository;

  public IesResponseDTO register(IesRequestDTO request) {
    Ies ies = new Ies();
    ies.setNome(request.getNome());
    ies.setEndereco(request.getEndereco());
    ies.setTelefone(request.getTelefone());
    ies.setStatus(request.getStatus() == null ? Status.ATIVO : request.getStatus());

    Ies savedIes = iesRepository.save(ies);
    return toResponse(savedIes);
  }

  public void activate(Long id) {
    Ies ies = iesRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("IES não encontrada com o ID: " + id));
    ies.setStatus(Status.ATIVO);
    iesRepository.save(ies);
  }

  public void inactivate(Long id) {
    Ies ies = iesRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("IES não encontrada com o ID: " + id));
    ies.setStatus(Status.INATIVO);
    iesRepository.save(ies);
  }

  public List<IesResponseDTO> listAll() {
    return iesRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public List<IesResponseDTO> listActives() {
    return iesRepository.findAllByStatus(Status.ATIVO).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public List<IesResponseDTO> listByStatus(Status status) {
    return iesRepository.findAllByStatus(status).stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  private IesResponseDTO toResponse(Ies ies) {
    IesResponseDTO response = new IesResponseDTO();
    response.setId(ies.getId());
    response.setNome(ies.getNome());
    response.setEndereco(ies.getEndereco());
    response.setTelefone(ies.getTelefone());
    response.setStatus(ies.getStatus());
    return response;
  }
}