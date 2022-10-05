package br.com.refisme.financeiro.repository.helper.grupocliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.GrupoCliente;
import br.com.refisme.financeiro.repository.filter.GrupoClienteFilter;

public interface GruposClienteQueries {

	Page<GrupoCliente> filtrar(GrupoClienteFilter filtro, Pageable pageable);
}
