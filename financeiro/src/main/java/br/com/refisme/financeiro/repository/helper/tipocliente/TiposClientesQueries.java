package br.com.refisme.financeiro.repository.helper.tipocliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.TipoCliente;
import br.com.refisme.financeiro.repository.filter.TipoClienteFilter;

public interface TiposClientesQueries {

	Page<TipoCliente> filtrar(TipoClienteFilter filtro, Pageable pageable);
}
