package arquivos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ApachePoi {

	public static void main(String[] args) throws IOException {
		File file =new File("C:\\Users\\rcm_r\\eclipse-workspace\\ArquivosEmJava\\src\\arquivos\\arquivo.excel.xls");
if(!file.exists()) {
	file.createNewFile();
}
Pessoa pessoa1 = new Pessoa();
Pessoa pessoa2 = new Pessoa();
Pessoa pessoa3 = new Pessoa();
Pessoa pessoa4 = new Pessoa();

pessoa1.setEmail("pessoa@gmail.com");
pessoa1.setNome("Pessoa");
pessoa1.setIdade(34);

pessoa2.setEmail("pessoa2@gmail.com");
pessoa2.setNome("Pessoa2");
pessoa2.setIdade(36);

pessoa3.setEmail("pessoa3@gmail.com");
pessoa3.setNome("Pessoa3");
pessoa3.setIdade(37);

pessoa4.setEmail("pessoa4@gmail.com");
pessoa4.setNome("Reinaldo Mendes");
pessoa4.setIdade(37);

List<Pessoa> pessoas = new ArrayList<Pessoa>();
pessoas.add(pessoa1);
pessoas.add(pessoa2);
pessoas.add(pessoa3);
pessoas.add(pessoa4);

@SuppressWarnings("resource")
HSSFWorkbook hssfWorkbook = new HSSFWorkbook();/*ussado para escrever planiolha*/
HSSFSheet linhaPessoa =hssfWorkbook.createSheet("planilha de pessoas jdev treinamento");/*cria panilha*/
int numeroLinha =0;
for(Pessoa p:pessoas) {
Row linha= linhaPessoa.createRow(numeroLinha++);
int celula=0;
Cell celNome = linha.createCell(celula++);
celNome.setCellValue(p.getNome());

Cell celEmal = linha.createCell(celula++);
celEmal.setCellValue(p.getEmail());

Cell celIdade = linha.createCell(celula++);
celIdade.setCellValue(p.getIdade());

}
FileOutputStream saida = new FileOutputStream(file);
hssfWorkbook.write(saida);
saida.flush();
saida.close();
System.out.println("Planilha foi criada");
	}

}
