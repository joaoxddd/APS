package br.com.refisme.financeiro.repository.helper.formapagamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.FormaPagamento;
import br.com.refisme.financeiro.repository.filter.FormaPagamentoFilter;

public interface FormasPagamentosQueries {

	Page<FormaPagamento> filtrar (FormaPagamentoFilter filtro, Pageable pageable);
}
