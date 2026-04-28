package com.alunos.controle.sistema.viki.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MonitoriaRelatorioRequestDTO {
  @NotNull(message = "Aluno monitor é obrigatório")
  private Long alunoMonitorId;

  @NotBlank(message = "Semestre é obrigatório")
  @Size(max = 10, message = "Semestre deve ter no máximo 10 caracteres")
  private String semestre;

  @NotNull(message = "Quantidade de alunos é obrigatória")
  @Min(value = 0, message = "Quantidade de alunos deve ser maior ou igual a 0")
  private Integer qtdAlunos;

  private String ocorrencias;
  private String parecer;
}
