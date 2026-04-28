package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IesRequestDTO {

  @NotBlank(message = "O nome da IES é obrigatório")
  @Size(max = 200, message = "O nome deve ter no máximo 200 caracteres")
  private String nome;

  @Size(max = 300, message = "O endereço deve ter no máximo 300 caracteres")
  private String endereco;

  @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
  private String telefone;

  private Status status;
}