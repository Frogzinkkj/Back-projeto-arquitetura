package com.alunos.controle.sistema.viki.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class DisciplinaRequestDTO {
  @NotBlank(message = "Sigla da disciplina é obrigatória")
  @Size(min = 2, max = 20, message = "Sigla deve ter entre 2 e 20 caracteres")
  private String sigla;

  @NotBlank(message = "Descrição é obrigatória")
  @Size(min = 3, max = 300, message = "Descrição deve ter entre 3 e 300 caracteres")
  private String descricao;

  @NotNull(message = "Carga horária é obrigatória")
  @Positive(message = "Carga horária deve ser positiva")
  private Integer cargaHoraria;

  @NotNull(message = "Escola é obrigatória")
  private Long escolaId;
}