package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Professor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,unique = true,length = 50)
  private String matricula;

  @Column(length = 200)
  private String nome;

  @Column(length = 200 , unique = true, nullable = false)
  private String email;

  @Column(length = 20)
  private String telefone;

  @Column(name = "data_cadastro", nullable = false,updatable = false)
  private LocalDate dataCadastro = LocalDate.now();

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status = Status.ATIVO;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "escola_id",nullable = false)
  private Escola escola;

  @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProfessorTitulacao> titulacoes = new ArrayList<>();

  public void addTitulacao(ProfessorTitulacao titulacao) {
    titulacoes.add(titulacao);
    titulacao.setProfessor(this);
  }
}
