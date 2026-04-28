package com.alunos.controle.sistema.viki.dto;

import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.enums.TipoMonitoria;
import lombok.Data;

@Data
public class AlunoMonitorResponseDTO {
  private Long id;
  private String matricula;
  private String nome;
  private String semestre;
  private TipoMonitoria tipoMonitoria;
  private String local;
  private Long disciplinaId;
  private Long professorId;
  private Status status;
}
