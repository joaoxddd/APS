package br.com.refisme.financeiro.model;

public enum Tipo {
	R("Rendimento"), 
	F("Fornecedor"), 
	C("Custo Fixo"), 
	V("Custo Variável"), 
	E("Extra Operação");
	
	private String descricao;
	
	Tipo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}