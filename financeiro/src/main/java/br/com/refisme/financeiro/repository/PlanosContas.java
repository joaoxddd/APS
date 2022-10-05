package br.com.refisme.financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.refisme.financeiro.model.PlanoConta;
import br.com.refisme.financeiro.repository.helper.planoconta.PlanosContasQueries;

@Repository
public interface PlanosContas extends JpaRepository<PlanoConta, Long>, PlanosContasQueries{
	
	Optional<PlanoConta> findByOrdemIgnoreCase(String ordem);
}
