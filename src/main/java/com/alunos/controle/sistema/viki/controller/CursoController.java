package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.CursoRequestDTO;
import com.alunos.controle.sistema.viki.dto.CursoResponseDTO;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

  @Autowired
  private CursoService cursoService;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<CursoResponseDTO>> create(@Valid @RequestBody CursoRequestDTO request) {
    CursoResponseDTO response = cursoService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Curso criado com sucesso"));
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<CursoResponseDTO>>> listAll(
      @RequestParam(required = false) Status status
  ) {
    List<CursoResponseDTO> courses;

    if (status != null) {
      courses = cursoService.listByStatus(status);
    } else {
      courses = cursoService.listAll();
    }

    return ResponseEntity.ok(ApiResponse.success(courses, "Busca realizada com sucesso"));
  }

  @GetMapping("/ativos")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<CursoResponseDTO>>> listActives() {
    List<CursoResponseDTO> courses = cursoService.listActives();
    return ResponseEntity.ok(ApiResponse.success(courses, "Cursos ativos listados com sucesso"));
  }

  @GetMapping("/status/{status}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<List<CursoResponseDTO>>> listByStatus(@PathVariable Status status) {
    List<CursoResponseDTO> courses = cursoService.listByStatus(status);
    return ResponseEntity.ok(ApiResponse.success(courses, "Cursos filtrados por status com sucesso"));
  }

  @PutMapping("/{id}/inativar")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<Void>> inactivate(@PathVariable Long id) {
    cursoService.inactivate(id);
    return ResponseEntity.ok(ApiResponse.successMessage("Curso inativado com sucesso"));
  }
}
