package br.com.refisme.financeiro.model;

public enum Status {

	A("Ativo"),
	I("Inativo");
	
	private String descricao;
	
	Status(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
