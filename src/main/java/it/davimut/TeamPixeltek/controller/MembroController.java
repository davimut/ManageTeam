package it.davimut.TeamPixeltek.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.davimut.TeamPixeltek.model.MembroModel;
import it.davimut.TeamPixeltek.model.DipartimentoModel; // <-- AGGIUNTO: Importiamo il modello Dipartimento
import it.davimut.TeamPixeltek.repository.MembroRepository;
import it.davimut.TeamPixeltek.repository.DipartimentoRepository; // <-- AGGIUNTO: Importiamo il repository Dipartimento
import jakarta.validation.Valid;

@Controller
@RequestMapping("/teamAdmin")
public class MembroController {

	@Autowired
	private MembroRepository membroRepository;

	@Autowired
	private DipartimentoRepository dipartimentoRepository; // <-- AGGIUNTO: Iniettiamo il database dei dipartimenti!

	// 1. READ ALL - Mostra la lista di tutti i membri divisi per dipartimento
	@GetMapping
	public String index(Model model) {
		// Recuperiamo la lista di tutti i dipartimenti (ognuno si porta dietro i suoi membri)
		List<DipartimentoModel> dipartimenti = dipartimentoRepository.findAll();
		
		// Passiamo "dipartimenti" a Thymeleaf, proprio come vuole il nuovo HTML
		model.addAttribute("dipartimenti", dipartimenti);
		
		return "DashboardAdmin/DashboardAdmin"
				+ ""; // Punta alla cartella templates/team/index.html
	}

	// 2. READ SINGLE - Mostra il dettaglio di un singolo membro
	@GetMapping("/dettaglio/{id}")
	public String show(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<MembroModel> queryResult = membroRepository.findById(id);
		if (queryResult.isPresent()) {
			model.addAttribute("membro", queryResult.get());
			return "teamAdmin/show"; // o il nome del tuo template di dettaglio
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Membro non trovato.");
			return "redirect:/teamAdmin";
		}
	}

	// 3. CREATE (FORM) - Mostra il form per aggiungere un nuovo membro
	@GetMapping("/crea")
	public String create(Model model) {
		model.addAttribute("membro", new MembroModel());
		// Passiamo anche i dipartimenti così nel form puoi scegliere a quale dipartimento assegnarlo!
		model.addAttribute("dipartimenti", dipartimentoRepository.findAll());
		return "teamAdmin/create"; // templates/team/create.html
	}

	// 4. CREATE (SAVE) - Salva il nuovo membro
	@PostMapping("/salva")
	public String store(@Valid @ModelAttribute("membro") MembroModel formMembro, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("dipartimenti", dipartimentoRepository.findAll());
			return "teamAdmin/create"; 
		}
		
		membroRepository.save(formMembro);
		redirectAttributes.addFlashAttribute("successMessage", "Membro del team aggiunto con successo!");
		return "redirect:/teamAdmin";
	}

	// 5. UPDATE (FORM) - Mostra il form di modifica pre-compilato
	@GetMapping("/modifica/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<MembroModel> queryResult = membroRepository.findById(id);
		
		if (queryResult.isPresent()) {
			model.addAttribute("membro", queryResult.get());
			model.addAttribute("dipartimenti", dipartimentoRepository.findAll());
			return "teamAdmin/edit"; // templates/team/edit.html
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Impossibile modificare: membro non trovato.");
			return "redirect:/teamAdmin";
		}
	}

	// 6. UPDATE (SAVE) - Applica le modifiche nel database
	@PostMapping("/modifica/{id}")
	public String update(@PathVariable Integer id, @Valid @ModelAttribute("membro") MembroModel formMembro, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("dipartimenti", dipartimentoRepository.findAll());
			return "teamAdmin/edit";
		}
		
		formMembro.setId(id); 
		membroRepository.save(formMembro);
		
		redirectAttributes.addFlashAttribute("successMessage", "Dati del membro aggiornati!");
		return "redirect:/teamAdmin";
	}

	// 7. DELETE - Rimuove il membro dal database
	@PostMapping("/elimina/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		if (membroRepository.existsById(id)) {
			membroRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "Membro rimosso dal team.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Impossibile eliminare: membro non trovato.");
		}
		return "redirect:/teamAdmin";
	}
}