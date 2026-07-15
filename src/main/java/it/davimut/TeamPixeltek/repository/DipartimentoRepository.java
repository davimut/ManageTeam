package it.davimut.TeamPixeltek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.davimut.TeamPixeltek.model.DipartimentoModel;

@Repository
public interface DipartimentoRepository extends JpaRepository<DipartimentoModel, Integer> {
    
    // Qui, se in futuro ti servirà, potrai definire query personalizzate.
    // Spring Boot le implementerà in automatico basandosi sul nome del metodo!
    // Esempio: 
    // Optional<DipartimentoModel> findByNome(String nome);
}