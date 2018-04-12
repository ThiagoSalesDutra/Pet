package br.com.caesgatos.repository;

import br.com.caesgatos.domain.Especie;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Especie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {

}
