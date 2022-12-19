package br.com.cursojsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;

@ViewScoped
@Named(value = "lancamentoBean")
public class LancamentoBean {
	private Lancamento lancamento = new Lancamento();
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	@Inject
	private IDaoLancamento daoLancamento;

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamento.setUsuario(pessoaUser);
		lancamento = daoGeneric.merge(lancamento);
		carregarLancamentos();
		FacesMessage message = new FacesMessage("Salvo com sucesso!");
		context.addMessage("msg", message);
		return "";
	}

	@PostConstruct
	private void carregarLancamentos() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamentos = daoLancamento.consultar(pessoaUser.getId());

	}

	public String editar() {
		return "";
	}

	public String novo() {
		lancamento = new Lancamento();
		carregarLancamentos();
		return "";
	}

	public String remover() {
		daoGeneric.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage("Eliminado com sucesso!");
		context.addMessage("msg", message);
		return "";
	}
}
