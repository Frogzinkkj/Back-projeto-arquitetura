package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Escola;
import com.alunos.controle.sistema.viki.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscolaRepository extends JpaRepository<Escola,Long> {
  List<Escola> findAllByStatus(Status status);
}
