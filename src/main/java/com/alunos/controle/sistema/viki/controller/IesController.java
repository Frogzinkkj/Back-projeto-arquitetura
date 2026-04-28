package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.IesRequestDTO;
import com.alunos.controle.sistema.viki.dto.IesResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.IesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ies")
public class IesController {

  @Autowired
  private IesService iesService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<IesResponseDTO>> create(@Valid @RequestBody IesRequestDTO request) {
    IesResponseDTO response = iesService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "IES cadastrada com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<IesResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<IesResponseDTO> iesList;

    if (status != null) {
      iesList = iesService.listByStatus(status);
    } else {
      iesList = iesService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(iesList, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativas")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<IesResponseDTO>>> listActives() {
    List<IesResponseDTO> iesList = iesService.listActives();
    return ResponseEntity.ok(ApiResponse.success(iesList, "IES ativas listadas com sucesso"));
  }

  @PutMapping("/{id}/ativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> activate(@PathVariable Long id) {
    iesService.activate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("IES ativada com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    iesService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("IES inativada com sucesso"));
  }
}