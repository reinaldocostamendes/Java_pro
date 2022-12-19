package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ApachePoi2 {

	public static void main(String[] args) throws IOException {
FileInputStream entrada = new FileInputStream(new File("C:\\Users\\rcm_r\\eclipse-workspace\\ArquivosEmJava\\src\\arquivos\\arquivo.excel.xls"));
HSSFWorkbook hssfWorkbook = new HSSFWorkbook(entrada);
HSSFSheet planilha = hssfWorkbook.getSheetAt(0);

Iterator<Row> linhaIterator = planilha.iterator();
List<Pessoa> pessoas = new ArrayList<Pessoa>();

while(linhaIterator.hasNext()) {
	Row linha = linhaIterator.next();
	Iterator<Cell> celulas =linha.iterator();
	Pessoa pessoa = new Pessoa();
			while(celulas.hasNext()) {
				Cell cell = celulas.next();
			switch(cell.getColumnIndex()) {
			case 0:
				pessoa.setNome(cell.getStringCellValue());
				break;
			case 1:
				pessoa.setEmail(cell.getStringCellValue());
				break;
			case 2:
				pessoa.setIdade(Double.valueOf(cell.getNumericCellValue()).intValue());
				break;
			}
			
			}
			pessoas.add(pessoa);
}
entrada.close();
for(Pessoa p:pessoas) {
	System.out.println(p);
}
	}

}
