package com.majesty.gerenciador.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class RelatorioDTO {

    private Map<String, Integer> quantidadeProjetosPorStatus;
    private Map<String, BigDecimal> totalOrcadoPorStatus;
    private Double mediaDuracaoProjetosEncerrados;
    private Integer totalMembrosUnicosAlocados;


    public RelatorioDTO( Map<String, Integer> quantidadePorStatus, Map<String,
                        BigDecimal> totalOrcadoPorStatus, BigDecimal mediaDuracaoProjetosEncerrados,
                        Integer totalMembrosUnicosAlocados) {
        this.quantidadeProjetosPorStatus = quantidadePorStatus;
        this.totalOrcadoPorStatus = totalOrcadoPorStatus;
        this.mediaDuracaoProjetosEncerrados = mediaDuracaoProjetosEncerrados.doubleValue();
        this.totalMembrosUnicosAlocados = totalMembrosUnicosAlocados;
    }
}
