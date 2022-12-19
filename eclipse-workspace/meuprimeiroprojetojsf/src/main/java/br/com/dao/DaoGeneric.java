package br.com.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;
@Named
public class DaoGeneric<E> implements Serializable{
	@Inject
	private EntityManager entityManager;
	@Inject
	private JPAUtil jpaUtil;
	public void salvar(E entidade) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);
		transaction.commit();
		entityManager.close();
	}

	public void delete(E entidade) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.remove(entidade);
		transaction.commit();
		
	}

	public void deletePorId(E entidade) {
	
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Object id = jpaUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id)
				.executeUpdate();
//entityManager.remove(entidade);
		transaction.commit();
		
	}

	public E merge(E entidade) {
	
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E retorno = entityManager.merge(entidade);
		transaction.commit();
		
		return retorno;
	}

	public List<E> getListEntity(Class<E> entidade) {
	
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		transaction.commit();
		
		return lista;
	}

	public List<E> getListEntityLimite10(Class<E> entidade) {
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName() +" order by id desc").setMaxResults(10).getResultList();
		transaction.commit();
		
		return lista;
	}
	public E consultar(Class<E> entidade, String codigo) {
	
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
		transaction.commit();
		//entityManager.close();
		return objeto;
	}
}
