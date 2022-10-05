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
import br.com.refisme.financeiro.controller.validator.FormaPagamentoValidator;
import br.com.refisme.financeiro.model.FormaPagamento;
import br.com.refisme.financeiro.model.TipoPagamento;
import br.com.refisme.financeiro.repository.FormasPagamentos;
import br.com.refisme.financeiro.repository.filter.FormaPagamentoFilter;
import br.com.refisme.financeiro.service.CadastroFormaPagamentoService;
import br.com.refisme.financeiro.service.exception.DescricaoJaCadastradaException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("formaspagamentos")
public class FormasPagamentosController {

	@Autowired
	private FormasPagamentos formasPagamentos;
	
	@Autowired 
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoValidator formaPagamentoValidator;
	
	@InitBinder("formaPagamento")
	public void inicializarValidador(WebDataBinder binder){
		binder.setValidator(formaPagamentoValidator);
	}
	
	@RequestMapping("novo")
	public ModelAndView novo(FormaPagamento formaPagamento){
		ModelAndView mv = new ModelAndView("formapagamento/CadastroFormaPagamento");
		mv.addObject("tipoPagamento", TipoPagamento.values());
		return mv;
	}
	
	@RequestMapping(value = {"/novo", "{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid FormaPagamento formaPagamento, BindingResult result, 
			RedirectAttributes attributes){
		if(result.hasErrors())
			return novo(formaPagamento);
		
		try{
			cadastroFormaPagamentoService.salvar(formaPagamento);
		}catch(DescricaoJaCadastradaException e){
			result.rejectValue("descricao", e.getMessage(), e.getMessage());
			return novo(formaPagamento);
		}
		
		attributes.addFlashAttribute("mensagem", "Forma de pagamento salva com sucesso.");
		return new ModelAndView("redirect:/formaspagamentos/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(FormaPagamentoFilter formaPagamentoFilter, 
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest){
		ModelAndView mv = new ModelAndView("formapagamento/PesquisaFormaPagamento");
		
		PageWrapper<FormaPagamento> paginaWrapper = new PageWrapper<>(formasPagamentos
				.filtrar(formaPagamentoFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id){
		FormaPagamento formaPagamento = formasPagamentos.findOne(id);
		
		try{
			cadastroFormaPagamentoService.excluir(formaPagamento);
		}catch(ImpossivelExcluirEntidadeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id){
		FormaPagamento formaPagamento = formasPagamentos.findOne(id);
		ModelAndView mv = novo(formaPagamento);
		mv.addObject(formaPagamento);
		return mv;
	}
}
