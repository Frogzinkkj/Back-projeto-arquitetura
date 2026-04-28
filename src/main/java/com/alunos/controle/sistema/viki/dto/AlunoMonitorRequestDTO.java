package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.TipoMonitoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlunoMonitorRequestDTO {
  @NotBlank(message = "Matricula do monitor é obrigatória")
  @Size(min = 3, max = 50, message = "Matricula deve ter entre 3 e 50 caracteres")
  private String matricula;

  @NotBlank(message = "Nome do monitor é obrigatório")
  @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
  private String nome;

  @NotNull(message = "Disciplina é obrigatória")
  private Long disciplinaId;

  @NotNull(message = "Professor orientador é obrigatório")
  private Long professorId;

  @NotBlank(message = "Semestre é obrigatório")
  @Size(max = 10, message = "Semestre deve ter no máximo 10 caracteres")
  private String semestre;

  @NotNull(message = "Tipo de monitoria é obrigatório")
  private TipoMonitoria tipoMonitoria;

  @Size(max = 200, message = "Local deve ter no máximo 200 caracteres")
  private String local;

  @NotNull(message = "Data de inicio é obrigatória")
  private LocalDate dataInicio;

  @NotNull(message = "Data de fim é obrigatória")
  private LocalDate dataFim;
}
