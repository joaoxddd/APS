package br.com.refisme.financeiro.repository.helper.planoconta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.refisme.financeiro.model.PlanoConta;
import br.com.refisme.financeiro.repository.filter.PlanoContaFilter;

public interface PlanosContasQueries {

	Page<PlanoConta> filtrar(PlanoContaFilter filtro, Pageable pageable);
}
