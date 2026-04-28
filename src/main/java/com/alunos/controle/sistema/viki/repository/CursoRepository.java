package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Curso;
import com.alunos.controle.sistema.viki.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
  List<Curso> findAllByStatus(Status status);
}
