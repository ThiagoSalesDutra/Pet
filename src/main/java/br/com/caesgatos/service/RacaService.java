package br.com.caesgatos.service;

import br.com.caesgatos.domain.Raca;
import java.util.List;

/**
 * Service Interface for managing Raca.
 */
public interface RacaService {

    /**
     * Save a raca.
     *
     * @param raca the entity to save
     * @return the persisted entity
     */
    Raca save(Raca raca);

    /**
     * Get all the racas.
     *
     * @return the list of entities
     */
    List<Raca> findAll();

    /**
     * Get the "id" raca.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Raca findOne(Long id);

    /**
     * Delete the "id" raca.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
