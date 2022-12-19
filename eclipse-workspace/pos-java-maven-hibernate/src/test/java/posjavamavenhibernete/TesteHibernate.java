package posjavamavenhibernete;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;
import posjavamavenhibernate.HibernateUtil;

public class TesteHibernate {
	/*
	 * @Test public void testeHibernateUtil() { DaoGeneric<UsuarioPessoa> daoGeneric
	 * = new DaoGeneric<UsuarioPessoa>(); UsuarioPessoa pessoa = new
	 * UsuarioPessoa(); pessoa.setEmail("rcm@mail.pt"); pessoa.setIdade(35);
	 * pessoa.setLogin("admin"); pessoa.setNome("Reinaldo Costa");
	 * pessoa.setSenha("admin"); pessoa.setSobrenome("Mendes");
	 * daoGeneric.salvar(pessoa); }
	 * 
	 * @Test public void testeBuscar() { DaoGeneric<UsuarioPessoa> daoGeneric = new
	 * DaoGeneric<UsuarioPessoa>(); UsuarioPessoa pessoa = new UsuarioPessoa();
	 * pessoa.setId(2L); pessoa = daoGeneric.pesquiar(pessoa);
	 * System.out.println(pessoa); }
	 * 
	 * @Test public void testeBuscar2() { DaoGeneric<UsuarioPessoa> daoGeneric = new
	 * DaoGeneric<UsuarioPessoa>(); UsuarioPessoa pessoa =
	 * daoGeneric.pesquiar(1L,UsuarioPessoa.class); System.out.println(pessoa); }
	 * 
	 * @Test public void testeUpdate() { DaoGeneric<UsuarioPessoa> daoGeneric = new
	 * DaoGeneric<UsuarioPessoa>(); UsuarioPessoa pessoa =
	 * daoGeneric.pesquiar(2L,UsuarioPessoa.class); pessoa.setNome("Admin");
	 * pessoa.setIdade(36); pessoa=daoGeneric.updateMerge(pessoa);
	 * System.out.println(pessoa); }
	 * 
	 * @Test public void testeDelete() { DaoGeneric<UsuarioPessoa> daoGeneric = new
	 * DaoGeneric<UsuarioPessoa>(); UsuarioPessoa pessoa =
	 * daoGeneric.pesquiar(1L,UsuarioPessoa.class);
	 * //daoGeneric.deletarPorId(pessoa); }
	 * 
	 * @Test public void testeConsultar() { DaoGeneric<UsuarioPessoa> daoGeneric =
	 * new DaoGeneric<UsuarioPessoa>(); List<UsuarioPessoa> lista =
	 * daoGeneric.listar(UsuarioPessoa.class); for(UsuarioPessoa u:lista) {
	 * System.out.println(u); } }
	 */
	@Test
	public void testeConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.listar(UsuarioPessoa.class);
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
	}

	@Test
	public void testeQueryeListNome() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa where nome='Reinas'")
				.getResultList();
		System.out.println("_______________TESTE______________");
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
		System.out.println("_______________FIM DO TESTE______________");
	}

	@Test
	public void testeQueryeListListMAxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa order by nome")
				.setMaxResults(3).getResultList();
		System.out.println("_______________TESTE Max3______________");
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
		System.out.println("_______________FIM DO TESTE Max3______________");
	}

	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
				.createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome")
				.setParameter("nome", "Admin").setParameter("sobrenome", "Costa").getResultList();
		System.out.println("_______________TESTE PARAM Admin______________");
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
		System.out.println("_______________FIM DO TESTE PARAM Admin______________");
	}

	@Test
	public void testeQueryeSomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		Long somaIdade = (Long) daoGeneric.getEntityManager().createQuery("Select sum(u.idade) from UsuarioPessoa u ")
				.getSingleResult();
		System.out.println("_______________TESTE SOMA IDADE______________");

		System.out.println("Soma de todas de idades é: " + somaIdade);

		System.out.println("_______________FIM DO TESTE SOMA IDADE______________");
	}

	@Test
	public void testeQueryeMediaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		Double somaIdade = (Double) daoGeneric.getEntityManager()
				.createQuery("Select avg(u.idade) from UsuarioPessoa u ").getSingleResult();
		System.out.println("_______________TESTE MEDIA IDADE______________");

		System.out.println("Media de todas de idades é: " + somaIdade);

		System.out.println("_______________FIM DO TESTE MEDIA IDADE______________");
	}

	@Test
	public void testeNAmedQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos")
				.getResultList();
		System.out.println("_______________TESTE NAMEDQUERY1______________");
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
		System.out.println("_______________FIM TESTE NAMEDQUERY1______________");
	}

	@Test
	public void testeNAmedQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> lista = daoGeneric.getEntityManager()
				.createNamedQuery("UsuarioPessoa.buscaPorNome")
				.setParameter("nome", "Admin")
				.getResultList();
		System.out.println("_______________TESTE NAMEDQUERY2______________");
		for (UsuarioPessoa u : lista) {
			System.out.println(u);
		}
		System.out.println("_______________FIM TESTE NAMEDQUERY2______________");
	}
	
	@Test
	public void testeGravaTelefone() {
		DaoGeneric daoGeneric = new DaoGeneric();	
		UsuarioPessoa pessoa =(UsuarioPessoa) daoGeneric.pesquiar(3L, UsuarioPessoa.class);
		TelefoneUser tel = new TelefoneUser();
		tel.setTipo("Movel");
		tel.setNuemro("91346666");
		tel.setUsuarioPessoa(pessoa);
		daoGeneric.salvar(tel);
	}
	
	@Test
	public void testeConsultaTelefonee() {
		DaoGeneric daoGeneric = new DaoGeneric();	
		UsuarioPessoa pessoa =(UsuarioPessoa) daoGeneric.pesquiar(3L, UsuarioPessoa.class);
		System.out.println("_______________TESTE TELEFONES______________");
	for(TelefoneUser tel: pessoa.getTelefoneUsers()) {
		System.out.println(tel);
	}
	System.out.println("_______________FIM TESTE TELEFONES______________");
	}
}
