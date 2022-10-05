package br.com.refisme.financeiro.repository.filter;

import br.com.refisme.financeiro.model.Funcionario;
import br.com.refisme.financeiro.model.Status;

public class UsuarioFilter {

	private String email;
	private String nomeFuncionario;
	private Status status;
	private Funcionario funcionario;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}