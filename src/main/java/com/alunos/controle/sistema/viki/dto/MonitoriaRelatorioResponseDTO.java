package com.alunos.controle.sistema.viki.dto;

import lombok.Data;

@Data
public class MonitoriaRelatorioResponseDTO {
  private Long id;
  private Long alunoMonitorId;
  private String semestre;
  private Integer qtdAlunos;
  private String ocorrencias;
  private String parecer;
}
