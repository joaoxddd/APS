package br.com.refisme.financeiro.repository.helper.tipocliente;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.TipoCliente;
import br.com.refisme.financeiro.repository.filter.TipoClienteFilter;
import br.com.refisme.financeiro.repository.paginacao.PaginacaoUtil;

public class TiposClientesImpl implements TiposClientesQueries{

	@Autowired
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Page<TipoCliente> filtrar(TipoClienteFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(TipoCliente.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(TipoClienteFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(TipoCliente.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(TipoClienteFilter filtro, Criteria criteria) {
		if(filtro.getDescricao() != null)
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		else 
			criteria.addOrder(Order.asc("id"));
	}
	
	
}
