package com.alunos.controle.sistema.viki.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aluno_atendido")
@Data
public class AlunoAtendido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "relatorio_id", nullable = false)
  private MonitoriaRelatorio relatorio;

  @Column(nullable = false, length = 50)
  private String matricula;

  @Column(nullable = false, length = 200)
  private String nome;

  // TODO: Gerar Getters e Setters (ou usar @Data do Lombok)
}