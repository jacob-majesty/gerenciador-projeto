package com.majesty.gerenciador.mapper;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjetoMapper {

    public ProjetoDTO toDTO(Projeto projeto) {
        return new ProjetoDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDataDeInicio(),
                projeto.getPrevisaoDeTermino(),
                projeto.getDataDeTermino(),
                projeto.getOrcamentoTotal(),
                projeto.getDescricao(),
                projeto.getGerente() != null ? projeto.getGerente().getId() : null,
                projeto.getStatusAtual()
        );
    }

    public Projeto toEntity(ProjetoDTO dto, Membro gerente) {
        Projeto projeto = new Projeto();
        projeto.setId(dto.getId());
        projeto.setNome(dto.getNome());
        projeto.setDataDeInicio(dto.getDataDeInicio());
        projeto.setPrevisaoDeTermino(dto.getPrevisaoDeTermino());
        projeto.setDataDeTermino(dto.getDataDeTermino());
        projeto.setOrcamentoTotal(dto.getOrcamentoTotal());
        projeto.setDescricao(dto.getDescricao());
        projeto.setGerente(gerente);
        projeto.setStatusAtual(dto.getStatus());
        return projeto;
    }
}

