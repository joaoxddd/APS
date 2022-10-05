package br.com.refisme.financeiro.model;

public enum Modelo {
	
	N("Normal"),
	E("Especial");
	
	private String descricao;
	
	Modelo(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
