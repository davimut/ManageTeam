package it.davimut.TeamPixeltek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import it.davimut.TeamPixeltek.model.DipartimentoModel;
import it.davimut.TeamPixeltek.repository.DipartimentoRepository;

@Controller
@RequestMapping("/dipartimento") // Mappato su /dipartimento come indicato nell'HTML
public class DipartimentoController {

    private final DipartimentoRepository dipartimentoRepository;

    // Constructor Injection (ottima scelta, super pulita!)
    public DipartimentoController(DipartimentoRepository dipartimentoRepository) {
        this.dipartimentoRepository = dipartimentoRepository;
    }

    // 1. MOSTRA FORM: Serve il form HTML di creazione dipartimento
    @GetMapping("/crea")
    public String mostraFormDipartimento(Model model) {
        // Passiamo un oggetto vuoto al form per il data binding
        model.addAttribute("dipartimento", new DipartimentoModel());
        
        // Punta esattamente a: src/main/resources/templates/DashboardAdmin/formdipartimento.html
        return "DashboardAdmin/formdipartimento"; 
    }

    // 2. SALVA: Riceve i dati dal form, li valida e li salva nel database
    @PostMapping("/salva")
    public String salvaDipartimento(
            @Valid @ModelAttribute("dipartimento") DipartimentoModel dipartimento, 
            BindingResult bindingResult, 
            RedirectAttributes redirectAttributes) {
        
        // Se ci sono errori di validazione (es. nome vuoto), ricarica il form mostrando l'errore
        if (bindingResult.hasErrors()) {
            return "DashboardAdmin/formdipartimento";
        }
        
        // Salva sul database locale
        dipartimentoRepository.save(dipartimento);
        
        // Messaggio flash di successo pronto per essere mostrato nella DashboardAdmin
        redirectAttributes.addFlashAttribute("successMessage", "Nuovo dipartimento inizializzato con successo!");
        
        // Reindirizza l'admin alla dashboard principale
        return "redirect:/teamAdmin";
    }
}