package br.com.caesgatos.service.impl;

import br.com.caesgatos.service.EspecieService;
import br.com.caesgatos.domain.Especie;
import br.com.caesgatos.repository.EspecieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Especie.
 */
@Service
@Transactional
public class EspecieServiceImpl implements EspecieService {

    private final Logger log = LoggerFactory.getLogger(EspecieServiceImpl.class);

    private final EspecieRepository especieRepository;

    public EspecieServiceImpl(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    /**
     * Save a especie.
     *
     * @param especie the entity to save
     * @return the persisted entity
     */
    @Override
    public Especie save(Especie especie) {
        log.debug("Request to save Especie : {}", especie);
        return especieRepository.save(especie);
    }

    /**
     * Get all the especies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Especie> findAll() {
        log.debug("Request to get all Especies");
        return especieRepository.findAll();
    }

    /**
     * Get one especie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Especie findOne(Long id) {
        log.debug("Request to get Especie : {}", id);
        return especieRepository.findOne(id);
    }

    /**
     * Delete the especie by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Especie : {}", id);
        especieRepository.delete(id);
    }
}
