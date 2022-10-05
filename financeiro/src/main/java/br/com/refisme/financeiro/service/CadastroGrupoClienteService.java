package br.com.refisme.financeiro.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.GrupoCliente;
import br.com.refisme.financeiro.repository.GruposCliente;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroGrupoClienteService {

	@Autowired
	private GruposCliente gruposCliente;
	
	@Transactional
	public GrupoCliente salvar(GrupoCliente grupoCliente){
		return gruposCliente.save(grupoCliente);
	}
	
	@Transactional
	public void excluir(GrupoCliente grupoCliente){
		try{
			gruposCliente.delete(grupoCliente);
			gruposCliente.flush();
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir o estado.");
		}
	}
}
