package analise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//moodle
public class Main {

	public static void main(String[] args) throws Exception {
		fluxo();
	}
	
	public static void fluxo() throws IOException{
		ColetarFluxo cf = new ColetarFluxo();
		LogsDiffs ld = new LogsDiffs();
		
		ArrayList<String> vulnerabilidades = cf.getVulnerabilidadesLinks();
		ArrayList<String> bugs;
		ArrayList<String> patch;
		ArrayList<String> functions;
		int count = 0;
		String textData = "";
		//String diffIndex = "";
		String file = "";
		String commit = "";
		
		//Pattern pattern1 = Pattern.compile("(diff --git\\s*\\w\\/)(.*?)(?<=.*)index(.+?\\s)");
		Pattern pattern2 = Pattern.compile("(php b/.*php)");
		
		for(String vLink : vulnerabilidades){
			System.out.println("\nVulnerabilidade: " + vLink);
			bugs = cf.getBugsLinks(vLink);
			for(String bLink : bugs){
				System.out.println("Bug: " + bLink);
				patch = cf.getPatchLink(bLink);
				for(String pLink : patch){
					System.out.println("Patch: " + pLink);
					textData = cf.getFile(pLink);
					//Matcher matcher1 = pattern1.matcher(textData);
					Matcher matcher2 = pattern2.matcher(textData);
					
					commit = pLink.split("h=")[1];
					System.out.println("Commit: " + commit);
					
					while (matcher2.find()) {
						file = matcher2.group().split(" ")[1];
						count = file.length() - file.replace("/", "").length();
						file = file.split("/")[count];
						System.out.println("File: " + file);
					}
					
					functions = cf.getFunction(pLink);
					for(String f : functions){
						System.out.println("Estrutura: " + f);
					}
				}
			}
		}
	}
	

}
