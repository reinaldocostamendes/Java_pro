package br.com.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.entidades.Pessoa;
@Named
public class IDaoPessoaImpl implements IDaoPessoa {
	@Inject
private EntityManager entityManager;
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		Pessoa pessoa = null;
try {
		pessoa = (Pessoa) entityManager
				.createQuery("select p from Pessoa p where p.login = '" + login + "' and  p.senha = '" + senha+"'")
				.getSingleResult();
}catch (javax.persistence.NoResultException e) {//se não encontrar usuario
	// TODO: handle exception
}
	
		return pessoa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
	
		List<Estados> estados =entityManager.createQuery("from Estados").getResultList();
		for(Estados estado: estados) {
			selectItems.add(new SelectItem(estado,estado.getNome()));	
		}
		return selectItems;
	}

	@Override
	public List<Pessoa> relatorioPessoa(String nome, Date dataIni, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		sql.append(" select p from Pessoa p ");
		if(dataIni==null && dataFim==null && nome!=null) {
		sql.append(" where upper(p.nome) like '%").append(nome.trim().toUpperCase()).append("%'");	
		}else if(nome==null ||(nome!=null && nome.isEmpty())&& dataIni !=null &&dataFim==null) {
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where p.dataNascimento >= '").append(dataIniString).append("'");
		}else if(nome==null ||(nome!=null && nome.isEmpty())&& dataFim !=null &&dataIni==null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append(" where p.dataNascimento <= '").append(dataFimString).append("'");
		}
		else if(nome==null ||(nome!=null && nome.isEmpty())&& dataFim !=null &&dataIni!=null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where p.dataNascimento >= '").append(dataIniString).append("' ");
			sql.append(" and p.dataNascimento <= '").append(dataFimString).append("'");
		}
		else if((nome!=null && !nome.isEmpty())&& dataFim !=null &&dataIni!=null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where upper(p.nome) like '%").append(nome.trim().toUpperCase()).append("%' ");
			sql.append(" and p.dataNascimento >= '").append(dataIniString).append("' ");
			sql.append(" and p.dataNascimento <= '").append(dataFimString).append("'");
		}
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		pessoas =entityManager.createQuery(sql.toString()).getResultList();
		transaction.commit();
		return pessoas;
	}

}
