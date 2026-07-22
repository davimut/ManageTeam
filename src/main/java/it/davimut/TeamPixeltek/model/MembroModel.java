package it.davimut.TeamPixeltek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "membro")
public class MembroModel { 

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
	
    
	@NotBlank(message = "La password è obbligatoria")
	@Column(name = "password" , nullable = true)
	private String password; 
	
   

	@NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Inserisci un indirizzo email valido")
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "dipartimento_id", nullable = true)
    private DipartimentoModel dipartimento;

    // --- GETTER E SETTER ---
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public DipartimentoModel getDipartimento() {
        return dipartimento;
    }

    public void setDipartimento(DipartimentoModel dipartimento) {
        this.dipartimento = dipartimento;
    }
  
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
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

    @Override
    public String toString() {
        return "MembroModel [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", descrizione=" + descrizione 
                + ", fotoUrl=" + fotoUrl + "]";
    }
}