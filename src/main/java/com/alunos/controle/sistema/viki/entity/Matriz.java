package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "matriz")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Matriz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 200,nullable = false)
  private String nome;

  @Column(nullable = false)
  private String descricao;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "curso_id",nullable = false)
  private Curso curso;

  @Column(name = "data_cadastro", nullable = false,updatable = false)
  private LocalDate dataCadastro;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;
}
