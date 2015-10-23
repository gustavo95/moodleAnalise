package analise;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//moodle
public class BugsPatches {
	
	public ArrayList<String> getBugs() throws IOException{
		ArrayList<String> bugs = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("https://tracker.moodle.org/secure/RapidBoard.jspa?projectKey=MDL&rapidView=30");
		Document doc = connection.get();

        //Elements bugs = doc.getElementsByTag("a");
        Elements ele = doc.getElementsByClass("ghx-key");
        
        for (int i = 0; i < ele.size(); i++) {     	
        	bugs.add(ele.get(i).getAllElements().get(0).text());
        }
        
        return bugs;
	}
}
