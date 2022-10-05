package br.com.refisme.financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.refisme.financeiro.model.FormaPagamento;
import br.com.refisme.financeiro.repository.helper.formapagamento.FormasPagamentosQueries;

@Repository
public interface FormasPagamentos extends JpaRepository<FormaPagamento, Long>, FormasPagamentosQueries{

	Optional<FormaPagamento> findByDescricaoIgnoreCase(String descricao);
}
