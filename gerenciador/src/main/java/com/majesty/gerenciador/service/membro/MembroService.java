package com.majesty.gerenciador.service.membro;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.repository.MembroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class MembroService implements IMembroService {

    private final MembroRepository membroRepository;

    public MembroService(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    @Override
    public ResponseEntity<Membro> criarMembro(String nome, String cargo) {
        Membro membro = new Membro(nome, cargo);
        Membro novoMembro = membroRepository.save(membro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }

    @Override
    public List<Membro> listarMembros() {
        return membroRepository.findAll();
    }

    @Override
    public Optional<Membro> buscarMembro(Long id) {
        return membroRepository.findById(id);
    }
}



