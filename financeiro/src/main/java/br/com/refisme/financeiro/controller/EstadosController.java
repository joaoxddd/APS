package br.com.refisme.financeiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.refisme.financeiro.controller.page.PageWrapper;
import br.com.refisme.financeiro.model.Estado;
import br.com.refisme.financeiro.repository.Estados;
import br.com.refisme.financeiro.repository.filter.EstadoFilter;
import br.com.refisme.financeiro.service.CadastroEstadoService;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("estados")
public class EstadosController {

	@Autowired
	private Estados estados;
	
	@Autowired 
	private CadastroEstadoService cadastroEstadoService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estado estado){
		return new ModelAndView("estado/CadastroEstado");
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Estado estado, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors())
			return novo(estado);
		
		cadastroEstadoService.salvar(estado);
		
		attributes.addFlashAttribute("mensagem", "Estado salvo com sucesso.");
		return new ModelAndView("redirect:/estados/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(EstadoFilter estadoFilter, @PageableDefault(size = 10) Pageable pageable,
			HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("estado/PesquisaEstado");
		
		PageWrapper<Estado> paginaWrapper = new PageWrapper<>(estados
				.filtrar(estadoFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		Estado estado = estados.findOne(id);
		try{
			cadastroEstadoService.excluir(estado);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		Estado estado = estados.findOne(id);
		ModelAndView mv = novo(estado);
		mv.addObject(estado);
		return mv;
	}
}
