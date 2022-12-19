package arquivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LerArquivoTxt {
public static void main(String[] args) throws FileNotFoundException {
FileInputStream entradaArquivo = new FileInputStream(new File("C:\\Users\\rcm_r\\eclipse-workspace\\arquivos\\src\\arquivos\\arquivo.csv"));
Scanner lerArquivo = new Scanner(entradaArquivo,"UTF-8");
List<Pessoa> pessoas = new ArrayList<Pessoa>();
while(lerArquivo.hasNext()) {
String linha = lerArquivo.nextLine();
if(linha !=null && !linha.isEmpty()) {
	String[] dados =linha.split("\\;");
	Pessoa p = new Pessoa();
	p.setNome(dados[0]);
	p.setEmail(dados[1]);
	p.setIdade(Integer.parseInt(dados[2]));
	pessoas.add(p);
}
}
for(Pessoa pessoa :pessoas) {
	System.out.println(pessoa);
}

}
}
