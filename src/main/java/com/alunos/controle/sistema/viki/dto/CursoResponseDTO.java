package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.enums.Turno;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CursoResponseDTO {
  private Long id;
  private String sigla;
  private String descricao;
  private Turno turno;
  private String coordenador;
  private String escolaNome;
  private Long escolaId;
  private LocalDate dataCadastro;
  private Status status;
}