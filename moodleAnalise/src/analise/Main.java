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
		String textData = "";
		String diffIndex = "";
		String file = "";
		
		Pattern pattern9 = Pattern.compile("(diff --git\\s*\\w\\/)(.*?)(?<=.*)index(.+?\\s)");
		
		for(String vLink : vulnerabilidades){
			System.out.println("\nVulnerabilidade: " + vLink);
			bugs = cf.getBugsLinks(vLink);
			for(String bLink : bugs){
				System.out.println("Bug: " + bLink);
				patch = cf.getPatchLink(bLink);
				for(String pLink : patch){
					System.out.println("Patch: " + pLink);
					textData = cf.getFile(pLink);
					Matcher matcher9 = pattern9.matcher(textData);
					
					while (matcher9.find()) {
						file = matcher9.group(2).replace(" ", "").split("b/")[0];
						diffIndex = matcher9.group(3).replace(" ", "");
						System.out.println("File: " + file);
						System.out.println("Index: " + diffIndex);
					}
					
					functions = cf.getClass(pLink);
					for(String f : functions){
						System.out.println("Class: " + f);
						
					}
				}
			}
		}
	}

}
