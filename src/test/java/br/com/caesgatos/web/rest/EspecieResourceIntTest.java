package br.com.caesgatos.web.rest;

import br.com.caesgatos.CaesGatosApp;

import br.com.caesgatos.domain.Especie;
import br.com.caesgatos.repository.EspecieRepository;
import br.com.caesgatos.service.EspecieService;
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
 * Test class for the EspecieResource REST controller.
 *
 * @see EspecieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaesGatosApp.class)
public class EspecieResourceIntTest {

    private static final String DEFAULT_ESPECIE = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIE = "BBBBBBBBBB";

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private EspecieService especieService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEspecieMockMvc;

    private Especie especie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EspecieResource especieResource = new EspecieResource(especieService);
        this.restEspecieMockMvc = MockMvcBuilders.standaloneSetup(especieResource)
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
    public static Especie createEntity(EntityManager em) {
        Especie especie = new Especie()
            .especie(DEFAULT_ESPECIE);
        return especie;
    }

    @Before
    public void initTest() {
        especie = createEntity(em);
    }

    @Test
    @Transactional
    public void createEspecie() throws Exception {
        int databaseSizeBeforeCreate = especieRepository.findAll().size();

        // Create the Especie
        restEspecieMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isCreated());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeCreate + 1);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getEspecie()).isEqualTo(DEFAULT_ESPECIE);
    }

    @Test
    @Transactional
    public void createEspecieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = especieRepository.findAll().size();

        // Create the Especie with an existing ID
        especie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecieMockMvc.perform(post("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEspecies() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        // Get all the especieList
        restEspecieMockMvc.perform(get("/api/especies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especie.getId().intValue())))
            .andExpect(jsonPath("$.[*].especie").value(hasItem(DEFAULT_ESPECIE.toString())));
    }

    @Test
    @Transactional
    public void getEspecie() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        // Get the especie
        restEspecieMockMvc.perform(get("/api/especies/{id}", especie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(especie.getId().intValue()))
            .andExpect(jsonPath("$.especie").value(DEFAULT_ESPECIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEspecie() throws Exception {
        // Get the especie
        restEspecieMockMvc.perform(get("/api/especies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEspecie() throws Exception {
        // Initialize the database
        especieService.save(especie);

        int databaseSizeBeforeUpdate = especieRepository.findAll().size();

        // Update the especie
        Especie updatedEspecie = especieRepository.findOne(especie.getId());
        // Disconnect from session so that the updates on updatedEspecie are not directly saved in db
        em.detach(updatedEspecie);
        updatedEspecie
            .especie(UPDATED_ESPECIE);

        restEspecieMockMvc.perform(put("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEspecie)))
            .andExpect(status().isOk());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getEspecie()).isEqualTo(UPDATED_ESPECIE);
    }

    @Test
    @Transactional
    public void updateNonExistingEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();

        // Create the Especie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEspecieMockMvc.perform(put("/api/especies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isCreated());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEspecie() throws Exception {
        // Initialize the database
        especieService.save(especie);

        int databaseSizeBeforeDelete = especieRepository.findAll().size();

        // Get the especie
        restEspecieMockMvc.perform(delete("/api/especies/{id}", especie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especie.class);
        Especie especie1 = new Especie();
        especie1.setId(1L);
        Especie especie2 = new Especie();
        especie2.setId(especie1.getId());
        assertThat(especie1).isEqualTo(especie2);
        especie2.setId(2L);
        assertThat(especie1).isNotEqualTo(especie2);
        especie1.setId(null);
        assertThat(especie1).isNotEqualTo(especie2);
    }
}
