package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.Cargo;
import com.majesty.gerenciador.enums.RiscoProjeto;
import com.majesty.gerenciador.enums.StatusProjeto;
import com.majesty.gerenciador.exception.RecursoNaoEncontradoException;
import com.majesty.gerenciador.exception.RequisicaoInvalidaException;
import com.majesty.gerenciador.mapper.ProjetoMapper;
import com.majesty.gerenciador.repository.MembroRepository;
import com.majesty.gerenciador.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetoService implements  IProjetoService{

    private final ProjetoRepository projetoRepository;
    private final MembroRepository membroRepository;
    private final ProjetoMapper projetoMapper;

    public ProjetoDTO criarProjeto(ProjetoDTO projetoDTO) {
        Membro gerente = membroRepository.findById(projetoDTO.getGerenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gerente não encontrado"));

        Projeto projeto = projetoMapper.toEntity(projetoDTO, gerente);
        projetoRepository.save(projeto);
        return projetoMapper.toDTO(projeto);
    }

    public ProjetoDTO obterProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));
        return projetoMapper.toDTO(projeto);
    }

    public List<ProjetoDTO> listarProjetos() {
        return projetoRepository.findAll()
                .stream()
                .map(projetoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        Membro gerente = membroRepository.findById(projetoDTO.getGerenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gerente não encontrado"));

        Projeto atualizado = projetoMapper.toEntity(projetoDTO, gerente);
        atualizado.setId(projeto.getId());
        projetoRepository.save(atualizado);
        return projetoMapper.toDTO(atualizado);
    }

    public void deletarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        proibidoExcluirProjeto(projeto);
        projetoRepository.delete(projeto);
    }

    @Override
    public String calculaRisco(BigDecimal orcamentoTotal, LocalDate previsaoDeTermino, LocalDate dataDeInicio) {
        long meses = ChronoUnit.MONTHS.between(dataDeInicio, previsaoDeTermino);

        if (calcularBaixoRisco(orcamentoTotal, meses)) {
            return RiscoProjeto.BAIXO.name();

        } else if (calcularMedioRisco(orcamentoTotal, meses)) {
            return RiscoProjeto.MEDIO.name();

        } else if (calcularAltoRisco(orcamentoTotal, meses)) {
            return RiscoProjeto.ALTO.name();
        }

        return "Operação inválida";
    }

    private boolean calcularBaixoRisco(BigDecimal orcamentoTotal, long meses) {
        return orcamentoTotal.compareTo(BigDecimal.valueOf(100000)) <= 0 && meses <= 3;
    }

    private boolean calcularMedioRisco(BigDecimal orcamentoTotal, long meses) {
        return (orcamentoTotal.compareTo(BigDecimal.valueOf(100000)) > 0 && orcamentoTotal.compareTo(BigDecimal.valueOf(500000)) <= 0)
                || (meses > 3 && meses <= 6);
    }

    private boolean calcularAltoRisco(BigDecimal orcamentoTotal, long meses) {
        return orcamentoTotal.compareTo(BigDecimal.valueOf(500000)) > 0 || meses > 6;
    }


    public void seguirTransitacaoStatus(Long projetoId, StatusProjeto novoStatus) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        proibidoPularStatus(projeto.getStatusAtual(), novoStatus);

        projeto.setStatusAtual(novoStatus);
        projetoRepository.save(projeto);
    }

    public void cancelarProjeto(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        projeto.setStatusAtual(StatusProjeto.CANCELADO);
        projetoRepository.save(projeto);
    }

    public void proibidoPularStatus(StatusProjeto atual, StatusProjeto novo) {
        List<StatusProjeto> ordemStatus = List.of(
                StatusProjeto.EM_ANALISE, StatusProjeto.ANALISE_REALIZADA, StatusProjeto.ANALISE_APROVADA,
                StatusProjeto.INICIADO, StatusProjeto.PLANEJADO, StatusProjeto.EM_ANDAMENTO, StatusProjeto.ENCERRADO
        );

        if (!ordemStatus.contains(novo) || ordemStatus.indexOf(novo) != ordemStatus.indexOf(atual) + 1) {
            throw new RequisicaoInvalidaException("Transição de status inválida!");
        }
    }

    public void proibidoExcluirProjeto(Projeto projeto) {
        if (List.of(StatusProjeto.INICIADO, StatusProjeto.EM_ANDAMENTO, StatusProjeto.ENCERRADO)
                .contains(projeto.getStatusAtual())) {
            throw new RequisicaoInvalidaException("Não é permitido excluir um projeto neste status!");
        }
    }

    public void alocarMembroAoProjeto(Long projetoId, Long membroId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Membro não encontrado"));

        if (!membro.getCargo().equals(Cargo.FUNCIONARIO.name())) {
            throw new RequisicaoInvalidaException("Apenas funcionários podem ser alocados a projetos");
        }

        if (projeto.getMembrosAlocados().size() >= 10) {
            throw new RequisicaoInvalidaException("Este projeto já atingiu o limite máximo de membros");
        }

        long projetosAtivos = membroRepository.contarProjetosAtivosPorMembro(membroId);
        if (projetosAtivos >= 3) {
            throw new RequisicaoInvalidaException("Este membro já está alocado em 3 projetos ativos");
        }

        projeto.getMembrosAlocados().add(membro);
        projetoRepository.save(projeto);
    }


    @Override
    public RelatorioDTO gerarRelatorioPortfolio() {
        Map<String, Integer> quantidadePorStatus = projetoRepository.countProjetosPorStatus();
        Map<String, BigDecimal> totalOrcadoPorStatus = projetoRepository.totalOrcadoPorStatus();
        BigDecimal mediaDuracaoProjetosEncerrados = projetoRepository.mediaDuracaoProjetosEncerrados();
        Integer totalMembrosUnicosAlocados = projetoRepository.totalMembrosUnicosAlocados();

        return new RelatorioDTO(
                quantidadePorStatus,
                totalOrcadoPorStatus,
                mediaDuracaoProjetosEncerrados,
                totalMembrosUnicosAlocados
        );
    }
}
