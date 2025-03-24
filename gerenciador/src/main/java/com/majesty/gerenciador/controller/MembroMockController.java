package com.majesty.gerenciador.controller;

import com.majesty.gerenciador.entity.Membro;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membros")
public class MembroMockController {

    @PostMapping
    public Membro criarMembro(@RequestBody Membro membro) {
        // Simula o processo de criação de um membro
        membro.setId(1L);
        return membro;
    }

    @GetMapping("/{id}")
    public Membro consultarMembro(@PathVariable Long id) {
        // Simula a consulta de um membro pelo ID
        Membro membro = new Membro();
        membro.setId(id);
        membro.setNome("Membro " + id);
        membro.setCargo("Cargo " + id);
        return membro;
    }
}
