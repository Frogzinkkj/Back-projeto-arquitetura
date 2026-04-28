package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.dto.AlunoMonitorRequestDTO;
import com.alunos.controle.sistema.viki.dto.AlunoMonitorResponseDTO;
import com.alunos.controle.sistema.viki.dto.MonitoriaRelatorioRequestDTO;
import com.alunos.controle.sistema.viki.dto.MonitoriaRelatorioResponseDTO;
import com.alunos.controle.sistema.viki.entity.AlunoMonitor;
import com.alunos.controle.sistema.viki.entity.Disciplina;
import com.alunos.controle.sistema.viki.entity.MonitoriaRelatorio;
import com.alunos.controle.sistema.viki.entity.Professor;
import com.alunos.controle.sistema.viki.enums.Status;
import com.alunos.controle.sistema.viki.repository.AlunoMonitorRepository;
import com.alunos.controle.sistema.viki.repository.DisciplinaRepository;
import com.alunos.controle.sistema.viki.repository.MonitoriaRelatorioRepository;
import com.alunos.controle.sistema.viki.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MonitoriaService {

  @Autowired
  private AlunoMonitorRepository alunoMonitorRepository;

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  @Autowired
  private MonitoriaRelatorioRepository monitoriaRelatorioRepository;

  public AlunoMonitorResponseDTO registerMonitor(AlunoMonitorRequestDTO request) {
    if (alunoMonitorRepository.existsByMatriculaAndSemestre(request.getMatricula(), request.getSemestre())) {
      throw new RuntimeException("Aluno já está vinculado como monitor em outra disciplina neste semestre");
    }

    Disciplina disciplina = disciplinaRepository.findById(request.getDisciplinaId())
        .orElseThrow(() -> new RuntimeException("Disciplina não encontrada com o ID: " + request.getDisciplinaId()));

    Professor professor = professorRepository.findById(request.getProfessorId())
        .orElseThrow(() -> new RuntimeException("Professor não encontrado com o ID: " + request.getProfessorId()));

    AlunoMonitor novoMonitor = new AlunoMonitor();
    novoMonitor.setMatricula(request.getMatricula());
    novoMonitor.setNome(request.getNome());
    novoMonitor.setSemestre(request.getSemestre());
    novoMonitor.setTipoMonitoria(request.getTipoMonitoria());
    novoMonitor.setLocal(request.getLocal());
    novoMonitor.setDataInicio(request.getDataInicio());
    novoMonitor.setDataFim(request.getDataFim());
    novoMonitor.setDataCadastro(LocalDate.now());
    novoMonitor.setStatus(Status.ATIVO);
    novoMonitor.setDisciplina(disciplina);
    novoMonitor.setProfessor(professor);

    AlunoMonitor monitorSalvo = alunoMonitorRepository.save(novoMonitor);
    return toMonitorResponse(monitorSalvo);
  }

  public MonitoriaRelatorioResponseDTO registrarRelatorio(MonitoriaRelatorioRequestDTO request) {
    AlunoMonitor monitor = alunoMonitorRepository.findById(request.getAlunoMonitorId())
        .orElseThrow(() -> new RuntimeException("Aluno monitor não encontrado com o ID: " + request.getAlunoMonitorId()));

    if (monitoriaRelatorioRepository.existsByAlunoMonitorIdAndSemestre(monitor.getId(), request.getSemestre())) {
      throw new RuntimeException("Já existe relatório cadastrado para este monitor no semestre informado");
    }

    if (!monitor.getSemestre().equals(request.getSemestre())) {
      throw new RuntimeException("Semestre do relatório deve ser igual ao semestre de atuação do monitor");
    }

    MonitoriaRelatorio relatorio = new MonitoriaRelatorio();
    relatorio.setAlunoMonitor(monitor);
    relatorio.setSemestre(request.getSemestre());
    relatorio.setQtdAlunos(request.getQtdAlunos());
    relatorio.setOcorrencias(request.getOcorrencias());
    relatorio.setParecer(request.getParecer());

    MonitoriaRelatorio relatorioSalvo = monitoriaRelatorioRepository.save(relatorio);
    return toRelatorioResponse(relatorioSalvo);
  }

  private AlunoMonitorResponseDTO toMonitorResponse(AlunoMonitor monitor) {
    AlunoMonitorResponseDTO response = new AlunoMonitorResponseDTO();
    response.setId(monitor.getId());
    response.setMatricula(monitor.getMatricula());
    response.setNome(monitor.getNome());
    response.setSemestre(monitor.getSemestre());
    response.setTipoMonitoria(monitor.getTipoMonitoria());
    response.setLocal(monitor.getLocal());
    response.setDisciplinaId(monitor.getDisciplina().getId());
    response.setProfessorId(monitor.getProfessor().getId());
    response.setStatus(monitor.getStatus());
    return response;
  }

  private MonitoriaRelatorioResponseDTO toRelatorioResponse(MonitoriaRelatorio relatorio) {
    MonitoriaRelatorioResponseDTO response = new MonitoriaRelatorioResponseDTO();
    response.setId(relatorio.getId());
    response.setAlunoMonitorId(relatorio.getAlunoMonitor().getId());
    response.setSemestre(relatorio.getSemestre());
    response.setQtdAlunos(relatorio.getQtdAlunos());
    response.setOcorrencias(relatorio.getOcorrencias());
    response.setParecer(relatorio.getParecer());
    return response;
  }
}
