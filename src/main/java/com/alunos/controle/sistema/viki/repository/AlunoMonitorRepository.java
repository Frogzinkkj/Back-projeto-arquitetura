package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.AlunoMonitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoMonitorRepository extends JpaRepository<AlunoMonitor, Long> {
  boolean existsByMatriculaAndSemestre(String matricula, String semestre);
}
