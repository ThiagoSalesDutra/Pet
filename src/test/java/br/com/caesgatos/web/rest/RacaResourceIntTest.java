package br.com.caesgatos.web.rest;

import br.com.caesgatos.CaesGatosApp;

import br.com.caesgatos.domain.Raca;
import br.com.caesgatos.repository.RacaRepository;
import br.com.caesgatos.service.RacaService;
import br.com.caesgatos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.caesgatos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RacaResource REST controller.
 *
 * @see RacaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaesGatosApp.class)
public class RacaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DETALHES = "AAAAAAAAAA";
    private static final String UPDATED_DETALHES = "BBBBBBBBBB";

    private static final String DEFAULT_PORTE = "AAAAAAAAAA";
    private static final String UPDATED_PORTE = "BBBBBBBBBB";

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private RacaService racaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRacaMockMvc;

    private Raca raca;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacaResource racaResource = new RacaResource(racaService);
        this.restRacaMockMvc = MockMvcBuilders.standaloneSetup(racaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Raca createEntity(EntityManager em) {
        Raca raca = new Raca()
            .nome(DEFAULT_NOME)
            .detalhes(DEFAULT_DETALHES)
            .porte(DEFAULT_PORTE);
        return raca;
    }

    @Before
    public void initTest() {
        raca = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaca() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isCreated());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate + 1);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testRaca.getDetalhes()).isEqualTo(DEFAULT_DETALHES);
        assertThat(testRaca.getPorte()).isEqualTo(DEFAULT_PORTE);
    }

    @Test
    @Transactional
    public void createRacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racaRepository.findAll().size();

        // Create the Raca with an existing ID
        raca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacaMockMvc.perform(post("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isBadRequest());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRacas() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get all the racaList
        restRacaMockMvc.perform(get("/api/racas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].detalhes").value(hasItem(DEFAULT_DETALHES.toString())))
            .andExpect(jsonPath("$.[*].porte").value(hasItem(DEFAULT_PORTE.toString())));
    }

    @Test
    @Transactional
    public void getRaca() throws Exception {
        // Initialize the database
        racaRepository.saveAndFlush(raca);

        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", raca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raca.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.detalhes").value(DEFAULT_DETALHES.toString()))
            .andExpect(jsonPath("$.porte").value(DEFAULT_PORTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaca() throws Exception {
        // Get the raca
        restRacaMockMvc.perform(get("/api/racas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaca() throws Exception {
        // Initialize the database
        racaService.save(raca);

        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Update the raca
        Raca updatedRaca = racaRepository.findOne(raca.getId());
        // Disconnect from session so that the updates on updatedRaca are not directly saved in db
        em.detach(updatedRaca);
        updatedRaca
            .nome(UPDATED_NOME)
            .detalhes(UPDATED_DETALHES)
            .porte(UPDATED_PORTE);

        restRacaMockMvc.perform(put("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRaca)))
            .andExpect(status().isOk());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate);
        Raca testRaca = racaList.get(racaList.size() - 1);
        assertThat(testRaca.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testRaca.getDetalhes()).isEqualTo(UPDATED_DETALHES);
        assertThat(testRaca.getPorte()).isEqualTo(UPDATED_PORTE);
    }

    @Test
    @Transactional
    public void updateNonExistingRaca() throws Exception {
        int databaseSizeBeforeUpdate = racaRepository.findAll().size();

        // Create the Raca

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRacaMockMvc.perform(put("/api/racas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raca)))
            .andExpect(status().isCreated());

        // Validate the Raca in the database
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaca() throws Exception {
        // Initialize the database
        racaService.save(raca);

        int databaseSizeBeforeDelete = racaRepository.findAll().size();

        // Get the raca
        restRacaMockMvc.perform(delete("/api/racas/{id}", raca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Raca> racaList = racaRepository.findAll();
        assertThat(racaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raca.class);
        Raca raca1 = new Raca();
        raca1.setId(1L);
        Raca raca2 = new Raca();
        raca2.setId(raca1.getId());
        assertThat(raca1).isEqualTo(raca2);
        raca2.setId(2L);
        assertThat(raca1).isNotEqualTo(raca2);
        raca1.setId(null);
        assertThat(raca1).isNotEqualTo(raca2);
    }
}
