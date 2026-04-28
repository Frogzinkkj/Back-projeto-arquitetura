package com.alunos.controle.sistema.viki.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monitoria_relatorio")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonitoriaRelatorio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10,nullable = false)
  private String semestre;

  @Column(nullable = false)
  private Integer qtdAlunos;

  @Column
  private String ocorrencias;

  @Column
  private String parecer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "aluno_id",nullable = false)
  private AlunoMonitor alunoMonitor;

  @OneToMany(mappedBy = "relatorio", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AlunoAtendido> alunosAtendidos = new ArrayList<>();

  public void addAlunoAtendido(AlunoAtendido aluno) {
    alunosAtendidos.add(aluno);
    aluno.setRelatorio(this);
  }
}
