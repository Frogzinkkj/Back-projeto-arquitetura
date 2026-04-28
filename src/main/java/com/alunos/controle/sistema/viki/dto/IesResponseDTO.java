package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IesResponseDTO {

  private Long id;
  private String nome;
  private String endereco;
  private String telefone;
  private Status status;

}