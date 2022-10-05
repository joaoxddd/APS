package br.com.refisme.financeiro.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.Estado;
import br.com.refisme.financeiro.repository.Estados;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroEstadoService {

	@Autowired
	private Estados estados;
	
	@Transactional
	public Estado salvar(Estado estado){
		return estados.save(estado);
	}
	
	@Transactional
	public void excluir(Estado estado){
		try{
			estados.delete(estado);
			estados.flush();
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir o estado");
		}
	}
}
