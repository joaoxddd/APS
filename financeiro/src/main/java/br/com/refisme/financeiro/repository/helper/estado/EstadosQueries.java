package br.com.refisme.financeiro.repository.helper.estado;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.Estado;
import br.com.refisme.financeiro.repository.filter.EstadoFilter;

public interface EstadosQueries {

	Page<Estado> filtrar(EstadoFilter filtro, Pageable pageable);
}
