package br.com.refisme.financeiro.model;

public enum TipoPagamento {
	
	B("Boleto"),
	C("Cartão"),
	H("Cheque"),
	O("Conta"),
	D("Dinheiro");

	private String descricao;
	
	TipoPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
}
