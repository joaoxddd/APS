package br.com.refisme.financeiro.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.FormaPagamento;
import br.com.refisme.financeiro.repository.FormasPagamentos;
import br.com.refisme.financeiro.service.exception.DescricaoJaCadastradaException;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroFormaPagamentoService {

	@Autowired
	private FormasPagamentos formasPagamentos;
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento){
		Optional<FormaPagamento> formaPagamentoOptional = formasPagamentos
				.findByDescricaoIgnoreCase(formaPagamento.getDescricao());
		
		if(formaPagamentoOptional.isPresent() && !formaPagamentoOptional.get().equals(formaPagamento))
			throw new DescricaoJaCadastradaException("Descrição já cadastrada");
		
		return formasPagamentos.save(formaPagamento);
	}
	
	@Transactional
	public void excluir(FormaPagamento formaPagamento){
		try{
			formasPagamentos.delete(formaPagamento);
			formasPagamentos.flush();
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir a forma de pagamento.");
		}
	}
}
