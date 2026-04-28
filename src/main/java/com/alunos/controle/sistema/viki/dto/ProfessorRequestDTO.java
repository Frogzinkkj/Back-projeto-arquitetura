package com.alunos.controle.sistema.viki.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ProfessorRequestDTO {
  @NotBlank(message = "Matrícula é obrigatória")
  @Size(min = 3, max = 50, message = "Matrícula deve ter entre 3 e 50 caracteres")
  private String matricula;

  @NotBlank(message = "Nome é obrigatório")
  @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
  private String nome;

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email deve ser válido")
  private String email;

  @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
  private String telefone;

  @NotNull(message = "Escola é obrigatória")
  private Long escolaId;
}