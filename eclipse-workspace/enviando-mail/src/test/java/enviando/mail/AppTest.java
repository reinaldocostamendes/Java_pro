package enviando.mail;

import org.junit.Test;

public class AppTest {
	@Test
	public void testeEmail() throws Exception {
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		stringBuilderTextoEmail.append("<h1>OLÁ</h1>");
		stringBuilderTextoEmail.append("<img src='https://reinaldocostamendes.com/external/promo.png'/>");
		stringBuilderTextoEmail.append("<br/><br/>");
		stringBuilderTextoEmail
				.append("<h3>Esta é a descrição do envio de email em formato html com java</h3><br/><br/>");
		stringBuilderTextoEmail.append("<b>Login: </b>admin<br/>");
		stringBuilderTextoEmail.append("<b>Password: </b>admin<br/><br/>");
		stringBuilderTextoEmail.append(
				"<a style='color:#2525a7;font-weight:bolder;font-size:18px;display:inline-block;font-family:courier; padding:18px;border-radius:30px; border:3px solid green; background:#99DA39; text-decoration:none; text-align:center;' href='https://reinaldomendes.com' target='_blank'>Visite a minha pagina Pessoal</a><br/><br/>");
		stringBuilderTextoEmail.append("<span style='font-size:8px;'>Ass.: Reinaldo Mendes</span><br/><br/>");
		ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("rcm_reinas@hotmail.com", "Reinaldo Mendes -envio com java",
				"Testando envio com java", "Este texto eé a descrição do meu email");
		// enviaEmail.enviarEmail(false);
		// Thread.sleep(5000);
		ObjetoEnviaEmail enviaEmail2 = new ObjetoEnviaEmail("reinaldocmen@gmail.com", "Reinaldo Mendes -envio com java",
				"Testando envio com java", stringBuilderTextoEmail.toString());
		enviaEmail2.enviarEmailAnexo(true);
		Thread.sleep(5000);

	}
}
