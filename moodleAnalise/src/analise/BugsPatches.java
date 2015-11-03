package analise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//moodle
public class BugsPatches {
	
	public ArrayList<String> getBugsLinks() throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect("https://tracker.moodle.org/sr/jira.issueviews:searchrequest-fullcontent/temp/SearchRequest.html?jqlQuery=project+%3D+MDL+AND+issuetype+%3D+Bug+AND+affectedVersion+%3D+3.0&tempMax=1000").timeout(3000000);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("((https.*tracker.*browse/MDL-[0-9]*))");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            
            if(matcher.find()) {
            	String result = matcher.group();
            	
            	if(checarLista(bugsLinks, result)){
            		bugsLinks.add(result);
                	System.out.println(result);
            	}
            }
        }
        
        return bugsLinks;
	}
	
	public boolean checarLista(ArrayList<String> al, String s){
		boolean b = true;
		
		for(int i = 0; i < al.size(); i++){
			if(al.get(i).equals(s)){
				b = false;
			}
		}
		
		return b;
	}
	
}
