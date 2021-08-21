package test.telkom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


@Command(name = "hello",showAtFileInUsageHelp=true,description="Hello World")
public class ReadTextFile implements Runnable{
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args){
		CommandLine.run(new ReadTextFile(), args);
	}
	
	@Parameters(paramLabel="FILE")
	public File file;

	@Option(names="-o",description="Untuk membuka file")
	public File outputFile;
	
	@Option(names="-t",description="Untuk konversi file")
	public String convert;
	
	@Option(names="-h",usageHelp=true,description="Help")
	public boolean help;

	private BufferedReader br;
	String isiFile;
	String textFile;
	JSONObject obj= null;
	
	@Override
	public void run(){

		//cek path file
		if(file != null){
			System.out.println("File exist.");
			//cek isi -t
			if(convert != null){
				//validasi flag convert
				if(convert.equals("json")){
					System.out.println("file akan dirubah ke format json");
					try {
						//baca isi file
						br = new BufferedReader(new FileReader(file));
						while((isiFile = br.readLine()) !=null){
							try {
								//proses convert
								obj = new JSONObject(isiFile);
							} catch (JSONException e) {
								e.printStackTrace();
							}							
											
						}
						isiFile = obj.toString().replace(",", ",\n  ")
								.replace("{", "{\n  ")
								.replace("}", "\n}")
								.replace("[", "[\n  ")
								.replace("]", "\n]")
								.replace(":", ":  ");
						obj = null;
						//cek isi -o
						if(outputFile==null){
							try {
								FileWriter writeFile = new FileWriter(file);
								writeFile.write(isiFile);
								writeFile.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							outputFile(outputFile,isiFile);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				//convert ke plaintext
				}else if(convert.equals("text") || convert.equals("") || convert.equals(" ")){
					System.out.println("file akan dirubah ke format plaintext");
					try {
						br = new BufferedReader(new FileReader(file));
						StringBuffer sb = new StringBuffer();
						while((textFile = br.readLine())!=null){
							sb.append(textFile+"\n");
							
						}
												
						isiFile = sb.toString();
						isiFile = isiFile.replaceAll("[\\t\" ]", "");
						isiFile = isiFile
								.replace(":", " ")
								.replace("=", " ")
								.replace("{", "")
								.replace("}", "")
								.replace("[", "[\n  ")
								.replace("]", "\n]")
								.replaceAll(",", "\n")
								.replaceAll(",", "").replaceAll(",+", "");

						//cek isi -o
						if(outputFile==null){
							try {
								FileWriter writeFile = new FileWriter(file);
								writeFile.write(isiFile);
								writeFile.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							outputFile(outputFile,isiFile);
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					System.out.println("konveris format lain belom tersedia");
				}				
				
			}
			}
		
		if(help){
			System.out.println("-t \t = ini untuk konversi file");
			System.out.println("-o \t = ini untuk menentukan path output file");
		}
	}	
	
	public void outputFile(File outputFile, String isiFile){
		//set output file	
		try {
			FileWriter writeFile = new FileWriter(outputFile);
			writeFile.write(isiFile);
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
		
}

