package analise;

import java.util.ArrayList;

//moodle
public class Main {

	public static void main(String[] args) throws Exception {
		Vulnerabilidades vul = new Vulnerabilidades();
		
		//ArrayList<String> temp = vul.getTemp();
		
		LogsDiffs logs = new LogsDiffs();
		//logs.getLogs();
		logs.getDiffs();
		
		//BugsPatches b = new BugsPatches();
		//b.getBugs();
	}

}
