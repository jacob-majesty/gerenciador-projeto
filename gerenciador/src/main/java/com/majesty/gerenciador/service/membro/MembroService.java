package com.majesty.gerenciador.service.membro;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MembroService implements IMembroService {

    private final List<Membro> membros = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Membro criarMembro(String nome, String cargo) {
        Membro membro = new Membro(nextId++, nome, cargo);
        membros.add(membro);
        return membro;
    }

    @Override
    public List<Membro> listarMembros() {
        return membros;
    }

    @Override
    public Optional<Membro> buscarMembro(Long id) {
        return membros.stream().filter(membro -> membro.getId().equals(id)).findFirst();
    }
}


