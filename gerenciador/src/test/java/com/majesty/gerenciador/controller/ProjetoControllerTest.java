package com.majesty.gerenciador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.service.projeto.ProjetoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjetoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProjetoService projetoService;

    @InjectMocks
    private ProjetoController projetoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projetoController).build();
    }

    @Test
    void criarProjetos() {
    }

    @Test
    void obterProjetos() {
    }

    @Test
    void listarProjetos() {
    }

    @Test
    void atualizarProjeto() {
    }

    @Test
    void deletarProjeto() {
    }

    @Test
    void seguirTransitacaoStatus() {
    }

    @Test
    void cancelarProjeto() {
    }

    @Test
    void alocarMembro() {
    }

    @Test
    void getRelatorioPortfolio() {
    }
}