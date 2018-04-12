package br.com.caesgatos.service.impl;

import br.com.caesgatos.service.RacaService;
import br.com.caesgatos.domain.Raca;
import br.com.caesgatos.repository.RacaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Raca.
 */
@Service
@Transactional
public class RacaServiceImpl implements RacaService {

    private final Logger log = LoggerFactory.getLogger(RacaServiceImpl.class);

    private final RacaRepository racaRepository;

    public RacaServiceImpl(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    /**
     * Save a raca.
     *
     * @param raca the entity to save
     * @return the persisted entity
     */
    @Override
    public Raca save(Raca raca) {
        log.debug("Request to save Raca : {}", raca);
        return racaRepository.save(raca);
    }

    /**
     * Get all the racas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Raca> findAll() {
        log.debug("Request to get all Racas");
        return racaRepository.findAll();
    }

    /**
     * Get one raca by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Raca findOne(Long id) {
        log.debug("Request to get Raca : {}", id);
        return racaRepository.findOne(id);
    }

    /**
     * Delete the raca by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Raca : {}", id);
        racaRepository.delete(id);
    }
}
