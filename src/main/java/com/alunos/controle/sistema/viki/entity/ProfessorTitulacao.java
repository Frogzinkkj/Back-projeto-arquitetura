package com.alunos.controle.sistema.viki.entity;

import com.alunos.controle.sistema.viki.enums.CategoriaTitulacao;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "professor_titulacao")
@Data
public class ProfessorTitulacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "professor_id", nullable = false)
  private Professor professor;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  private CategoriaTitulacao categoria;

  @Column(nullable = false, length = 200)
  private String instituicao;

  @Column(name = "nome_curso", nullable = false, length = 200)
  private String nomeCurso;

  @Column(name = "ano_conclusao", nullable = false)
  private Integer anoConclusao;


}