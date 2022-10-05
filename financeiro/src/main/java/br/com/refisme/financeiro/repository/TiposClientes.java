package br.com.refisme.financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.refisme.financeiro.model.TipoCliente;
import br.com.refisme.financeiro.repository.helper.tipocliente.TiposClientesQueries;

@Repository
public interface TiposClientes extends JpaRepository<TipoCliente, Long>, TiposClientesQueries{

	Optional<TipoCliente> findByDescricaoIgnoreCase(String descricao);
}
