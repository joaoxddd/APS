package br.com.refisme.financeiro.repository.helper.usuario;

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

import br.com.refisme.financeiro.model.Status;
import br.com.refisme.financeiro.model.Usuario;
import br.com.refisme.financeiro.repository.filter.UsuarioFilter;
import br.com.refisme.financeiro.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries{

	@Autowired
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if(filtro.getEmail() != null || filtro.getFuncionario() != null ||
				filtro.getStatus() != null ){
			
			if(filtro.getFuncionario().getId() != null)
				criteria.add(Restrictions.eq("funcionario.id", filtro.getFuncionario().getId()));
			
			if(filtro.getEmail() != null)
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
			
			if(filtro.getStatus() == Status.A)
				criteria.add(Restrictions.eq("status", true));
			
			if(filtro.getStatus() == Status.I)
				criteria.add(Restrictions.eq("status", false));
		}else
			criteria.addOrder(Order.asc("id"));
	}
}