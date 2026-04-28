package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.EscolaRequestDTO;
import com.alunos.controle.sistema.viki.dto.EscolaResponseDTO;
import com.alunos.controle.sistema.viki.entity.Escola;
import com.alunos.controle.sistema.viki.entity.Ies;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.EscolaRepository;
import com.alunos.controle.sistema.viki.repository.IesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EscolaService {

  @Autowired
  EscolaRepository escolaRepository;

  @Autowired
  IesRepository iesRepository;


  public EscolaResponseDTO register(EscolaRequestDTO escolaRequestDTO){
    Escola newEscola = new Escola();
    Ies ies = iesRepository.findById(escolaRequestDTO.getIesId())
        .orElseThrow(() -> new RuntimeException("IES não encontrada com o ID: " + escolaRequestDTO.getIesId()));
    newEscola.setNome(escolaRequestDTO.getNome());
    newEscola.setCoordenador(escolaRequestDTO.getCoordenador());
    newEscola.setIes(ies);
    newEscola.setStatus(Status.ATIVO);
    escolaRepository.save(newEscola);
    return conversorToResponse(newEscola);
  }

  public List<EscolaResponseDTO> listActives() {

    List<Escola> activesSchools = escolaRepository.findAllByStatus(Status.ATIVO);

    return activesSchools.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }

  public List<EscolaResponseDTO> listAll() {
    List<Escola> allSchools = escolaRepository.findAll();
    return allSchools.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }

  public List<EscolaResponseDTO> listByStatus(Status status) {
    List<Escola> schools = escolaRepository.findAllByStatus(status);
    return schools.stream()
        .map(this::conversorToResponse)
        .collect(Collectors.toList());
  }
  public void inactivate(Long id) {
    Escola escola = escolaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Escola não encontrada com o ID: " + id));
    escola.setStatus(Status.INATIVO);
    escolaRepository.save(escola);
  }

  private EscolaResponseDTO conversorToResponse(Escola escola) {
    EscolaResponseDTO escolaResponseDTO = new EscolaResponseDTO();
    escolaResponseDTO.setId(escola.getId());
    escolaResponseDTO.setNome(escola.getNome());
    escolaResponseDTO.setCoordenador(escola.getCoordenador());
    escolaResponseDTO.setStatus(escola.getStatus());

    if (escola.getIes() != null) {
        escolaResponseDTO.setIesId(escola.getIes().getId());
        escolaResponseDTO.setIesNome(escola.getIes().getNome());
    }

    return escolaResponseDTO;
  }
}
