package com.alunos.controle.sistema.viki.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "matriz_disciplina",uniqueConstraints = {@UniqueConstraint(columnNames = {"matriz_id","disciplina_id"})})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatrizDisciplina {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "matriz_id", nullable = false)
  private Matriz matriz;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "disciplina_id", nullable = false)
  private Disciplina disciplina;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pre_requisito_id",nullable = false,unique = true)
  private Disciplina preRequisito;
}
