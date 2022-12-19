package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class UserPosDAO {
 private Connection connection;
 public UserPosDAO() {
	 connection = SingleConnection.getConnection();
 }
 public void salvar(Userposjava userposjava) {
	 String sql = "INSERT INTO userposjava(nome, email ) VALUES (?, ?)";
	 try {
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, userposjava.getNome());
		insert.setString(2, userposjava.getEmail());
		insert.execute();
		connection.commit();
	} catch (SQLException e) {	
		e.printStackTrace();
	}
	 		
 }
 public List<Userposjava> listar() throws SQLException{
	 List<Userposjava>  list = new ArrayList<Userposjava>(); 
	 String sql = "SELECT * FROM userposjava";
		PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultado =statement.executeQuery();
	while(resultado.next()) {
	Userposjava u = new Userposjava();
	u.setId(resultado.getLong("id"));
	u.setNome(resultado.getString("nome"));
	u.setEmail(resultado.getString("email"));
	list.add(u);
	}
	 return list;
 }
 
 public Userposjava buscar(Long id) throws SQLException{
	 Userposjava u = new Userposjava();
	 String sql = "SELECT * FROM userposjava WHERE id="+id;
		PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultado =statement.executeQuery();
	while(resultado.next()) {
	u.setId(resultado.getLong("id"));
	u.setNome(resultado.getString("nome"));
	u.setEmail(resultado.getString("email"));
	}
	 return u;
 }
 public void actualizar(Userposjava u) {
	 String sql = "UPDATE userposjava SET nome = ?   Where id="+u.getId();
	 try {
		PreparedStatement update = connection.prepareStatement(sql);
		update.setString(1, u.getNome());
		update.execute();
		connection.commit();
	} catch (SQLException e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		e.printStackTrace();
	} 
 }
 public void deletar(Long id) {
	 String sql = "DELETE from  userposjava    Where id="+id;
	 try {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		connection.commit();
	} catch (SQLException e) {
		try {
			connection.rollback();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		e.printStackTrace();
	} 
 }
 public void salvarTelefone(Telefone telefone) {
try {
	String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?,?, ?)";
	PreparedStatement insert = connection.prepareStatement(sql);
	insert.setString(1, telefone.getNumero());
	insert.setString(2, telefone.getTipo());
	insert.setLong(3,telefone.getUsuario());
	insert.execute();
	connection.commit();
} catch (Exception e) {
	e.printStackTrace();
	try {
		connection.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}	 
 }
 
 public List<BeanUserFone> listaUSerFone(Long iduser){
	 List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
	 String sql = " select nome, numero, email from telefoneuser as fone inner join userposjava as userp on fone.usuariopessoa = userp.id where userp.id="+iduser;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resulset = preparedStatement.executeQuery();
			while(resulset.next()) {
				BeanUserFone userFone = new BeanUserFone();
				
				userFone.setEmail(resulset.getString("email"));
				userFone.setNome(resulset.getString("nome"));
				userFone.setNumero(resulset.getString("numero"));
				
				beanUserFones.add(userFone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 return beanUserFones;
 }
 public void deleteFonesPorUser(Long idUser) {
	 String sqlFone = "delete from telefoneuser where usuariopessoa ="+idUser;
	 String sqlUser = "delete from userposjava where id="+idUser;
	 try {
		PreparedStatement statement = connection.prepareStatement(sqlFone);
		statement.executeUpdate();
		connection.commit();
		statement = connection.prepareStatement(sqlUser);
		statement.executeUpdate();
		connection.commit();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
 }
}
