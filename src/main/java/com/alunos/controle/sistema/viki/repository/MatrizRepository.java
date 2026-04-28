package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Matriz;
import com.alunos.controle.sistema.viki.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatrizRepository extends JpaRepository<Matriz, Long> {
  boolean existsByCursoIdAndStatus(Long cursoId, Status status);
  boolean existsByCursoIdAndStatusAndIdNot(Long cursoId, Status status, Long id);

  List<Matriz> findAllByStatus(Status status);
}
