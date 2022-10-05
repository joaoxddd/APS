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
import br.com.refisme.financeiro.model.TipoCliente;
import br.com.refisme.financeiro.repository.TiposClientes;
import br.com.refisme.financeiro.repository.filter.TipoClienteFilter;
import br.com.refisme.financeiro.service.CadastroTipoClienteService;
import br.com.refisme.financeiro.service.exception.DescricaoJaCadastradaException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("tiposclientes")
public class TiposClientesController {

	@Autowired
	private TiposClientes tiposClientes;
	
	@Autowired
	private CadastroTipoClienteService cadastroTipoClienteService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(TipoCliente tipoCliente){
		return new ModelAndView("tipocliente/CadastroTipoCliente");
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid TipoCliente tipoCliente, BindingResult result,
			RedirectAttributes attributes){
		if(result.hasErrors())
			return novo(tipoCliente);
		
		try{
			cadastroTipoClienteService.salvar(tipoCliente);
		}catch(DescricaoJaCadastradaException e){
			result.rejectValue("descricao", e.getMessage(), e.getMessage());
			return novo(tipoCliente);
		}
		
		attributes.addFlashAttribute("mensagem", "Tipo de cliente salvo com sucesso.");
		return new ModelAndView("redirect:/tiposclientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(TipoClienteFilter tipoClienteFilter, 
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("tipocliente/PesquisaTipoCliente");
		
		PageWrapper<TipoCliente> paginaWrapper = new PageWrapper<>(tiposClientes
				.filtrar(tipoClienteFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		TipoCliente tipoCliente = tiposClientes.findOne(id);
		try{
			cadastroTipoClienteService.excluir(tipoCliente);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		TipoCliente tipoCliente = tiposClientes.findOne(id);
		ModelAndView mv = novo(tipoCliente);
		mv.addObject(tipoCliente);
		return mv;
	}
}
