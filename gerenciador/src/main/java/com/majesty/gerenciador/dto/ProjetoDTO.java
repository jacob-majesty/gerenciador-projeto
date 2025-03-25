package com.majesty.gerenciador.dto;

import com.majesty.gerenciador.enums.StatusProjeto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {
    @NotNull(message = "ID não pode ser nulo")
    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Data de início não pode ser nula")
    private LocalDate dataDeInicio;

    @NotNull(message = "Previsão de término não pode ser nula")
    private LocalDate previsaoDeTermino;

    private LocalDate dataDeTermino;  // Pode ser nula se o projeto ainda não tiver terminado

    @NotNull(message = "Orçamento total não pode ser nulo")
    @Positive(message = "Orçamento total deve ser maior que zero")
    private BigDecimal orcamentoTotal;

    @NotEmpty(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Gerente ID não pode ser nulo")
    private Long gerenteId;

    @NotNull(message = "Status do projeto não pode ser nulo")
    private StatusProjeto status;
}
