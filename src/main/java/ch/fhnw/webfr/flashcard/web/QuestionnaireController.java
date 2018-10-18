package ch.fhnw.webfr.flashcard.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {
	
	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("questionnaire", new Questionnaire());
		return "questionnaires/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Questionnaire questionnaire, BindingResult result) {
		if(result.hasErrors()){
			return "questionnaires/create";
		}
		questionnaireRepository.save(questionnaire);
		return "redirect:/questionnaires";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String id) {
		Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
		if (questionnaire.isPresent()) {
			questionnaireRepository.delete(questionnaire.get());
			return "redirect:/questionnaires";
		} else {
			return "404";
		}
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String findAll(Model model) {
		List<Questionnaire> questionnaires = questionnaireRepository.findAll();
		
		model.addAttribute("questionnaires", questionnaires);
		
		return "questionnaires/list";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable String id, Model model) {
		
		Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
		if(questionnaire.isPresent()){
			model.addAttribute("questionnaire", questionnaire.get());
			return "questionnaires/show";
		}
		else {
			return "404";
		}

	}
}
