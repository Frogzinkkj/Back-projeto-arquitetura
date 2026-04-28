package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.AlunoMonitorRequestDTO;
import com.alunos.controle.sistema.viki.dto.AlunoMonitorResponseDTO;
import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.MonitoriaRelatorioRequestDTO;
import com.alunos.controle.sistema.viki.dto.MonitoriaRelatorioResponseDTO;
import com.alunos.controle.sistema.viki.service.MonitoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitorias")
public class MonitoriaController {

  @Autowired
  private MonitoriaService monitoriaService;

  @PostMapping
  @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<AlunoMonitorResponseDTO>> createMonitor(@Valid @RequestBody AlunoMonitorRequestDTO request) {
    AlunoMonitorResponseDTO response = monitoriaService.registerMonitor(request);
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(ApiResponse.success(response, "Monitor cadastrado com sucesso"));
  }

  @PostMapping("/relatorio")
  @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
  public ResponseEntity<ApiResponse<MonitoriaRelatorioResponseDTO>> registrarRelatorio(@Valid @RequestBody MonitoriaRelatorioRequestDTO request) {
    MonitoriaRelatorioResponseDTO response = monitoriaService.registrarRelatorio(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(response, "Relatório registrado com sucesso"));
  }
}
