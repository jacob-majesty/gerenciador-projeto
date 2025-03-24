package com.majesty.gerenciador.repository;

import com.majesty.gerenciador.entity.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro, Long> {

}
