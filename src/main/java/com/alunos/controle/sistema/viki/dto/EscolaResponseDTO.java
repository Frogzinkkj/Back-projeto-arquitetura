package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import lombok.Data;

@Data
public class EscolaResponseDTO {
  private Long id;
  private String nome;
  private String coordenador;
  private Status status;
  private Long iesId;
  private String iesNome;
}