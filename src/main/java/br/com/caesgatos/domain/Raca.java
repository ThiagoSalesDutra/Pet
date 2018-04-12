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
 * A Raca.
 */
@Entity
@Table(name = "raca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Raca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "detalhes")
    private String detalhes;

    @Column(name = "porte")
    private String porte;

    @OneToMany(mappedBy = "raca")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Especie> especies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Raca nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public Raca detalhes(String detalhes) {
        this.detalhes = detalhes;
        return this;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getPorte() {
        return porte;
    }

    public Raca porte(String porte) {
        this.porte = porte;
        return this;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public Set<Especie> getEspecies() {
        return especies;
    }

    public Raca especies(Set<Especie> especies) {
        this.especies = especies;
        return this;
    }

    public Raca addEspecie(Especie especie) {
        this.especies.add(especie);
        especie.setRaca(this);
        return this;
    }

    public Raca removeEspecie(Especie especie) {
        this.especies.remove(especie);
        especie.setRaca(null);
        return this;
    }

    public void setEspecies(Set<Especie> especies) {
        this.especies = especies;
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
        Raca raca = (Raca) o;
        if (raca.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), raca.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Raca{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", detalhes='" + getDetalhes() + "'" +
            ", porte='" + getPorte() + "'" +
            "}";
    }
}
