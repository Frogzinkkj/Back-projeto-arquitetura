package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.enums.TipoMonitoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aluno_monitor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlunoMonitor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,unique = true,length = 50)
  private String matricula;

  @Column(length = 200,nullable = false)
  private String nome;

  @Column(length = 10 , nullable = false)
  private String semestre;

  @Enumerated(EnumType.STRING)
  @Column
  private TipoMonitoria tipoMonitoria;

  @Column(length = 50)
  private String local;

  @Column(name = "data_cadastro", nullable = false,updatable = false)
  private LocalDate dataCadastro;

  @Column(name = "data_inicio", nullable = false,updatable = false)
  private LocalDate dataInicio;

  @Column(name = "data_fim", nullable = false,updatable = false)
  private LocalDate dataFim;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "disciplina_id",nullable = false)
  private Disciplina disciplina;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "professor_id",nullable = false)
  private Professor professor;

  @OneToMany(mappedBy = "alunoMonitor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MonitoriaRelatorio> relatorios = new ArrayList<>();
}
