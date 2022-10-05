package br.com.refisme.financeiro.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.refisme.financeiro.model.PlanoConta;
import br.com.refisme.financeiro.repository.PlanosContas;
import br.com.refisme.financeiro.service.exception.ImpossivelExcluirEntidadeException;
import br.com.refisme.financeiro.service.exception.OrdemJaCadastradaException;

@Service
public class CadastroPlanoContaService {
	
	@Autowired
	private PlanosContas planosContas;
	
	@Transactional
	public PlanoConta salvar(PlanoConta planoConta){
		Optional<PlanoConta> planoContaOptional = planosContas
				.findByOrdemIgnoreCase(planoConta.getOrdem());
		
		if(planoContaOptional.isPresent() && !planoContaOptional.get().equals(planoConta))
			throw new OrdemJaCadastradaException("Ordem já cadastrada");
		
		return planosContas.save(planoConta);
	}
	
	@Transactional
	public void excluir(PlanoConta planoConta){
		try{
			planosContas.delete(planoConta);
			planosContas.flush();
		}catch(PersistenceException e){
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir o plano de conta");
		}
	}

}
