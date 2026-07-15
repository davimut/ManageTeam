package it.davimut.TeamPixeltek.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "dipartimento")
public class DipartimentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Il nome del dipartimento è obbligatorio")
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @JsonIgnore
    @OneToMany(mappedBy = "dipartimento", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<MembroModel> membri = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<MembroModel> getMembri() {
        return membri;
    }

    public void setMembri(List<MembroModel> membri) {
        this.membri = membri;
    }
}