package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "escola")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Escola {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,length = 200)
  private String nome;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;

  @Column(nullable = false,length = 200)
  private String coordenador;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ies_id", nullable = false)
  private Ies ies;

}
