package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.ProfessorRequestDTO;
import com.alunos.controle.sistema.viki.dto.ProfessorResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

  @Autowired
  private ProfessorService professorService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<ProfessorResponseDTO>> create(@Valid @RequestBody ProfessorRequestDTO request) {
    ProfessorResponseDTO response = professorService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Professor criado com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<ProfessorResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<ProfessorResponseDTO> professors;

    if (status != null) {
      professors = professorService.listByStatus(status);
    } else {
      professors = professorService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(professors, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativos")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<ProfessorResponseDTO>>> listActives() {
    List<ProfessorResponseDTO> professors = professorService.listActives();
    return ResponseEntity.ok(ApiResponse.success(professors, "Professores ativos listados com sucesso"));
  }

  @GetMapping("/status/{status}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<ProfessorResponseDTO>>> listByStatus(@PathVariable Status status) {
    List<ProfessorResponseDTO> professors = professorService.listByStatus(status);
    return ResponseEntity.ok(ApiResponse.success(professors, "Professores filtrados por status com sucesso"));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
  public ResponseEntity<ApiResponse<ProfessorResponseDTO>> getById(@PathVariable Long id) {
    ProfessorResponseDTO professor = professorService.getById(id);
    return ResponseEntity.ok(ApiResponse.success(professor, "Professor encontrado com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    professorService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Professor inativado com sucesso"));
  }

  @PutMapping("/{id}/ativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
    professorService.activate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Professor ativado com sucesso"));
  }
}
