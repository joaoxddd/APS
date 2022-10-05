package br.com.refisme.financeiro.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.TipoCliente;
import br.com.refisme.financeiro.repository.TiposClientes;
import br.com.refisme.financeiro.service.exception.DescricaoJaCadastradaException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroTipoClienteService {

	@Autowired
	private TiposClientes tiposClientes;
	
	@Transactional
	public TipoCliente salvar(TipoCliente tipoCliente){
		Optional<TipoCliente> tipoClienteOptional = tiposClientes
				.findByDescricaoIgnoreCase(tipoCliente.getDescricao());
		
		if(tipoClienteOptional.isPresent() && !tipoClienteOptional.get().equals(tipoCliente))
			throw new DescricaoJaCadastradaException("Tipo de cliente já cadastrado.");
		
		return tiposClientes.save(tipoCliente);
	}
	
	@Transactional
	public void excluir(TipoCliente tipoCliente){
		try{
			tiposClientes.delete(tipoCliente);
			tiposClientes.flush();
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir o tipo de cliente.");
		}
	}
}
