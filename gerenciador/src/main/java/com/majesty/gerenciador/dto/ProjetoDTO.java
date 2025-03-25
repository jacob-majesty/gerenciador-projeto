package com.majesty.gerenciador.dto;

import com.majesty.gerenciador.enums.StatusProjeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {
    private Long id;
    private String nome;
    private LocalDate dataDeInicio;
    private LocalDate previsaoDeTermino;
    private LocalDate dataDeTermino;
    private BigDecimal orcamentoTotal;
    private String descricao;
    private Long gerenteId;
    private StatusProjeto status;
}
