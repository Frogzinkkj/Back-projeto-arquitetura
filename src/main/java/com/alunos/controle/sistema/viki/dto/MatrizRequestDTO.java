package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MatrizRequestDTO {
  @NotBlank(message = "Nome da matriz é obrigatório")
  @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
  private String nome;

  @NotBlank(message = "Descrição é obrigatória")
  private String descricao;

  @NotNull(message = "Curso é obrigatório")
  private Long cursoId;

  private Status status;
}
