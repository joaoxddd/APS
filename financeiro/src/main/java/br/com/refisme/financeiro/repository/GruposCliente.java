package br.com.refisme.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.refisme.financeiro.model.GrupoCliente;
import br.com.refisme.financeiro.repository.helper.grupocliente.GruposClienteQueries;

@Repository
public interface GruposCliente  extends JpaRepository<GrupoCliente, Long>, GruposClienteQueries{
	
}
