package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.DisciplinaRequestDTO;
import com.alunos.controle.sistema.viki.dto.DisciplinaResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

  @Autowired
  private DisciplinaService disciplinaService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<DisciplinaResponseDTO>> create(@Valid @RequestBody DisciplinaRequestDTO request) {
    DisciplinaResponseDTO response = disciplinaService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Disciplina criada com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<DisciplinaResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<DisciplinaResponseDTO> disciplines;

    if (status != null) {
      disciplines = disciplinaService.listByStatus(status);
    } else {
      disciplines = disciplinaService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(disciplines, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativas")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<DisciplinaResponseDTO>>> listActives() {
    List<DisciplinaResponseDTO> disciplines = disciplinaService.listActives();
    return ResponseEntity.ok(ApiResponse.success(disciplines, "Disciplinas ativas listadas com sucesso"));
  }

  @GetMapping("/status/{status}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<DisciplinaResponseDTO>>> listByStatus(@PathVariable Status status) {
    List<DisciplinaResponseDTO> disciplines = disciplinaService.listByStatus(status);
    return ResponseEntity.ok(ApiResponse.success(disciplines, "Disciplinas filtradas por status com sucesso"));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<DisciplinaResponseDTO>> getById(@PathVariable Long id) {
    DisciplinaResponseDTO discipline = disciplinaService.getById(id);
    return ResponseEntity.ok(ApiResponse.success(discipline, "Disciplina encontrada com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    disciplinaService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Disciplina inativada com sucesso"));
  }

  @PutMapping("/{id}/ativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
    disciplinaService.activate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Disciplina ativada com sucesso"));
  }
}
