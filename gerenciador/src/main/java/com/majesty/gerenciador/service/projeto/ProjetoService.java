package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;
import com.majesty.gerenciador.repository.MembroRepository;
import com.majesty.gerenciador.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.majesty.gerenciador.enums.StatusProjeto.*;

@Service
public class ProjetoService implements  IProjetoService{

    private final ProjetoRepository projetoRepository;
    private final MembroRepository membroRepository;

    private ProjetoService(ProjetoRepository projetoRepository, MembroRepository membroRepository){
        this.projetoRepository = projetoRepository;
        this.membroRepository = membroRepository;
    }

    @Override
    public String calcularRisco(BigDecimal orcamentoTotal, LocalDate previsaoDeTermino, LocalDate dataDeInicio) {
        long meses = ChronoUnit.MONTHS.between(dataDeInicio, previsaoDeTermino);

        if (calcularBaixoRisco(orcamentoTotal, meses)) {
            return "Baixo Risco";
        } else if (calcularMedioRisco(orcamentoTotal, meses)) {
            return "Médio Risco";
        } else if (calcularAltoRisco(orcamentoTotal, meses)) {
            return "Alto Risco";
        }

        return "Desconhecido";
    }

    @Override
    public void alocarMembroAoProjeto(Long projetoId, Long membroId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Membro membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado"));

        // Verifica se o membro tem a atribuição "funcionário"
        if (!"funcionário".equals(membro.getCargo())) {
            throw new IllegalArgumentException("Apenas membros com atribuição 'funcionário' podem ser alocados.");
        }

        // Verifica se o projeto já possui 10 membros
        if (projeto.getMembrosAlocados().size() >= 10) {
            throw new IllegalArgumentException("O projeto já possui o número máximo de membros.");
        }

        // Verifica se o membro não está alocado em mais de 3 projetos com status diferente de 'encerrado' ou 'cancelado'
        long projetosAtivos = membro.getProjetosAlocados().stream()
                .filter(p -> p.getStatusAtual() != StatusProjeto.ENCERRADO && p.getStatusAtual() != StatusProjeto.CANCELADO)
                .count();

        if (projetosAtivos >= 3) {
            throw new IllegalArgumentException("O membro não pode estar alocado em mais de 3 projetos simultaneamente com status ativo.");
        }
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


    @Override
    public void cancelarProjeto(Projeto projeto) {
        projeto.setStatusAtual(StatusProjeto.CANCELADO);
    }


    @Override
    public void transitarStatus(Projeto projeto, StatusProjeto novoStatus) {
        if (!podeTransitarPara(StatusProjeto.valueOf(String.valueOf(projeto.getStatusAtual())), novoStatus)) {
            throw new IllegalArgumentException("Não é permitido pular etapas. A transição é inválida.");
        }
        projeto.setStatusAtual(novoStatus);

    }

    @Override
    public boolean podeTransitarPara(StatusProjeto atualStatus, StatusProjeto novoStatus) {
        if (novoStatus == StatusProjeto.CANCELADO) {
            return true;
        }
        List<StatusProjeto> statusPermitidos = Arrays.asList(StatusProjeto.values());
        int index = statusPermitidos.indexOf(this);
        int indexNovoStatus = statusPermitidos.indexOf(novoStatus);
        return indexNovoStatus == index + 1;
    }

    @Override
    public void excluirProjeto(Projeto projeto) {
        if (projeto.getStatusAtual() == StatusProjeto.INICIADO ||
                projeto.getStatusAtual() == StatusProjeto.EM_ANDAMENTO ||
                projeto.getStatusAtual() == ENCERRADO) {
            throw new IllegalArgumentException("Não é possível excluir o projeto nos status 'iniciado', 'em andamento' ou 'encerrado'.");
        }

    }

    @Override
    public RelatorioDTO gerarRelatorio() {

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
