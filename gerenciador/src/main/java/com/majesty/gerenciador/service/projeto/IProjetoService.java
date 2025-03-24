package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IProjetoService {

    String calcularRisco(BigDecimal orcamentoTotal, LocalDate previsaoDeTermino, LocalDate dataDeInicio);

    void alocarMembroAoProjeto(Long projetoId, Long membroId);

    void cancelarProjeto(Projeto projeto);

    void transitarStatus(Projeto projeto, StatusProjeto novoStatus);

    boolean podeTransitarPara(StatusProjeto atualStatus, StatusProjeto novoStatus);

    void excluirProjeto(Projeto projeto);

    RelatorioDTO gerarRelatorio();
}
