package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "curso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,length = 20)
  private String sigla;

  @Column(nullable = false,length = 300)
  private String descricao;


  @Column(length = 200)
  private String coordenador;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Turno turno;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;

  @ManyToOne
  @JoinColumn(name = "escola_id",nullable = false)
  private Escola escola;

  @Column(name = "data_cadastro", nullable = false,updatable = false)
  private LocalDate dataCadastro;

}
