package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import dao.UserPosDAO;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc{
	
	@Test
public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();
		userposjava.setNome("Matheus Nunes");
		userposjava.setEmail("matheus@gmail.com");
		userPosDAO.salvar(userposjava);
}
	
	
	@Test
public void initListar() {
		UserPosDAO dao= new UserPosDAO();
	try {
		List<Userposjava> lista = dao.listar();
		for(Userposjava u:lista) {
			System.out.println(u);
			System.out.println("----------------------------");
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}
	
	@Test
public void initBuscar() {
		UserPosDAO dao= new UserPosDAO();
	try {
	Userposjava u = dao.buscar(2L);
			System.out.println(u);
			System.out.println("----------------------------");
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}
	
	@Test
public void initActualizar() {
		UserPosDAO dao= new UserPosDAO();
	try {
	Userposjava u = dao.buscar(2L);
			System.out.println(u);
			u.setNome("Reinaldo Mendes Actualizado!");
			System.out.println("----------------------------");
			dao.actualizar(u);
			Userposjava u2 = dao.buscar(2L);
			System.out.println(u2);
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
}
	
	@Test
public void initDeletar() {
		UserPosDAO dao= new UserPosDAO();
	dao.deletar(8L);
}
	@Test
	public void testeInserTelefone() {
	Telefone tel = new Telefone();
	tel.setNumero("21455565");
	tel.setTipo("Fixo");
	tel.setUsuario(11L);
	UserPosDAO dao = new UserPosDAO();
	dao.salvarTelefone(tel);
	}
	@Test
	 public void testeCarregaFonesUser() {
		 UserPosDAO dao = new UserPosDAO();
		 List<BeanUserFone> listaBeaFU = dao.listaUSerFone(11L);
		 System.out.println("-----TELEFONES DE USUARIOS------");
		 for(BeanUserFone b: listaBeaFU) {
			 System.out.println(b);
		 }
	 }
	
	public void  testeDeleteUserFone() {
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(12L);
	}
}
