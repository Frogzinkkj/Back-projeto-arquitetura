package com.alunos.controle.sistema.viki.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class EscolaRequestDTO {
  @NotBlank(message = "Nome da escola é obrigatório")
  @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
  private String nome;

  @Size(max = 200, message = "Coordenador deve ter no máximo 200 caracteres")
  private String coordenador;

  @NotNull(message = "IES é obrigatória")
  private Long iesId;
}