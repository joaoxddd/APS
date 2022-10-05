package br.com.refisme.financeiro.model;

public enum Sexo {
	M("Masculino"),
	F("Feminino"),
	N("Não Binário");
	
	private String descricao;
	
	Sexo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
