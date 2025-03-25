package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IProjetoService {

    ProjetoDTO criarProjeto(ProjetoDTO projetoDTO);
    ProjetoDTO obterProjeto(Long id);
    List<ProjetoDTO> listarProjetos();
    ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO);
    void deletarProjeto(Long id);
    void seguirTransitacaoStatus(Long id, StatusProjeto novoStatus);
    void cancelarProjeto(Long projetoId);
    void proibidoPularStatus(StatusProjeto atual, StatusProjeto novo);
    void proibidoExcluirProjeto(Projeto projeto);
    String calculaRisco(BigDecimal orcamento, LocalDate previsaoTermino, LocalDate dataInicio);
    void alocarMembroAoProjeto(Long projetoId, Long membroId);
    RelatorioDTO gerarRelatorioPortfolio();
}
