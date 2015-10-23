package analise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//moodle
public class LogsDiffs {
	public ArrayList<String> getLogs() throws IOException {
		ArrayList<String> logs = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("(commit .*\nAuthor)");
		String log = execCommand("git log");
		//System.out.println(log);

		Matcher matcher = pattern.matcher(log);
		while (matcher.find()) {
			String result = matcher.group().substring(7,48);
			//System.out.println(result + "\n-------------------------");
			logs.add(result);
		}
		//System.out.println(logs.size());
		//System.out.println(logs.get(0));
		
		return logs;
	}
	
	public ArrayList<String> getDiffs() throws IOException{
		ArrayList<String> logs = getLogs();
		ArrayList<String> diffs = new ArrayList<String>();
		
		for(int i = 0; i < logs.size()-1; i++){
			String diff = execCommand("git diff " + logs.get(i) + " " + logs.get(i+1));
			System.out.println(i);
			diffs.add(diff);
		}
		
		return diffs;
	}

	private synchronized static String execCommand(final String commandLine) throws IOException {  

		boolean success = false;  
		String result;  

		Process p;  
		BufferedReader input;  
		StringBuffer cmdOut = new StringBuffer();  
		String lineOut = null;  
		int numberOfOutline = 0;  

		try {  

			p = Runtime.getRuntime().exec(commandLine);  

			input = new BufferedReader(new InputStreamReader(p.getInputStream()));  

			while ((lineOut = input.readLine()) != null) {  
				if (numberOfOutline > 0) {  
					cmdOut.append("\n");  
				}  
				cmdOut.append(lineOut);  
				numberOfOutline++;  
			}  

			result = cmdOut.toString();  

			success = true;  

			input.close();  

		} catch (IOException e) { 
			result = String.format("Falha ao executar comando %s. Erro: %s", commandLine, e.toString());  
		} 

		// Se não executou com sucesso, lança a falha  
		if (!success) {  
			throw new IOException(result);  
		}  

		return result;  

	} 
}
