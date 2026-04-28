package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Ies;
import com.alunos.controle.sistema.viki.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IesRepository extends JpaRepository<Ies,Long> {
  List<Ies> findAllByStatus(Status status);
}
