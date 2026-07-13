package it.davimut.TeamPixeltek.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.davimut.TeamPixeltek.model.TeamModel;

@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Integer> {

   
    // Metodi di ricerca utili per filtrare i membri del team
    List<TeamModel> findByNome(String nome);

    List<TeamModel> findByCognome(String cognome);

    // Comodo per una barra di ricerca globale (cerca nel nome o nel cognome)
    List<TeamModel> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);
}