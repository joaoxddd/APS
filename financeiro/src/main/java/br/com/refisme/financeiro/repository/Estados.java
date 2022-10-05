package br.com.refisme.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.refisme.financeiro.model.Estado;
import br.com.refisme.financeiro.repository.helper.estado.EstadosQueries;

@Repository
public interface Estados extends JpaRepository<Estado, Long>, EstadosQueries{

}
