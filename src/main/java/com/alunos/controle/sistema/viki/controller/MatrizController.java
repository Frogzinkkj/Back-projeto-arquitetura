package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.MatrizDisciplinaRequestDTO;
import com.alunos.controle.sistema.viki.dto.MatrizRequestDTO;
import com.alunos.controle.sistema.viki.dto.MatrizResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.MatrizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matrizes")
public class MatrizController {

  @Autowired
  private MatrizService matrizService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<MatrizResponseDTO>> create(@Valid @RequestBody MatrizRequestDTO request) {
    MatrizResponseDTO response = matrizService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(ApiResponse.success(response, "Matriz cadastrada com sucesso"));
  }

  @PutMapping("/{id}/ativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
    matrizService.activate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Matriz ativada com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    matrizService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Matriz inativada com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<MatrizResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<MatrizResponseDTO> matrizes;

    if (status != null) {
      matrizes = matrizService.listByStatus(status);
    } else {
      matrizes = matrizService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(matrizes, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativas")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<MatrizResponseDTO>>> listActives() {
    List<MatrizResponseDTO> matrizes = matrizService.listActives();
    return ResponseEntity.ok(ApiResponse.success(matrizes, "Matrizes ativas listadas com sucesso"));
  }

  @PostMapping("/{id}/disciplinas")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> associarDisciplina(
      @PathVariable Long id,
      @RequestBody MatrizDisciplinaRequestDTO request) {

    matrizService.associateDiscipline(id, request);
    return ResponseEntity.ok(ApiResponse.successMessage("Disciplina associada com sucesso"));
  }
}
