package br.com.caesgatos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Especie.
 */
@Entity
@Table(name = "especie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Especie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "especie")
    private String especie;

    @OneToMany(mappedBy = "especie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Raca> racas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public Especie especie(String especie) {
        this.especie = especie;
        return this;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Set<Raca> getRacas() {
        return racas;
    }

    public Especie racas(Set<Raca> racas) {
        this.racas = racas;
        return this;
    }

    public Especie addRacas(Raca raca) {
        this.racas.add(raca);
        raca.setEspecie(this);
        return this;
    }

    public Especie removeRacas(Raca raca) {
        this.racas.remove(raca);
        raca.setEspecie(null);
        return this;
    }

    public void setRacas(Set<Raca> racas) {
        this.racas = racas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Especie especie = (Especie) o;
        if (especie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), especie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Especie{" +
            "id=" + getId() +
            ", especie='" + getEspecie() + "'" +
            "}";
    }
}
