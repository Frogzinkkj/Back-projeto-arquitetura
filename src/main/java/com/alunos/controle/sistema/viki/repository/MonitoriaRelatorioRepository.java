package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.MonitoriaRelatorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoriaRelatorioRepository extends JpaRepository<MonitoriaRelatorio, Long> {
  boolean existsByAlunoMonitorIdAndSemestre(Long alunoMonitorId, String semestre);
}
