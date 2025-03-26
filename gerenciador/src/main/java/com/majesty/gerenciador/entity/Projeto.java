package com.majesty.gerenciador.entity;

import com.majesty.gerenciador.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate dataDeInicio;
    private LocalDate previsaoDeTermino;
    private LocalDate dataDeTermino;
    private BigDecimal orcamentoTotal;
    private String descricao;
    private String gerenteResponsavel;

    @Enumerated(EnumType.STRING)
    private StatusProjeto statusAtual;

    @ManyToOne
    @JoinColumn(name = "gerente_id")
    private Membro gerente;

    @ManyToMany
    @JoinTable(
            name = "projeto_membro",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "membro_id")
    )
    private Set<Membro> membrosAlocados = new HashSet<>();
}
