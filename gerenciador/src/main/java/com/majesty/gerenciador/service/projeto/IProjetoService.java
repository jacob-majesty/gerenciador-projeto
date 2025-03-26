package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.time.LocalDate;


public interface IProjetoService {

    ProjetoDTO criarProjeto(ProjetoDTO projetoDTO);
    ProjetoDTO obterProjeto(Long id);
    Page<ProjetoDTO> listarProjetos(String nome, String descricao, LocalDate dataInicio,
                                    LocalDate dataFim, int pagina, int tamanho);
    ProjetoDTO atualizarProjeto(Long id, ProjetoDTO projetoDTO);
    void deletarProjeto(Long id);
    void seguirTransitacaoStatus(Long id, StatusProjeto novoStatus);
    void cancelarProjeto(Long projetoId);
    void proibidoPularStatus(StatusProjeto atual, StatusProjeto novo);
    void proibidoExcluirProjeto(Projeto projeto);
    String calculaRisco(BigDecimal orcamentoTotal, LocalDate previsaoDeTermino, LocalDate dataDeInicio);
    void alocarMembroAoProjeto(Long projetoId, Long membroId);
    RelatorioDTO gerarRelatorioPortfolio();
}
