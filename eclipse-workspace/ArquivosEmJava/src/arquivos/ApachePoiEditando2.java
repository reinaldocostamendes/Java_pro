package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ApachePoiEditando2 {

	public static void main(String[] args) throws Exception {
		
File file = new File("C:\\Users\\rcm_r\\eclipse-workspace\\ArquivosEmJava\\src\\arquivos\\arquivo.excel.xls");
FileInputStream entrada = new FileInputStream(file);
HSSFWorkbook hssfWorkbook = new HSSFWorkbook(entrada);
HSSFSheet planilha = hssfWorkbook.getSheetAt(0);
Iterator<Row> linhaIterator = planilha.iterator();
while(linhaIterator.hasNext()) {
Row linha = linhaIterator.next();

String valorCelula = linha.getCell(0).getStringCellValue();

linha.getCell(0).setCellValue(valorCelula+" * valor gravado na aula");
}
entrada.close();
FileOutputStream saida = new FileOutputStream(file);
hssfWorkbook.write(saida);
saida.flush();
saida.close();
System.out.println("Planilha actualizada");
	}

}
