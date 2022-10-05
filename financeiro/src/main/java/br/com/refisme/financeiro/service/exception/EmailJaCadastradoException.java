package br.com.refisme.financeiro.service.exception;

public class EmailJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailJaCadastradoException(String message) {
		super(message);
	}

}
