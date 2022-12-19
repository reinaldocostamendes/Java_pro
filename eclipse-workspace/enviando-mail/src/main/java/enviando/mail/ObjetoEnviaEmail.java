package enviando.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {
	private String userName ="impactprices@gmail.com";
	private String senha = "RCM@reinas123";
	private String listaDestinatarios="";
	private String nomeRemetente="";
	private String assuntoEmail="";
	private String textoEmail="";
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String  assuntoEmail, String textoEmail) {
		this.listaDestinatarios=listaDestinatarios;
		this.nomeRemetente=nomeRemetente;
		this.assuntoEmail=assuntoEmail;
		this.textoEmail=textoEmail;
	}
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
		
		/* Olhe as config smtp do seu email */	
				Properties properties = new Properties();
				properties.put("mail.smtp.ssl.trust", "*");
				properties.put("mail.smtp.auth", true);
				properties.put("mail.smtp.starttls", true);
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port","465");
				properties.put("mail.smtp.socketFactory.port","465");
				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						
						return new PasswordAuthentication(userName, senha);
					}
				});
				Address [] toUser = InternetAddress.parse(listaDestinatarios);
				Message message = new MimeMessage(session);
				message.setFrom(new  InternetAddress(userName, nomeRemetente));
				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject(assuntoEmail);
				
				/*Parte 1 do e-mail que é texto e a descrição do email*/
				MimeBodyPart corpoEmail = new MimeBodyPart();
				
				if(envioHtml) {
					corpoEmail.setContent(textoEmail,"text/html; charset=utf-8");	
				}else {
					corpoEmail.setText(textoEmail);
				}
				
				List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(corpoEmail);
				arquivos.add(simuladorDePDF());
				arquivos.add(simuladorDePDF());
				arquivos.add(simuladorDePDF());
				arquivos.add(simuladorDePDF());
				int index=0;
				for(FileInputStream fileInputStream:arquivos) {							
				/*Segunda parte anexo em pdf*/
				MimeBodyPart anexoEmail = new MimeBodyPart();				
				/*Onde é passado simulador de PDF onde voce pode passar o arquivo gravado no banco de dados*/
				anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf") ));
				anexoEmail.setFileName("anexoemail"+index+".pdf");							
				multipart.addBodyPart(anexoEmail);
				index++;
				}
				message.setContent(multipart);
				Transport.send(message);

}
	
	public void enviarEmail(boolean envioHtml) throws Exception {
		
			/* Olhe as config smtp do seu email */	
					Properties properties = new Properties();
					properties.put("mail.smtp.ssl.trust", "*");
					properties.put("mail.smtp.auth", true);
					properties.put("mail.smtp.starttls", true);
					properties.put("mail.smtp.host", "smtp.gmail.com");
					properties.put("mail.smtp.port","465");
					properties.put("mail.smtp.socketFactory.port","465");
					properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					Session session = Session.getInstance(properties, new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							
							return new PasswordAuthentication(userName, senha);
						}
					});
					Address [] toUser = InternetAddress.parse(listaDestinatarios);
					Message message = new MimeMessage(session);
					message.setFrom(new  InternetAddress(userName, nomeRemetente));
					message.setRecipients(Message.RecipientType.TO, toUser);
					message.setSubject(assuntoEmail);
					if(envioHtml) {
					message.setContent(textoEmail,"text/html; charset=utf-8");	
					}else {
					message.setText(textoEmail);
					}
					Transport.send(message);
	
	}
	/**
	 * Esse metodo simula o PDF ou qualquer arquivo que possa ser anexado e enviado por email
	 * Voce pode pegar o arquivo no seu banco de dados  base64, byte[], stream de arquivos. ou numa pasta
	 * @return Um PDF em Branco com o texto do paragrafo de exemplo
	 * @throws Exception
	 */
	private FileInputStream simuladorDePDF() throws Exception{
		Document document = new Document();
		File file = new File("anexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF aenxo com java Mail, esse texto é do pdf"));
		document.close();
		return new FileInputStream(file);
	}
}