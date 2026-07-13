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

import it.davimut.TeamPixeltek.model.TeamModel;
import it.davimut.TeamPixeltek.repository.TeamRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;

	// 1. READ ALL - Mostra la lista di tutti i membri del team
	@GetMapping
	public String index(Model model) {
		List<TeamModel> membri = teamRepository.findAll();
		model.addAttribute("membri", membri);
		return "team/index"; // Punta alla cartella templates/team/index.html
	}

	// 2. READ SINGLE - Mostra il dettaglio di un singolo membro (usa findByIdWithDatiMembro per evitare LazyInitializationException)
	@GetMapping("/dettaglio/{id}")
	public void show(@PathVariable Integer id, Model model) {
	
		
	
	}

	// 3. CREATE (FORM) - Mostra il form per aggiungere un nuovo membro
	@GetMapping("/crea")
	public String create(Model model) {
		model.addAttribute("membro", new TeamModel());
		return "team/create"; // templates/team/create.html
	}

	// 4. CREATE (SAVE) - Salva il nuovo membro gestendo i messaggi di errore di @NotBlank
	@PostMapping("/salva")
	public String store(@Valid @ModelAttribute("membro") TeamModel formMembro, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "team/create"; 
		}
		
		teamRepository.save(formMembro);
		redirectAttributes.addFlashAttribute("successMessage", "Membro del team aggiunto con successo!");
		return "redirect:/team";
	}

	// 5. UPDATE (FORM) - Mostra il form di modifica pre-compilato
	@GetMapping("/modifica/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
		Optional<TeamModel> queryResult = teamRepository.findById(id);
		
		if (queryResult.isPresent()) {
			model.addAttribute("membro", queryResult.get());
			return "team/edit"; // templates/team/edit.html
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Impossibile modificare: membro non trovato.");
			return "redirect:/team";
		}
	}

	// 6. UPDATE (SAVE) - Applica le modifiche nel database
	@PostMapping("/modifica/{id}")
	public String update(@PathVariable Integer id, @Valid @ModelAttribute("membro") TeamModel formMembro, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "team/edit";
		}
		
		formMembro.setId(id); 
		teamRepository.save(formMembro);
		
		redirectAttributes.addFlashAttribute("successMessage", "Dati del membro aggiornati!");
		return "redirect:/team";
	}

	// 7. DELETE - Rimuove il membro dal database
	@PostMapping("/elimina/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		if (teamRepository.existsById(id)) {
			teamRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("successMessage", "Membro rimosso dal team.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Impossibile eliminare: membro non trovato.");
		}
		return "redirect:/team";
	}
}