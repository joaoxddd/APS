package br.com.refisme.financeiro.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(name = "nome_tratamento")
	private String nomeTratamento;

	@Enumerated(EnumType.ORDINAL)
	private Sexo sexo;

	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@ManyToOne(optional = true)
	@JoinColumn(name = "naturalidade_id", nullable = true)
	private Cidade naturalidade;

	@Column(name = "nome_pai")
	private String nomePai;

	@Column(name = "nome_mae")
	private String nomeMae;

	@ManyToOne(optional = true)
	@JoinColumn(name = "profissao_id", nullable = true)
	private Profissao profissao;

	private String cpf;

	private String rg;

	private String pis;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cargo_id", nullable = true)
	private Cargo cargo;

	@Column(name = "data_admissao")
	private LocalDate dataAdmissao;

	private boolean status = true;

	@Column(name = "observacao")
	private String observacoes;

	@PrePersist
	@PreUpdate
	private void prePersistPreUpdate() {
		this.cpf = this.cpf.replaceAll("\\.|-", "");
		this.pis = this.pis.replaceAll("\\.|-", "");
	}

	@PostLoad
	private void postLoad() {
		this.cpf = this.cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		this.pis = this.pis.replaceAll("(\\d{3})(\\d{5})(\\d{2})", "$1.$2.$3-");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeTratamento() {
		return nomeTratamento;
	}

	public void setNomeTratamento(String nomeTratamento) {
		this.nomeTratamento = nomeTratamento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Cidade getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Cidade naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	public Profissao getProfissao() {
		return profissao;
	}
	
	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public boolean isNovo() {
		return this.id == null;
	}

	public String getCpfSemFormatacao() {
		return cpf.replaceAll("\\.|-", "");
	}

	public String getDataAdmissaoFormatada() {
		if (this.dataAdmissao != null) {
			return String.format("%s/%s/%d", formatar(this.dataAdmissao.getDayOfMonth()),
					formatar(this.dataAdmissao.getMonthValue()), this.dataAdmissao.getYear());
		}
		return "";
	}

	public String getCargoFinal() {
		if (this.cargo != null) {
			return this.cargo.getDescricao();
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private String formatar(int valor) {
		if (Integer.toString(valor).length() == 1) {
			return '0' + Integer.toString(valor);
		}
		return Integer.toString(valor);
	}
}
