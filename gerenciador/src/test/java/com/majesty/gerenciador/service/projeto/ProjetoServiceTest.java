package com.majesty.gerenciador.service.projeto;

import com.majesty.gerenciador.dto.ProjetoDTO;
import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.Cargo;
import com.majesty.gerenciador.enums.StatusProjeto;
import com.majesty.gerenciador.exception.RecursoNaoEncontradoException;
import com.majesty.gerenciador.mapper.ProjetoMapper;
import com.majesty.gerenciador.repository.MembroRepository;
import com.majesty.gerenciador.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProjetoServiceTest {
    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private MembroRepository membroRepository;

    @Mock
    private ProjetoMapper projetoMapper;

    @InjectMocks
    private ProjetoService projetoService;

    private Projeto projeto;
    private ProjetoDTO projetoDTO;
    private Membro gerente;


    @BeforeEach
    void setUp() {
        gerente = new Membro();
        gerente.setId(1L);
        gerente.setNome("Gerente Teste");
        gerente.setCargo(Cargo.GERENTE.name());

        projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto Teste");
        projeto.setDescricao("Descrição Teste");
        projeto.setDataDeInicio(LocalDate.of(2024, 1, 1));
        projeto.setDataDeTermino(LocalDate.of(2024, 12, 31));
        projeto.setStatusAtual(StatusProjeto.INICIADO);
        projeto.setOrcamentoTotal(BigDecimal.valueOf(100000));
        projeto.setGerente(gerente);

        projetoDTO = new ProjetoDTO();
        projetoDTO.setId(1L);
        projetoDTO.setNome("Projeto Teste DTO");
        projetoDTO.setGerenteId(1L);
    }

    @Test
    void testCriarProjeto() {
        when(membroRepository.findById(1L)).thenReturn(Optional.of(gerente));
        when(projetoMapper.toEntity(projetoDTO, gerente)).thenReturn(projeto);
        when(projetoRepository.save(projeto)).thenReturn(projeto);
        when(projetoMapper.toDTO(projeto)).thenReturn(projetoDTO);

        ProjetoDTO resultado = projetoService.criarProjeto(projetoDTO);

        assertNotNull(resultado);
        assertEquals("Projeto Teste DTO", resultado.getNome());
    }

    @Test
    void testCriarProjeto_GerenteNaoEncontrado() {
        when(membroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> projetoService.criarProjeto(projetoDTO));
    }

    @Test
    void obterProjeto() {
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
    void calculaRisco() {
    }

    @Test
    void seguirTransitacaoStatus() {
    }

    @Test
    void cancelarProjeto() {
    }

    @Test
    void proibidoPularStatus() {
    }

    @Test
    void proibidoExcluirProjeto() {
    }

    @Test
    void alocarMembroAoProjeto() {
    }

    @Test
    void gerarRelatorioPortfolio() {
    }
}