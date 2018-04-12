package br.com.caesgatos.service;

import br.com.caesgatos.domain.Animal;
import java.util.List;

/**
 * Service Interface for managing Animal.
 */
public interface AnimalService {

    /**
     * Save a animal.
     *
     * @param animal the entity to save
     * @return the persisted entity
     */
    Animal save(Animal animal);

    /**
     * Get all the animals.
     *
     * @return the list of entities
     */
    List<Animal> findAll();

    /**
     * Get the "id" animal.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Animal findOne(Long id);

    /**
     * Delete the "id" animal.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
