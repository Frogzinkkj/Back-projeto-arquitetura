package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DisciplinaResponseDTO {
  private Long id;
  private String sigla;
  private String descricao;
  private Integer cargaHoraria;
  private String escolaNome;
  private LocalDate dataCadastro;
  private Status status;
}