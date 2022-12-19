package arquivos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EscreveJSON {
public static void main(String[] args) throws IOException {
Usuario usuario1 = new Usuario();
usuario1.setNome("Reinaldo");
usuario1.setCpf("53546646");
usuario1.setLogin("reinaldo");
usuario1.setSenha("admin");
Usuario usuario2 = new Usuario();
usuario2.setNome("Admin");
usuario2.setCpf("53546646");
usuario2.setLogin("admin");
usuario2.setSenha("admin");

List<Usuario> usuarios = new ArrayList<Usuario>();
usuarios.add(usuario1);
usuarios.add(usuario2);
Gson gson = new GsonBuilder().setPrettyPrinting().create();
String jsonUser =gson.toJson(usuarios);
System.out.println(jsonUser);
FileWriter fileWriter = new FileWriter("C:\\Users\\rcm_r\\eclipse-workspace\\ArquivosEmJava\\src\\arquivos\\filejosn.json");
fileWriter.write(jsonUser);
fileWriter.flush();
fileWriter.close();
/*LENDO ARQUIVO JSON*/

FileReader fileReader=  new FileReader("C:\\Users\\rcm_r\\eclipse-workspace\\ArquivosEmJava\\src\\arquivos\\filejosn.json");
JsonArray jsonArray  = (JsonArray) JsonParser.parseReader(fileReader);
List<Usuario> listausuarios = new ArrayList<Usuario>();
for(JsonElement jsonElement: jsonArray) {
	Usuario usuario = new Gson().fromJson(jsonElement, Usuario.class);
	listausuarios.add(usuario);
}
System.out.println("Leitura Arquivo JSON: "+listausuarios);
}
}
