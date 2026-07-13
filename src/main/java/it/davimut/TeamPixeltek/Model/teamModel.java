package it.davimut.TeamPixeltek.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "member")

public class TeamModel { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank(message = "Il nome del membro è obbligatorio")
	@Column(name = "nome", nullable = false) 
	private String nome;

	@NotBlank(message = "Il cognome del membro è obbligatorio")
	@Column(name = "cognome", nullable = false)
	private String cognome;

	@NotBlank(message = "La descrizione è obbligatoria")
	@Column(name = "descrizione", nullable = false)
	private String descrizione;

	@Column(name = "foto_url", nullable = true)
	private String fotoUrl;

	
	// --- GETTER E SETTER ---

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	// --- TO STRING ---
	@Override
	public String toString() {
		return "TeamModel [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", descrizione=" + descrizione 
				+ ", fotoUrl=" + fotoUrl + "]";
	}
}