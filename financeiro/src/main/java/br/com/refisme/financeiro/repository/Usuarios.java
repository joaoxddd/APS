package br.com.refisme.financeiro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries{
	
	Optional<Usuario> findById(Long id);
	
	Optional<Usuario> findByFuncionarioId(Long id);
	
	Optional<Usuario> findByEmailIgnoreCase(String email);
	
	List<Usuario> findByEmailStartingWithIgnoreCase(String email);

	Optional<Usuario> findByFuncionarioNomeIgnoreCase(String nome);
	
	Optional<Usuario> findByEmailAndStatus(String email, boolean Status);
	
}
