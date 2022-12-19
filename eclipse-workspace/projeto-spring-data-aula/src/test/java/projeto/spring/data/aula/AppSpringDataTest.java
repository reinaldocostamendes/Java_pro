package projeto.spring.data.aula;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import projeto.spring.data.aula.dao.InterfaceSpringDataUser;
import projeto.spring.data.aula.dao.InterfaceTelefone;
import projeto.spring.data.aula.model.Telefone;
import projeto.spring.data.aula.model.UsuarioSpringData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring-config.xml"})
public class AppSpringDataTest {
	@Autowired
	private InterfaceSpringDataUser interfaceSpringDataUser;
	@Autowired
	private InterfaceTelefone interfaceTelefone;
@Test
public void testInsert() {
UsuarioSpringData user = new UsuarioSpringData();
user.setEmail("rcm_reinas@hotmail.com");
user.setIdade(35);
user.setLogin("reinaldo");
user.setSenha("admin");
user.setNome("Costa Mendes, Reinaldo");
interfaceSpringDataUser.save(user);
System.out.println("Usuarios inseridos -> "+interfaceSpringDataUser.count());
}
@Test
public void consulta() {
Optional<UsuarioSpringData> u = interfaceSpringDataUser.findById(1L);
System.out.println(u.get().getNome());
System.out.println(u.get().getEmail());
System.out.println(u.get().getLogin());
System.out.println(u.get().getSenha());
System.out.println(u.get().getIdade());
for (Telefone tel : u.get().getTelefones()){
	System.out.println("Tel:");
	System.out.println(tel.getTipo());
	System.out.println(tel.getNumero());		
}
System.out.println("fim de consulta");
}
@Test
public void consultaTodos() {
	System.out.println("Lista todos-------");
Iterable<UsuarioSpringData> lista = interfaceSpringDataUser	.findAll();
for (UsuarioSpringData u : lista) {
	System.out.println(u.getNome());
	System.out.println(u.getEmail());
	System.out.println(u.getLogin());
	System.out.println(u.getSenha());
	System.out.println(u.getIdade());
	for (Telefone tel : u.getTelefones()){
		System.out.println("Tel:");
		System.out.println(tel.getTipo());
		System.out.println(tel.getNumero());		
	}
	System.out.println("--------------------------------------------");	
}
}
@Test
public void consultaTNome() {
	System.out.println("Busca por nome-------");
List<UsuarioSpringData> lista = interfaceSpringDataUser.buscaPorNome("Costa");
for (UsuarioSpringData u : lista) {
	System.out.println(u.getNome());
	System.out.println(u.getEmail());
	System.out.println(u.getLogin());
	System.out.println(u.getSenha());
	System.out.println(u.getIdade());
	System.out.println("--------------------------------------------");	
}

}

@Test
public void consultaTNomeParam() {
	System.out.println("Busca por nome param-------");
UsuarioSpringData u = interfaceSpringDataUser.buscaPorNomeParam("Reinaldo da Costa Mendes");

	System.out.println(u.getNome());
	System.out.println(u.getEmail());
	System.out.println(u.getLogin());
	System.out.println(u.getSenha());
	System.out.println(u.getIdade());
	System.out.println("--------------------------------------------");	


}

@Test
public void actualizar() {
	Optional<UsuarioSpringData> u = interfaceSpringDataUser.findById(1L);
	UsuarioSpringData data = u.get();
	data.setNome("Reinaldo da Costa Mendes");
	data.setLogin("admin");
	interfaceSpringDataUser.save(data);
}
@Test
public void apagar() {
interfaceSpringDataUser.deleteById(5L);	
}

@Test
public void apagarPorNome() {
interfaceSpringDataUser.deletePorNome("Costa Mendes");
}
@Test
public void ActualizarEmailPorNome() {
interfaceSpringDataUser.updateEmailPorNome("admin@reinaldo.com", "Reinaldo da Costa Mendes");
}


@Test
public void testInsertTelefone() {
Telefone tlf = new Telefone();
tlf.setTipo("Fixo");
tlf.setNumero("+351 21546652");
UsuarioSpringData u = interfaceSpringDataUser.buscaPorNomeParam("Reinaldo da Costa Mendes");
tlf.setUsuarioSpringData(u);
interfaceTelefone.save(tlf);
}
}