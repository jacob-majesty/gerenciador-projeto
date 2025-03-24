package com.majesty.gerenciador.service.membro;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;

import java.util.List;

public interface IMembroService {

    List<Projeto> getProjetosAtivosPorMembro(Long membroId);

    Membro criarMembro(String nome, String cargo);

    Membro consultarMembro(Long id);


}
