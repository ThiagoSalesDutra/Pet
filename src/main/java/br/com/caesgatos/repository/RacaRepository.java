package br.com.caesgatos.repository;

import br.com.caesgatos.domain.Raca;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Raca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {

}
