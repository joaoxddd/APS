package br.com.refisme.financeiro.repository.helper.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
}
