package it.davimut.TeamPixeltek.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.davimut.TeamPixeltek.model.DipartimentoModel;
import it.davimut.TeamPixeltek.model.MembroModel;
import it.davimut.TeamPixeltek.repository.DipartimentoRepository;
import it.davimut.TeamPixeltek.repository.MembroRepository;

@Controller
@RequestMapping("/team") // Rotta pubblica
public class TeamPublicController {

    @Autowired
    private DipartimentoRepository dipartimentoRepository;

    @Autowired
    private MembroRepository membroRepository;

    // 1. VIEW ALL (Pubblica) - Mostra il team diviso per dipartimento senza comandi di modifica
    @GetMapping
    public String index(Model model) {
        List<DipartimentoModel> dipartimenti = dipartimentoRepository.findAll();
        model.addAttribute("dipartimenti", dipartimenti);
        return "teamPublic/index"; // Punta alla cartella templates/team/index.html
    }

    // 2. VIEW SINGLE (Pubblica) - Mostra il dettaglio di un membro in sola lettura
    @GetMapping("/dettaglio/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<MembroModel> queryResult = membroRepository.findById(id);
        if (queryResult.isPresent()) {
            model.addAttribute("membro", queryResult.get());
            return "team/show"; // templates/team/show.html (La vista di dettaglio pubblica)
        }
        return "redirect:/team";
    }
}