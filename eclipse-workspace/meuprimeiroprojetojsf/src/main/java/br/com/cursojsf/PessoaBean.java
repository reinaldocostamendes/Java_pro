package br.com.cursojsf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;
import net.bootsfaces.component.selectOneMenu.SelectOneMenu;

@ViewScoped
@Named(value = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	@Inject
	private IDaoPessoa iDaoPessoa;
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private Part arquivofoto;
	@Inject
	private JPAUtil jpaUtil;

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public String salvar() throws IOException {
		System.out.println("-----------------------------------SALVANDO-------------------------------");
		byte[] imagemByte = getByte(arquivofoto.getInputStream());
		pessoa.setFotoIconBase64Original(imagemByte);// imagem original
		// transformar em bufferimab«ge
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
		if(bufferedImage!=null) {
		// pega o tipo de imagem
		int type = (bufferedImage == null || bufferedImage.getType() == 0) ? BufferedImage.TYPE_INT_ARGB
				: bufferedImage.getType();

		int largura = 200;
		int altura = 200;
		// criar a miniatrua
		BufferedImage resizedImage = new BufferedImage(largura, altura, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(bufferedImage, 0, 0, largura, altura, null);
		g.dispose();

		// Escrever imagem em tamanho menor
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String extensao = arquivofoto.getContentType().split("\\/")[1];
		ImageIO.write(resizedImage, extensao, baos);
		String miniImagem = "data:" + arquivofoto.getContentType() + ";base64,"
				+ DatatypeConverter.printBase64Binary(baos.toByteArray());
		pessoa.setFotoIconBase64(miniImagem);
		// System.out.println("Extnsao: " + extensao);
		pessoa.setExtensao(extensao);
	}
		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();
		mostarMsg("Gravado com sucesso!");
		System.out.println("-----------------------------------SALVANDO-------------------------------");
		return "";
	}

	private void mostarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);

	}

	public String novo() {
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}

	public String limpar() {
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}

	public String remove() {
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostarMsg("Eliminado com sucesso");
		return "";
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getEstados() {
		estados = iDaoPessoa.listaEstados();
		return estados;
	}

	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		System.out.println("Carregar cidades");
		Estados estado = (Estados) ((SelectOneMenu) event.getSource()).getValue();

		if (estado != null && estado instanceof Estados) {
			pessoa.setEstados(estado);
			System.out.println(estado.getNome());
			List<Cidades> cidades = jpaUtil.getEntyManager()
					.createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
			}
			setCidades(selectItemsCidade);
		} else {
			System.out.println("Estado está null");
		}

	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		// System.out.println("Metodo pesquisa cep chamado CEP: " + pessoa.getCep());
		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			System.out.println(url);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			int statusCode = http.getResponseCode();
			System.out.println(statusCode);
			if (statusCode == 200) {

				URLConnection connection = url.openConnection();
				InputStream is = connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

				String cep = "";
				StringBuilder jsonCep = new StringBuilder();
				while ((cep = br.readLine()) != null) {
					jsonCep.append(cep);
				}
				Pessoa gsonP = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
				pessoa.setLogradouro(gsonP.getLogradouro());
				pessoa.setComplemento(gsonP.getComplemento());
				pessoa.setBairro(gsonP.getBairro());
				pessoa.setLocalidade(gsonP.getLocalidade());
				pessoa.setUf(gsonP.getUf());
				pessoa.setUnidade(gsonP.getUnidade());
				pessoa.setIbge(gsonP.getIbge());
			}
		} catch (Exception e) {
			e.printStackTrace();
			mostarMsg("Erro ao consultar o CEP");
		}
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	@PostConstruct
	public void carregarPessoas() {
	//	pessoas = daoGeneric.getListEntityLimite10(Pessoa.class);
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	@SuppressWarnings("unchecked")
	public String editar() {
		if (pessoa.getCidades() != null) {
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			pessoa.setExtensao(pessoa.getExtensao());
			pessoa.setFotoIconBase64(pessoa.getFotoIconBase64());
			pessoa.setFotoIconBase64Original(pessoa.getFotoIconBase64Original());
			if (estado != null) {
				pessoa.setEstados(estado);
				List<Cidades> cidades = jpaUtil.getEntyManager()
						.createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();

				List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
				for (Cidades cidade : cidades) {
					selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				}
				setCidades(selectItemsCidade);
			}
		}
		return "";
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public String logar() {
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		if (pessoaUser != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			return "primeirapagina.jsf";
		}else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage("Login ou senha Invalido!");
			context.addMessage("msg", message);
		}
		return "index.jsf";
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");
		HttpServletRequest httpServletRequest = (HttpServletRequest) context.getCurrentInstance().getExternalContext()
				.getRequest();
		httpServletRequest.getSession().invalidate();
		return "index.jsf";
	}

	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		return pessoaUser.getPerfilUser().equals(acesso);
	}

	public void setArquivofoto(Part arquivofoto) {
		this.arquivofoto = arquivofoto;
	}

	public Part getArquivofoto() {
		return arquivofoto;
	}

	// metodo que converte inputstream em array de bytes
	private byte[] getByte(InputStream is) throws IOException {
		int len;
		int size = 1024;
		byte[] buf = null;
		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}
			buf = bos.toByteArray();
		}
		return buf;
	}

	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = params.get("fileDownloadId");
		Pessoa pessoa = daoGeneric.consultar(Pessoa.class, fileDownloadId);
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=download." + pessoa.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(pessoa.getFotoIconBase64Original().length);
		response.getOutputStream().write(pessoa.getFotoIconBase64Original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void registaLog() {
		System.out.println("Regista log");
	}

	public void mudancaDeValor(ValueChangeEvent vce) {
		System.out.println("Valor antigo: " + vce.getOldValue());
		System.out.println("Valor novo: " + vce.getNewValue());
	}

	public void mudancaDeValorSobreNome(ValueChangeEvent vce) {
		System.out.println("Valor antigo: " + vce.getOldValue());
		System.out.println("Valor novo: " + vce.getNewValue());
	}
}
