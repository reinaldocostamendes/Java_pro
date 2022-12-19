package br.com.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento {
	@Inject
	private EntityManager entityManager;

	@Override
	public List<Lancamento> consultar(Long codeUser) {
		List<Lancamento> lancamentos = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		lancamentos = entityManager.createQuery(" from Lancamento where usuario.id = " + codeUser).getResultList();
		transaction.commit();
		return lancamentos;
	}

	@Override
	public List<Lancamento> consultarLimite10(Long codeUser) {
		List<Lancamento> lancamentos = null;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		lancamentos = entityManager
				.createQuery(" from Lancamento where usuario.id = " + codeUser + " order by id desc ").setMaxResults(10)
				.getResultList();
		transaction.commit();
		return lancamentos;
	}

	@Override
	public List<Lancamento> relatorioLancamento(String nota, Date dataIni, Date dataFim) {
		StringBuilder sql = new StringBuilder();
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		sql.append(" select l from Lancamento l ");
		if(dataIni==null && dataFim==null && nota!=null) {
		sql.append(" where l.numeroNotaFiscal = '").append(nota.trim()).append("'");	
		}else if(nota==null ||(nota!=null && nota.isEmpty())&& dataIni !=null &&dataFim==null) {
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where l.dataIni >= '").append(dataIniString).append("'");
		}else if(nota==null ||(nota!=null && nota.isEmpty())&& dataFim !=null &&dataIni==null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append(" where l.dataFin <= '").append(dataFimString).append("'");
		}
		else if(nota==null ||(nota!=null && nota.isEmpty())&& dataFim !=null &&dataIni!=null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where l.dataIni >= '").append(dataIniString).append("' ");
			sql.append(" and l.dataFin <= '").append(dataFimString).append("'");
		}
		else if((nota!=null && !nota.isEmpty())&& dataFim !=null &&dataIni!=null) {
			String dataFimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where l.numeroNotaFiscal = '").append(nota.trim()).append("' ");
			sql.append(" and l.dataIni >= '").append(dataIniString).append("' ");
			sql.append(" and l.dataFin <= '").append(dataFimString).append("'");
		}
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		lancamentos =entityManager.createQuery(sql.toString()).getResultList();
		transaction.commit();
		return lancamentos;
	}
}
