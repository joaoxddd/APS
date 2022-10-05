package br.com.refisme.financeiro.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.Usuarios;
import br.com.refisme.financeiro.security.UsuarioSistema;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarios.findByEmailAndStatus(email, true);
		Usuario usuario = usuarioOptional.orElseThrow( 
				() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new UsuarioSistema(usuario, new HashSet<>());
	}

}
