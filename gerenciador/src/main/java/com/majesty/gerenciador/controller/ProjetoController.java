package com.majesty.gerenciador.controller;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.enums.StatusProjeto;
import com.majesty.gerenciador.service.projeto.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    @Autowired
    private final ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<ProjetoDTO> criarProjeto(@RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.criarProjeto(projetoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> obterProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.obterProjeto(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
        return ResponseEntity.ok(projetoService.listarProjetos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @RequestBody @Valid ProjetoDTO projetoDTO) {
        return ResponseEntity.ok(projetoService.atualizarProjeto(id, projetoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/transitar-status/{novoStatus}")
    public ResponseEntity<Void> seguirTransitacaoStatus(@PathVariable Long id, @PathVariable StatusProjeto novoStatus) {
        projetoService.seguirTransitacaoStatus(id, novoStatus);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarProjeto(@PathVariable Long id) {
        projetoService.cancelarProjeto(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/alocar-membro/{membroId}")
    public ResponseEntity<Void> alocarMembro(@PathVariable Long id, @PathVariable Long membroId) {
        projetoService.alocarMembroAoProjeto(id, membroId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioDTO> getRelatorioPortfolio() {
        RelatorioDTO relatorio = projetoService.gerarRelatorioPortfolio();
        return ResponseEntity.ok(relatorio);
    }
}
