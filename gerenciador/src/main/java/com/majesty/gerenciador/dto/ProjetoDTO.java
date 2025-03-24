package com.majesty.gerenciador.dto;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record ProjetoDTO(
        Long id,
        String nome,
        LocalDate dataDeInicio,
        LocalDate previsaoDeTermino,
        LocalDate dataDeTermino,
        BigDecimal orcamentoTotal,
        String descricao,
        StatusProjeto statusAtual,
        List<String> membrosAlocados
) {
    public ProjetoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDataDeInicio(),
                projeto.getPrevisaoDeTermino(),
                projeto.getDataDeTermino(),
                projeto.getOrcamentoTotal(),
                projeto.getDescricao(),
                projeto.getStatusAtual(),
                Optional.ofNullable(projeto.getMembrosAlocados())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(Membro::getNome)
                        .collect(Collectors.toList())
        );
    }
}
