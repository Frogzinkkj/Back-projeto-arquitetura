package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Disciplina;
import com.alunos.controle.sistema.viki.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
 List<Disciplina> findAllByStatus(Status status);
}
