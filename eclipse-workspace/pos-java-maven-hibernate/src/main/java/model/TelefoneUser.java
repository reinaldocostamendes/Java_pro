package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TelefoneUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String tipo;

	@Column(nullable = false)
	private String nuemro;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UsuarioPessoa usuarioPessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNuemro() {
		return nuemro;
	}

	public void setNuemro(String nuemro) {
		this.nuemro = nuemro;
	}

	public UsuarioPessoa getUsuarioPessoa() {
		return usuarioPessoa;
	}

	@Override
	public String toString() {
		return "TelefoneUser [id=" + id + ", tipo=" + tipo + ", nuemro=" + nuemro + ", usuarioPessoa=" + usuarioPessoa.getNome()
				+ "]";
	}

	public void setUsuarioPessoa(UsuarioPessoa pesUsuarioPessoa) {
		this.usuarioPessoa = pesUsuarioPessoa;
	}

}
