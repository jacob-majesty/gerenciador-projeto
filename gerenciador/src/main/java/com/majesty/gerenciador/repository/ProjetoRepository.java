package com.majesty.gerenciador.repository;

import com.majesty.gerenciador.entity.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("SELECT p FROM Projeto p " +
            "JOIN p.membrosAlocados m " +
            "WHERE m.id = :membroId " +
            "AND p.statusAtual NOT IN ('ENCERRADO', 'CANCELADO')")
    List<Projeto> acharProjetosAtivosPorMembro(@Param("membroId") Long membroId);

    @Query("SELECT p FROM Projeto p WHERE " +
            "(:nome IS NULL OR p.nome LIKE %:nome%) AND " +
            "(:descricao IS NULL OR p.descricao LIKE %:descricao%) AND " +
            "(:dataDeInicio IS NULL OR p.dataDeInicio >= :dataDeInicio) AND " +
            "(:dataDeTermino IS NULL OR p.dataDeTermino <= :dataDeTermino)")
    Page<Projeto> findByFilters(@Param("nome") String nome,
                                @Param("descricao") String descricao,
                                @Param("dataDeInicio") LocalDate dataDeInicio,
                                @Param("dataDeTermino") LocalDate dataDeTermino,
                                Pageable pageable);


    @Query("SELECT p.statusAtual, COUNT(p) FROM Projeto p GROUP BY p.statusAtual")
    Map<String, Integer> contarProjetosPorStatus();

    @Query("SELECT p.statusAtual, SUM(p.orcamentoTotal) FROM Projeto p GROUP BY p.statusAtual")
    Map<String, BigDecimal> totalOrcadoPorStatus();

    @Query(value = "SELECT AVG(DATEDIFF(p.dataDeTermino, p.dataDeInicio)) FROM Projeto p WHERE p.statusAtual = 'ENCERRADO'", nativeQuery = true)
    BigDecimal mediaDuracaoProjetosEncerrados();


    @Query("SELECT COUNT(DISTINCT m) FROM Membro m JOIN m.projetosAlocados p WHERE p.statusAtual NOT IN ('ENCERRADO', 'CANCELADO')")
    Integer totalMembrosUnicosAlocados();
}

