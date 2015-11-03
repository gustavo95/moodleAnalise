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
public class ColetarFluxo {
	public ArrayList<String> getVulnerabilidadesLinks() throws IOException{
		ArrayList<String> vulnerabilitiesLinks = new ArrayList<String>();
		
		for(int i = 1; i <= 6; i++){
			Connection connection = Jsoup.connect("http://www.cvedetails.com/vulnerability-list.php?vendor_id=2105&product_id=3590&version_id=&page=" + i + "&hasexp=0&opdos=0&opec=0&opov=0&opcsrf=0&opgpriv=0&opsqli=0&opxss=0&opdirt=0&opmemc=0&ophttprs=0&opbyp=0&opfileinc=0&opginf=0&cvssscoremin=0&cvssscoremax=0&year=0&month=0&cweid=0&order=1&trc=281&sha=9ddd526d6c3de20c88ac1e41f0bc477f1393cc17").timeout(300000);
			Document doc = connection.get();
			
			Pattern pattern = Pattern.compile("(http.*cvedetails.*CVE-[0-9]*-[0-9]*)");

			Elements links = doc.select("a[href]");
			
	        for(Element l: links){
	            String link = l.attr("abs:href");
	            
	            Matcher matcher = pattern.matcher(link);
	            if(matcher.find()) {
	            	String result = matcher.group();
	            	vulnerabilitiesLinks.add(result);
	            	//System.out.println("Vulnerabilidade: " + result);

	            }
	        }
		}
        
        return vulnerabilitiesLinks;
	}
	
	public ArrayList<String> getBugsLinks(String url) throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect(url).timeout(300000);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(http.*git\\.moodle.*commit.*)");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            if(matcher.find()) {
            	String result = matcher.group();
            	bugsLinks.add(result);
            	//System.out.println("Bugs: " + result);

            }
        }
        
        return bugsLinks;
	}
	
	public ArrayList<String> getPatchLink(String url) throws IOException{
		ArrayList<String> bugsLinks = new ArrayList<String>();
		
		Connection connection = Jsoup.connect(url).timeout(300000);
		Document doc = connection.get();
		
		Pattern pattern = Pattern.compile("(http.*git\\.moodle.*commitdiff.*)");
		Pattern pattern2 = Pattern.compile("(http.*moodle.*commitdiff.*h=HEAD)|(http.*moodle.*commitdiff.*hp=.*)");

		Elements links = doc.select("a[href]");
		
        for(Element l: links){
            String link = l.attr("abs:href");
            
            Matcher matcher = pattern.matcher(link);
            Matcher matcher2 = pattern2.matcher(link);
            if(matcher.find()) {
            	if(matcher2.find()){
            		
            	}else{
            		String result = matcher.group();
                	
                	if(checarLista(bugsLinks, result)){
                		bugsLinks.add(result);
                    	//System.out.println("Patch links: " + result);
                	}
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
	
	public String getPatchDiff(String url) throws IOException{
		String patchDiff = "nada";
		
		Connection connection = Jsoup.connect(url);
		Document doc = connection.get();

		Elements result = doc.getAllElements();
		     	
		patchDiff = result.get(0).getAllElements().get(0).text();
		
		//System.out.println("Diff: " + patchDiff);
		
        //patchDiff = result;
        //System.out.println(result);
        
        return patchDiff;
	}
	
	public String getFile(String url) throws IOException{
		String file = "";
		
		Document doc = Jsoup.connect(url).get();

        Elements cves = doc.getElementsByClass("patch");
        
        for (int i = 0; i < cves.size(); i++) {     	
        	file = (cves.get(i).getAllElements().get(0).text().replace("&nbsp;", " "));
        	//System.out.println("File: " + file);
        }
		
		return file;
	}
	
	public ArrayList<String> getClass(String url) throws IOException{
		ArrayList<String> functions = new ArrayList<String>();
		String function = "";
		
		Document doc = Jsoup.connect(url).get();

        Elements ele = doc.getElementsByClass("section");
        
        for (int i = 0; i < ele.size(); i++) {
        	function = ele.get(i).getAllElements().get(0).text().replace("&nbsp;", " ");
        	if(!function.equals("")){
        		functions.add(ele.get(i).getAllElements().get(0).text().replace("&nbsp;", " "));
        	}
        }
				
		return functions;
	}
}
