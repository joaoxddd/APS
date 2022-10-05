
package br.com.refisme.financeiro.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.refisme.financeiro.controller.page.PageWrapper;
import br.com.refisme.financeiro.controller.validator.UsuarioValidator;
import br.com.refisme.financeiro.model.Status;
import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.Usuarios;
import br.com.refisme.financeiro.repository.filter.UsuarioFilter;
import br.com.refisme.financeiro.service.CadastroUsuarioService;
import br.com.refisme.financeiro.service.exception.EmailJaCadastradoException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;
import br.com.refisme.financeiro.service.exception.NomeJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@InitBinder("usuario")
	public void inicializarValidator(WebDataBinder binder) {
		binder.setValidator(usuarioValidator);
	}

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		return new ModelAndView("usuario/CadastroUsuario");
	}
	
	@RequestMapping(value = { "/novo", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result,
			RedirectAttributes attributes) {
		Optional<Usuario> usuariosIdOptional = usuarios.findById(usuario.getId());
		
		
		if(usuario.getId() == null){
			if (result.hasErrors() && usuario.getSenha().length() < 1) {
				result.rejectValue("senha", "Senha é obrigatória", "Senha é obrigatória");
				return novo(usuario);
			}else if(result.hasErrors()){
				return novo(usuario);
			}else if(usuario.getSenha().length() < 1){
				result.rejectValue("senha", "Senha é obrigatória", "Senha é obrigatória");
				return novo(usuario);
			}
		}else{
			if(usuario.getSenha() == ""){
				if(result.hasErrors()){
					return novo(usuario);
				}
				usuario.setSenha(usuariosIdOptional.get().getSenha());
			}else{
				if (result.hasErrors() && usuario.getSenha().length() < 1) {
					result.rejectValue("senha", "Senha é obrigatória", "Senha é obrigatória");
					return novo(usuario);
				}else if(result.hasErrors()){
					return novo(usuario);
				}else if(usuario.getSenha().length() < 1){
					result.rejectValue("senha", "Senha é obrigatória", "Senha é obrigatória");
					return novo(usuario);
				}
			}
		}
		
		try {
			cadastroUsuarioService.salvar(usuario);

		} catch (EmailJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (NomeJaCadastradoException e) {
			result.rejectValue("funcionario.id", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return new ModelAndView("redirect:/usuarios/"+usuario.getId());
	}


	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarios
				.filtrar(usuarioFilter, pageable), httpServletRequest);
		mv.addObject("status", Status.values());
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Usuario> pesquisar(String email) {
		validarTamanhoDescricao(email);
		return usuarios.findByEmailStartingWithIgnoreCase(email);
	}
	
	private void validarTamanhoDescricao(String email) {
		if (StringUtils.isEmpty(email) || email.length() < 1) {
			
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("id") Long id) {
		Usuario usuario = usuarios.findOne(id);
		
		try {
			cadastroUsuarioService.excluir(usuario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		Usuario usuario = usuarios.findOne(id);
		mv.addObject(usuario);
		return mv;
	}
	
}
