package br.com.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.eventos.models.Evento;
import br.com.eventos.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET) //get retorna o formulário
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST) //salvar os dados
	public String form(Evento evento) {
		
		er.save(evento);
		
		return "redirect:/cadastrarEvento"; //após persistir os dados, redireciona para a página
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {       //método para listar todos os eventos
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> listaEventos = er.findAll();
		mv.addObject("eventos",listaEventos);  //eventos é o ${eventos} do index
		return mv;
	}
	
	@RequestMapping("/{codigo}")
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
