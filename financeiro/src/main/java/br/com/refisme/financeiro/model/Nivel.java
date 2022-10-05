package br.com.refisme.financeiro.model;

public enum Nivel {
	
	G("Grupo"),
	S("Subgrupo"),
	C("Conta");
	
	private String descricao;
	
	Nivel(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
