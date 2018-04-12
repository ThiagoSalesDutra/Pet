package br.com.caesgatos.service;

import br.com.caesgatos.domain.Especie;
import java.util.List;

/**
 * Service Interface for managing Especie.
 */
public interface EspecieService {

    /**
     * Save a especie.
     *
     * @param especie the entity to save
     * @return the persisted entity
     */
    Especie save(Especie especie);

    /**
     * Get all the especies.
     *
     * @return the list of entities
     */
    List<Especie> findAll();

    /**
     * Get the "id" especie.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Especie findOne(Long id);

    /**
     * Delete the "id" especie.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
