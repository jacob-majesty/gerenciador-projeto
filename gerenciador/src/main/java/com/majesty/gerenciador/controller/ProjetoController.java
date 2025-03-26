package com.majesty.gerenciador.controller;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.enums.StatusProjeto;
import com.majesty.gerenciador.service.projeto.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
@Tag(name = "Projetos", description = "Gerenciamento de projetos e relatórios")
public class ProjetoController {

    @Autowired
    private final ProjetoService projetoService;

    @Operation(summary = "Criar um novo projeto", description = "Cria um projeto com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.criarProjeto(projetoDTO));
    }

    @Operation(summary = "Obter um projeto por ID", description = "Retorna os detalhes de um projeto específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> obterProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.obterProjeto(id));
    }

    @Operation(summary = "Listar todos os projetos", description = "Retorna uma lista com todos os projetos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de projetos retornada com sucesso")
    @GetMapping("/projetos")
    public ResponseEntity<Page<ProjetoDTO>> listarProjetos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDeInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDeTermino,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamanho) {

        if (pagina < 0) pagina = 0;
        if (tamanho <= 0) tamanho = 5;

        Page<ProjetoDTO> projetosDTO = projetoService.listarProjetos(nome, descricao, dataDeInicio, dataDeTermino, pagina, tamanho);

        return ResponseEntity.ok(projetosDTO);
    }


    @Operation(summary = "Atualizar um projeto", description = "Atualiza os detalhes de um projeto existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.atualizarProjeto(id, projetoDTO));
    }

    @Operation(summary = "Deletar um projeto", description = "Remove um projeto pelo ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Projeto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é permitido excluir projetos já iniciados")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar status do projeto", description = "Realiza a transição de status do projeto seguindo a sequência lógica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é permitido pular etapas na transição de status")
    })
    @PostMapping("/{id}/transitar-status/{novoStatus}")
    public ResponseEntity<Void> seguirTransitacaoStatus(@PathVariable Long id, @PathVariable StatusProjeto novoStatus) {
        projetoService.seguirTransitacaoStatus(id, novoStatus);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancelar um projeto", description = "Marca um projeto como cancelado.")
    @ApiResponse(responseCode = "200", description = "Projeto cancelado com sucesso")
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarProjeto(@PathVariable Long id) {
        projetoService.cancelarProjeto(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Alocar um membro ao projeto", description = "Associa um membro ao projeto, desde que ele tenha a atribuição 'funcionário'.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membro alocado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Regras de alocação não atendidas"),
            @ApiResponse(responseCode = "404", description = "Membro não encontrado")
    })
    @PostMapping("/{id}/alocar-membro/{membroId}")
    public ResponseEntity<Void> alocarMembro(@PathVariable Long id, @PathVariable Long membroId) {
        projetoService.alocarMembroAoProjeto(id, membroId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Gerar relatório do portfólio", description = "Gera um relatório resumido do portfólio de projetos.")
    @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso")
    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioDTO> getRelatorioPortfolio() {
        RelatorioDTO relatorio = projetoService.gerarRelatorioPortfolio();
        return ResponseEntity.ok(relatorio);
    }
}

