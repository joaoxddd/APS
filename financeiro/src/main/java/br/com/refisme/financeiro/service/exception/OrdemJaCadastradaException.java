package br.com.refisme.financeiro.service.exception;

public class OrdemJaCadastradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public OrdemJaCadastradaException(String mensagem){
		super(mensagem);
	}
}
