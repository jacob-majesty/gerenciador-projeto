package com.majesty.gerenciador.service.membro;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IMembroService {
    ResponseEntity<Membro> criarMembro(String nome, String cargo);
    List<Membro> listarMembros();
    Optional<Membro> buscarMembro(Long id);
}
