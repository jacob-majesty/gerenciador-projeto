package com.majesty.gerenciador.service.membro;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.repository.ProjetoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MembroService implements IMembroService {

    // URL fictícia para a API externa que cria membros
    private static final String URL_API_EXTERNA = "https://api.externa.com/membros";
    private final RestTemplate restTemplate;
    private final ProjetoRepository projetoRepository;


    private MembroService(RestTemplate restTemplate, ProjetoRepository projetoRepository) {
        this.restTemplate = restTemplate;
        this.projetoRepository = projetoRepository;
    }

    public List<Projeto> getProjetosAtivosPorMembro(Long membroId) {
        return projetoRepository.acharProjetosAtivosPorMembro(membroId);
    }

    public Membro criarMembro(String nome, String cargo) {
        // Criando um objeto que representa o membro a ser enviado à API
        Membro membro = new Membro();
        membro.setNome(nome);
        membro.setCargo(cargo);

        // Enviando o membro para a API externa
        return restTemplate.postForObject(URL_API_EXTERNA, membro, Membro.class);
    }

    public Membro consultarMembro(Long id) {
        // Consultando o membro na API externa usando o ID
        return restTemplate.getForObject(URL_API_EXTERNA + "/" + id, Membro.class);
    }
}


