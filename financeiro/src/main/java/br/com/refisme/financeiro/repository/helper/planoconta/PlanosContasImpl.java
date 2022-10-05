package br.com.refisme.financeiro.repository.helper.planoconta;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.PlanoConta;
import br.com.refisme.financeiro.repository.filter.PlanoContaFilter;
import br.com.refisme.financeiro.repository.paginacao.PaginacaoUtil;

public class PlanosContasImpl implements PlanosContasQueries{

	@Autowired
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Page<PlanoConta> filtrar(PlanoContaFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(PlanoConta.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(PlanoContaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(PlanoConta.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(PlanoContaFilter filtro, Criteria criteria) {
		criteria.addOrder(Order.asc("id"));
	}

}
