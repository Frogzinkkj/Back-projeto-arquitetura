package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MatrizResponseDTO {
  private Long id;
  private String nome;
  private String descricao;
  private Long cursoId;
  private String cursoNome;
  private String cursoDescricao;
  private LocalDate dataCadastro;
  private Status status;
}
