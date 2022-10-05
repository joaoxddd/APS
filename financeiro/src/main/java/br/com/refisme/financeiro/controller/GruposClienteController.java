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
import br.com.refisme.financeiro.model.GrupoCliente;
import br.com.refisme.financeiro.repository.GruposCliente;
import br.com.refisme.financeiro.repository.filter.GrupoClienteFilter;
import br.com.refisme.financeiro.service.CadastroGrupoClienteService;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/gruposcliente")
public class GruposClienteController {

	@Autowired
	private GruposCliente gruposCliente;
	
	@Autowired
	private CadastroGrupoClienteService cadastroGrupoClienteService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(GrupoCliente grupoCliente){
		return new ModelAndView("grupocliente/CadastroGrupoCliente");
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid GrupoCliente grupoCliente, BindingResult result, 
			RedirectAttributes attributes){
		if(result.hasErrors())
			return novo(grupoCliente);
		
		cadastroGrupoClienteService.salvar(grupoCliente);
		
		attributes.addFlashAttribute("mensagem", "Grupo de cliente salvo com sucesso.");
		return new ModelAndView("redirect:/gruposcliente/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(GrupoClienteFilter grupoClienteFilter, 
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("grupocliente/PesquisaGrupoCliente");
		
		PageWrapper<GrupoCliente> paginaWrapper = new PageWrapper<>(gruposCliente
				.filtrar(grupoClienteFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		GrupoCliente grupoCliente = gruposCliente.findOne(id);
		try{
			cadastroGrupoClienteService.excluir(grupoCliente);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		GrupoCliente grupoCliente = gruposCliente.findOne(id);
		ModelAndView mv = novo(grupoCliente);
		mv.addObject(grupoCliente);
		return mv;
	}
}
