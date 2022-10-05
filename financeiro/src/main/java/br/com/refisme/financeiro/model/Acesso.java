package br.com.refisme.financeiro.model;

public enum Acesso {

	T("Total"),
	G("Gerência");
	
	private String descricao;
	
	Acesso(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
