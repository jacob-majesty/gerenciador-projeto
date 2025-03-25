package com.majesty.gerenciador.controller;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.service.membro.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/membros")
@Tag(name = "Membros", description = "API para gerenciamento de membros")
public class MembroMockController {

    @Autowired
    private MembroService membroService;


    @Operation(summary = "Criar um novo membro", description = "Cria um novo membro enviando nome e cargo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity<Membro> criarMembro(@RequestBody Membro membro) {
        Membro novoMembro = membroService.criarMembro(membro.getNome(), membro.getCargo());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }

    @Operation(summary = "Listar todos os membros", description = "Retorna uma lista de todos os membros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de membros retornada com sucesso")
    @GetMapping
    public List<Membro> listarMembros() {
        return membroService.listarMembros();
    }

    @Operation(summary = "Buscar um membro por ID", description = "Retorna os detalhes de um membro específico pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro encontrado"),
            @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Membro> buscarMembro(@PathVariable Long id) {
        Optional<Membro> membro = membroService.buscarMembro(id);
        return membro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
