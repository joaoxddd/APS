package br.com.refisme.financeiro.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.Usuarios;
import br.com.refisme.financeiro.service.exception.EmailJaCadastradoException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;
import br.com.refisme.financeiro.service.exception.NomeJaCadastradoException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioEmailOptinal = usuarios.findByEmailIgnoreCase(usuario.getEmail());
		Optional<Usuario> usuarioOptinal = usuarios
				.findByFuncionarioNomeIgnoreCase(usuario.getFuncionario().getNome());
		
		if (usuarioEmailOptinal.isPresent() && !usuarioEmailOptinal.get().equals(usuario)) {
			throw new EmailJaCadastradoException("E-mail já cadastrado");
		}
		
		if (usuarioOptinal.isPresent() && !usuarioOptinal.get().equals(usuario)) {
			throw new NomeJaCadastradoException("Usuário já cadastrado");
		}
		
		return usuarios.save(usuario);
	}
	
	@Transactional
	public void excluir(Usuario usuario) {
		try {
			usuarios.delete(usuario);
			usuarios.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar usuário. Já está atrelada");
		}
	}

	public Usuario findOne(Long id) {
		return usuarios.findOne(id);
	}
}

