package it.davimut.TeamPixeltek.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import it.davimut.TeamPixeltek.model.DipartimentoModel;
import it.davimut.TeamPixeltek.repository.DipartimentoRepository;

@RestController
@RequestMapping("/api/dipartimenti")
public class DipartimentoController {

    private final DipartimentoRepository dipartimentoRepository;

    // Usiamo la Constructor Injection (molto più pulita ed elegante di @Autowired sui campi)
    public DipartimentoController(DipartimentoRepository dipartimentoRepository) {
        this.dipartimentoRepository = dipartimentoRepository;
    }

    // 1. GET ALL: Recupera tutti i dipartimenti
    @GetMapping
    public ResponseEntity<List<DipartimentoModel>> getAllDipartimenti() {
        List<DipartimentoModel> dipartimenti = dipartimentoRepository.findAll();
        return ResponseEntity.ok(dipartimenti);
    }

    // 2. GET BY ID: Recupera un singolo dipartimento per ID
    @GetMapping("/{id}")
    public ResponseEntity<DipartimentoModel> getDipartimentoById(@PathVariable Integer id) {
        return dipartimentoRepository.findById(id)
                .map(ResponseEntity::ok) // Se lo trova, ritorna 200 OK con il dipartimento
                .orElse(ResponseEntity.notFound().build()); // Se non lo trova, ritorna 404 Not Found
    }

    // 3. CREATE: Crea un nuovo dipartimento
    @PostMapping
    public ResponseEntity<DipartimentoModel> createDipartimento(@Valid @RequestBody DipartimentoModel dipartimento) {
        DipartimentoModel nuovoDipartimento = dipartimentoRepository.save(dipartimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoDipartimento); // Ritorna 201 Created
    }

    // 4. UPDATE: Aggiorna un dipartimento esistente
    @PutMapping("/{id}")
    public ResponseEntity<DipartimentoModel> updateDipartimento(
            @PathVariable Integer id,
            @Valid @RequestBody DipartimentoModel dettagliDipartimento) {
        
        return dipartimentoRepository.findById(id)
                .map(dipartimentoEsistente -> {
                    // Aggiorniamo solo i campi necessari (evitando di toccare l'ID o la lista membri direttamente)
                    dipartimentoEsistente.setNome(dettagliDipartimento.getNome());
                    dipartimentoEsistente.setDescrizione(dettagliDipartimento.getDescrizione());
                    
                    DipartimentoModel dipartimentoAggiornato = dipartimentoRepository.save(dipartimentoEsistente);
                    return ResponseEntity.ok(dipartimentoAggiornato); // Ritorna 200 OK con i dati aggiornati
                })
                .orElse(ResponseEntity.notFound().build()); // Ritorna 404 se l'ID non esiste
    }

    // 5. DELETE: Elimina un dipartimento per ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDipartimento(@PathVariable Integer id) {
        if (!dipartimentoRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Ritorna 404 se non esiste
        }
        
        dipartimentoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Ritorna 204 No Content se l'eliminazione va a buon fine
    }
}