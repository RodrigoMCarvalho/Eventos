package br.com.eventos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.eventos.models.Convidado;
import br.com.eventos.models.Evento;
import br.com.eventos.repository.ConvidadoRepository;
import br.com.eventos.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er; //usado para salvar/excluir/editar/buscar um evento no BD
	
	@Autowired
	private ConvidadoRepository cr; //usado para salvar/excluir/editar/buscar um convidado no BD
	
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET) //get retorna o formulário
	public String form() {
		return "evento/formEvento";
	}
	
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST) //salvar os dados
	public String form(@Valid Evento evento, BindingResult resultado, RedirectAttributes atributos) {
		
		if (resultado.hasErrors()) { //caso deixe algum campo em branco
			atributos.addFlashAttribute("mensagem", "Erro! Por favor, verifique os campos.");
			return "redirect:/cadastrarEvento";
		}
		
		er.save(evento);
		atributos.addFlashAttribute("mensagem", "Evento adicionado com sucesso!"); //"mensagem" se refere a <span th:text=${mensagem}></span>
																					  //de mensagemValidacao.html
		return "redirect:/cadastrarEvento"; //após persistir os dados, redireciona para a página
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {       //método para listar todos os eventos
		ModelAndView mv = new ModelAndView("index"); //
		Iterable<Evento> listaEventos = er.findAll();
		mv.addObject("eventos",listaEventos);  //eventos é o ${eventos} do index
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento"); //retorna para a view
		mv.addObject("evento", evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento); //lista de convidados por evento
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult resultado, RedirectAttributes atributos) {
		
		if (resultado.hasErrors()) { //caso deixe algum campo em branco
			atributos.addFlashAttribute("mensagem", "Erro! Por favor, verifique os campos.");
			return "redirect:/{codigo}";
		}
		
		Evento evento = er.findByCodigo(codigo); //busca pelo código recebido como parâmetro
		convidado.setEvento(evento); //associa o evento encontrado ao convidado
		cr.save(convidado); //persiste o convidado no BD
		atributos.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!"); //"mensagem" se refere a <span th:text=${mensagem}></span>
		return "redirect:/{codigo}"; 												  //de mensagemValidacao.html
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();   //obtém o evento do convidado
		long codLong = evento.getCodigo();		//obtém o código do evento em Long
		String codigo = "" + codLong;           //converte para String
		
		return "redirect:" + codigo;
	}
	
	
	
	
	
	
	
	
	
}
