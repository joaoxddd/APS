package br.com.refisme.financeiro.service.exception;

public class DescricaoJaCadastradaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DescricaoJaCadastradaException(String mensagem){
		super(mensagem);
	}
}