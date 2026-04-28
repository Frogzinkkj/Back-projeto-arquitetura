package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.EscolaRequestDTO;
import com.alunos.controle.sistema.viki.dto.EscolaResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escolas")
public class EscolaController {

  @Autowired
  private EscolaService escolaService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<EscolaResponseDTO>> create(@Valid @RequestBody EscolaRequestDTO request) {
    EscolaResponseDTO response = escolaService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Escola criada com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<EscolaResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<EscolaResponseDTO> schools;

    if (status != null) {
      schools = escolaService.listByStatus(status);
    } else {
      schools = escolaService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(schools, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativas")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<EscolaResponseDTO>>> listActives() {
    List<EscolaResponseDTO> schools = escolaService.listActives();
    return ResponseEntity.ok(ApiResponse.success(schools, "Escolas ativas listadas com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    escolaService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Escola inativada com sucesso"));
  }
}
