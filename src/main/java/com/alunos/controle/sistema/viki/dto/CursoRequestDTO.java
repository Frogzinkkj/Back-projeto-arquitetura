package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Turno;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class CursoRequestDTO {
  @NotBlank(message = "Sigla do curso é obrigatória")
  @Size(min = 2, max = 20, message = "Sigla deve ter entre 2 e 20 caracteres")
  private String sigla;

  @NotBlank(message = "Descrição é obrigatória")
  @Size(min = 3, max = 300, message = "Descrição deve ter entre 3 e 300 caracteres")
  private String descricao;

  @NotNull(message = "Turno é obrigatório")
  private Turno turno;

  @Size(max = 200, message = "Coordenador deve ter no máximo 200 caracteres")
  private String coordenador;

  @NotNull(message = "Escola é obrigatória")
  private Long escolaId;
}