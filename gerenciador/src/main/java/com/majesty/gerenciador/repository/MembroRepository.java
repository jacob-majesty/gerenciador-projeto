package com.majesty.gerenciador.repository;

import com.majesty.gerenciador.entity.Membro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MembroRepository extends JpaRepository<Membro, Long> {
    @Query("SELECT COUNT(p) FROM Membro m " +
            "JOIN m.projetosAlocados p " +
            "WHERE m.id = :membroId " +
            "AND p.statusAtual NOT IN ('ENCERRADO', 'CANCELADO')")
    long contarProjetosAtivosPorMembro(@Param("membroId") Long membroId);
}
