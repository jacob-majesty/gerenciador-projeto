package com.majesty.gerenciador.controller;

import com.majesty.gerenciador.dto.RelatorioDTO;
import com.majesty.gerenciador.service.projeto.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/relatorio")
    public ResponseEntity<RelatorioDTO> getRelatorioPortfolio() {
        RelatorioDTO relatorio = projetoService.gerarRelatorio();
        return ResponseEntity.ok(relatorio);
    }
}
