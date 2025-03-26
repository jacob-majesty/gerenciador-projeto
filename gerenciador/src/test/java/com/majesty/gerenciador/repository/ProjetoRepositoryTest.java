package com.majesty.gerenciador.repository;

import com.majesty.gerenciador.entity.Membro;
import com.majesty.gerenciador.entity.Projeto;
import com.majesty.gerenciador.enums.StatusProjeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProjetoRepositoryTest {

    private final ProjetoRepository projetoRepository;
    private final TestEntityManager entityManager;

    public ProjetoRepositoryTest(ProjetoRepository projetoRepository, TestEntityManager entityManager) {
        this.projetoRepository = projetoRepository;
        this.entityManager = entityManager;
    }

    private Projeto projeto1, projeto2;
    private Membro membro1, membro2;

    @BeforeEach
    void setUp() {
        // Criando os membros
        membro1 = new Membro();
        membro1.setId(1L);
        membro1.setNome("Membro 1");
        entityManager.persist(membro1);

        membro2 = new Membro();
        membro2.setId(2L);
        membro2.setNome("Membro 2");
        entityManager.persist(membro2);

        // Criando os projetos
        projeto1 = new Projeto();
        projeto1.setNome("Projeto A");
        projeto1.setDescricao("Descrição A");
        projeto1.setDataDeInicio(LocalDate.of(2024, 1, 1));
        projeto1.setDataDeTermino(LocalDate.of(2024, 6, 1));
        projeto1.setStatusAtual(StatusProjeto.valueOf("EM_ANDAMENTO"));
        projeto1.setOrcamentoTotal(BigDecimal.valueOf(50000));
        projeto1.getMembrosAlocados().add(membro1);
        entityManager.persist(projeto1);

        projeto2 = new Projeto();
        projeto2.setNome("Projeto B");
        projeto2.setDescricao("Descrição B");
        projeto2.setDataDeInicio(LocalDate.of(2023, 1, 1));
        projeto2.setDataDeTermino(LocalDate.of(2023, 12, 1));
        projeto2.setStatusAtual(StatusProjeto.valueOf("ENCERRADO"));
        projeto2.setOrcamentoTotal(BigDecimal.valueOf(100000));
        projeto2.getMembrosAlocados().add(membro2);
        entityManager.persist(projeto2);

        entityManager.flush();
    }

    @Test
    void testAcharProjetosAtivosPorMembro() {
        List<Projeto> projetos = projetoRepository.acharProjetosAtivosPorMembro(membro1.getId());
        assertThat(projetos).hasSize(1);
        assertThat(projetos.get(0).getNome()).isEqualTo("Projeto A");
    }

    @Test
    void testFindByFilters() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Projeto> projetos = projetoRepository.findByFilters("Projeto", null, null, null, pageable);
        assertThat(projetos.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testContarProjetosPorStatus() {
        Map<String, Integer> contagem = projetoRepository.contarProjetosPorStatus();
        assertThat(contagem.get("EM_ANDAMENTO")).isEqualTo(1);
        assertThat(contagem.get("ENCERRADO")).isEqualTo(1);
    }

    @Test
    void testTotalOrcadoPorStatus() {
        Map<String, BigDecimal> totalOrcado = projetoRepository.totalOrcadoPorStatus();
        assertThat(totalOrcado.get("EM_ANDAMENTO")).isEqualTo(BigDecimal.valueOf(50000));
        assertThat(totalOrcado.get("ENCERRADO")).isEqualTo(BigDecimal.valueOf(100000));
    }

    @Test
    void testMediaDuracaoProjetosEncerrados() {
        BigDecimal mediaDuracao = projetoRepository.mediaDuracaoProjetosEncerrados();
        assertThat(mediaDuracao).isNotNull();
        assertThat(mediaDuracao).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void testTotalMembrosUnicosAlocados() {
        Integer totalMembros = projetoRepository.totalMembrosUnicosAlocados();
        assertThat(totalMembros).isEqualTo(1); // Somente membros de projetos ativos são contados
    }
}
