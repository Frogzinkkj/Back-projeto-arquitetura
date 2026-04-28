package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfessorResponseDTO {
  private Long id;
  private String matricula;
  private String nome;
  private String email;
  private String telefone;
  private String escolaNome;
  private Long escolaId;
  private LocalDate dataCadastro;
  private Status status;
}