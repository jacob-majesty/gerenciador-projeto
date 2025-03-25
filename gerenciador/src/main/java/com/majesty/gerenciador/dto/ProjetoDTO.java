package com.majesty.gerenciador.dto;

import com.majesty.gerenciador.enums.StatusProjeto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotBlank(message = "O cargo é obrigatório.")
    @Size(max = 50, message = "O cargo deve ter no máximo 50 caracteres.")
    @Pattern(regexp = "^(funcionário|gerente)$", message = "O cargo deve ser 'funcionário' ou 'gerente'.")
    private String cargo;

    @NotEmpty(message = "Descrição não pode ser vazia")
    private String descricao;

    @NotNull(message = "Gerente ID não pode ser nulo")
    private Long gerenteId;

    @NotNull(message = "Status do projeto não pode ser nulo")
    private StatusProjeto status;

    public ProjetoDTO(Long id, String nome, LocalDate dataDeInicio, LocalDate previsaoDeTermino, LocalDate dataDeTermino, BigDecimal orcamentoTotal, String descricao, Long gerenteId, StatusProjeto statusAtual) {
        this.id = id;
        this.nome = nome;
        this.dataDeInicio = dataDeInicio;
        this.previsaoDeTermino = previsaoDeTermino;
        this.dataDeTermino = dataDeTermino;
        this.orcamentoTotal = orcamentoTotal;
        this.descricao = descricao;
        this.gerenteId = gerenteId;
        this.status = statusAtual;
    }
}
