package br.com.refisme.financeiro.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.refisme.financeiro.controller.page.PageWrapper;
import br.com.refisme.financeiro.controller.validator.PlanoContaValidator;
import br.com.refisme.financeiro.model.Acesso;
import br.com.refisme.financeiro.model.Modelo;
import br.com.refisme.financeiro.model.Nivel;
import br.com.refisme.financeiro.model.PlanoConta;
import br.com.refisme.financeiro.model.SimNao;
import br.com.refisme.financeiro.model.Status;
import br.com.refisme.financeiro.model.Tipo;
import br.com.refisme.financeiro.repository.PlanosContas;
import br.com.refisme.financeiro.repository.filter.PlanoContaFilter;
import br.com.refisme.financeiro.service.CadastroPlanoContaService;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;
import br.com.refisme.financeiro.service.exception.OrdemJaCadastradaException;

@Controller
@RequestMapping("planosconta")
public class PlanosContasController {

	@Autowired
	private PlanosContas planosContas;
	
	@Autowired
	private CadastroPlanoContaService cadastroPlanoContaService;
	
	@Autowired
	private PlanoContaValidator planoContaValidator;
	
	@InitBinder("planoConta")
	public void inicializatValidador(WebDataBinder binder){
		binder.setValidator(planoContaValidator);
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(PlanoConta planoConta){
		ModelAndView mv = new ModelAndView("planoconta/CadastroPlanoConta");
		mv.addObject("tipo", Tipo.values());
		mv.addObject("nivel", Nivel.values());
		mv.addObject("acesso", Acesso.values());
		mv.addObject("modelo", Modelo.values());
		mv.addObject("status", Status.values());
		mv.addObject("simNao", SimNao.values());
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid PlanoConta planoConta, BindingResult result, 
			RedirectAttributes attributes){
		
		if(result.hasErrors())
			return novo(planoConta);
		
		try{
			cadastroPlanoContaService.salvar(planoConta);
		}catch(OrdemJaCadastradaException e){
			result.rejectValue("ordem", e.getMessage(), e.getMessage());
			return novo(planoConta);
		}
		
		attributes.addFlashAttribute("mensagem", "Plano de conta salvo com sucesso.");
		return new ModelAndView("redirect:/planosconta/" + planoConta.getId());
	}
	
	@GetMapping
	public ModelAndView pesquisar(PlanoContaFilter planoContaFilter, BindingResult result, 
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("planoconta/PesquisaPlanoConta");
		
		PageWrapper<PlanoConta> paginaWrapper = new PageWrapper<>(planosContas
				.filtrar(planoContaFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		PlanoConta planoConta = planosContas.findOne(id);
		
		try{
			cadastroPlanoContaService.excluir(planoConta);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		PlanoConta planoConta = planosContas.findOne(id);
		ModelAndView mv = novo(planoConta);
		mv.addObject(planoConta);
		return mv;
	}
}
