package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Disciplina {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,length = 20)
  private String sigla;

  @Column(nullable = false,length = 300)
  private String descricao;

  @Column(nullable = false)
  private Integer cargaHoraria;

  @Column(name = "data_cadastro", nullable = false,updatable = false)
  private LocalDate dataCadastro;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "escola_id",nullable = false)
  private Escola escola;
}
