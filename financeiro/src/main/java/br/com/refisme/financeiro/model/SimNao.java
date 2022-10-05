package br.com.refisme.financeiro.model;

public enum SimNao {

	S("Sim"),
	N("Não");
	
	private String descricao;
	
	SimNao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
