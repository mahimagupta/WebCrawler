package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

	//private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
	//		 + "|png|mp3|mp3|zip|gz))$");
	//private static final Pattern FILTERS = Pattern.compile(
   	//        ".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|svg" +
   	//        "|rm|smil|wmv|swf|wma|zip|rar|gz|xml|rss|json|ico))$");
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mp3|mp3|zip|gz|csv))$");

			static int visited=0,succeeded=0,aborted=0,unique=0,nonunique=0,totalsize=0;
			static int groupA=0, groupB=0, groupC=0, groupD=0, groupE=0; //filesize
			static int texthtml=0,imagegif=0,imagejpeg=0,imagepng=0,pdf=0;
			static HashSet<String> uniqueHS=new HashSet<String>();
			static int OK=0, N_OK=0,uniqueoutside=0,uniqueinside=0;
			static HashMap<Integer, Integer> statusHS=new HashMap<Integer,Integer>();
	       
	      
			
			 /**
			 * This method receives two parameters. The first parameter is the page
			 * in which we have discovered this new url and the second parameter is
			 * the new url. 
			 */
			 @Override
			 public boolean shouldVisit(Page referringPage, WebURL url) {
				 String href = url.getURL().toLowerCase();
				
				 //System.out.println("HREF IS!! "+ href);
				 String okay="";
				 if(uniqueHS.contains(href)){
					  nonunique++;
				  }
				  else{
					  uniqueHS.add(href);
					  unique++;
					  if(href.startsWith("http://www.latimes.com/") || href.startsWith("https://www.latimes.com/")){
							 uniqueinside++;
						 }
						 else{
							 uniqueoutside++;
						 }
				  }
				 if(href.startsWith("http://www.latimes.com/") || href.startsWith("https://www.latimes.com/")){
					 okay="OK";
				 }
				 else{
					 okay="N_OK";
				 }
				 FileWriter pw;
					try {
						File f=new File("/Users/mahima/Desktop/CrawlerResults3/urls_latimes.csv");
						if(f.exists()){
							pw = new FileWriter(f.getAbsoluteFile(),true);
							StringBuilder sb = new StringBuilder();
							sb.append(href);
					        sb.append(',');
					        sb.append(okay);
					        sb.append('\n');
					        pw.write(sb.toString());
					        pw.flush();
					        pw.close();
						}
						else{
							pw = new FileWriter(f);
							StringBuilder sb = new StringBuilder();
							 sb.append("URL");
						     sb.append(',');
						     sb.append("OK/N_OK?");
						     sb.append('\n');
						     	sb.append(href);
						        sb.append(',');
						        sb.append(okay);
						        sb.append('\n');
						     pw.write(sb.toString());
						     pw.flush();
						     pw.close();
						}
						
						 
						
					 return !FILTERS.matcher(href).matches()
					 && (href.startsWith("http://www.latimes.com/") || href.startsWith("https://www.latimes.com/"))  && !href.contains(".css") && !href.contains(".svg") && !href.contains(".ico") && !href.contains(".xml");
					    
				      
					 
					// !(!FILTERS.matcher(href).matches() && href.startsWith("https://www.latimes.com/") && !href.contains(".css") && !href.contains(".svg") && !href.contains(".ico"));
						 
					 

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
					
					
				
				 }
			 /**
			  * This function is called when a page is fetched and ready
			  * to be processed by your program.
			  */
			  @Override
			  public void visit(Page page) {
			  String url = page.getWebURL().getURL();
			  System.out.println("URL: " + url);
			  
			  
			  //Adds to the total number of pages
			  //countVisited();
			  
			  //Get the content type
			  String ctype=page.getContentType();
			  if(ctype.contains("text/html"))
				  ctype="text/html";
			  if(ctype.contains("image/png"))
				  ctype="image/png";
			  System.out.println("Content Type: " + ctype);
			  classifyContentType(ctype);
			  
			  //Get response code
			  int statuscode=page.getStatusCode();
			  System.out.println("Status Code: " + statuscode);
			  
			  FileWriter pw;
			
			  
			  int filesize=page.getContentData().length;
			  System.out.println("File size is: "+ filesize);
			  classifyFileSize(filesize);
			  
			  int linksize=0;
			  if (page.getParseData() instanceof HtmlParseData) {
			  HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			  String text = htmlParseData.getText();
			  String html = htmlParseData.getHtml();
			  Set<WebURL> links = htmlParseData.getOutgoingUrls();
			  
			  //System.out.println("Hashcode is "+htmlParseData.hashCode());
			  
			  
			  
			  
			 
			  
			  System.out.println("Text length: " + text.length());
			  System.out.println("Html length: " + html.length());
			  System.out.println("Number of outgoing links: " + links.size());
			  linksize=links.size();
			  totalsize+=linksize;
				 

			  }
			  
			  try {
					File f=new File("/Users/mahima/Desktop/CrawlerResults3/visit_latimes.csv");
					if(f.exists()){
						pw = new FileWriter(f.getAbsoluteFile(),true);
						StringBuilder sb = new StringBuilder();
						sb.append(url.replace(',', '_'));
				        sb.append(',');
				        sb.append(filesize);
				        sb.append(',');
				        sb.append(linksize);
				        sb.append(',');
				        sb.append(ctype);
				        sb.append('\n');
				        pw.write(sb.toString());
				        pw.flush();
				        pw.close();
					}
					else{
						pw = new FileWriter(f);
						StringBuilder sb = new StringBuilder();
						 sb.append("URL");
					     sb.append(',');
					     sb.append("Filesize");
					     sb.append(',');
					     sb.append("Number of Outgoing links");
					     sb.append(',');
					     sb.append("Content type");
					     sb.append('\n');
					     	sb.append(url.replace(',', '_'));
					        sb.append(',');
					        sb.append(filesize);
					        sb.append(',');
					        sb.append(linksize);
					        sb.append(',');
					        sb.append(ctype);
					        sb.append('\n');
					     pw.write(sb.toString());
					     pw.flush();
					     pw.close();
					}
					
					
			      

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
			  System.out.println("*****STATS*****");
			  System.out.println("Total links is :"+ totalsize);
			  System.out.println("Visited is: " + visited);
			  System.out.println("Suceeded is: " + succeeded);
			  System.out.println("Aborted is: " + aborted);
			  System.out.println("Unique URLS inside "+uniqueinside);
			  System.out.println("Unique URLS outside "+uniqueoutside);
			  System.out.println("Unique URLS  "+unique);
			  System.out.println("<1KB: "+groupA);
			  System.out.println("1KB ~<10KB: "+groupB);
			  System.out.println("10KB ~<100KB: "+groupC);
			  System.out.println("100KB ~<1MB: "+groupD);
			  System.out.println(">=1MB: "+groupE);
			  System.out.println("text/html: "+texthtml);
			  System.out.println("image/jpeg: "+imagejpeg);
			  System.out.println("image/gif: "+imagegif);
			  System.out.println("image/png: "+imagepng);
			  System.out.println("application/pdf:" + pdf );
			  for (Entry<Integer, Integer> entry : statusHS.entrySet()) {
				  System.out.println(entry.getKey() + " = " + entry.getValue());
				}
			  
			  }
			  
			  
			  
			
			private static void classifyContentType(String ctype) {
				// TODO Auto-generated method stub
				if(ctype.contains("text/html")){
					texthtml++;
				}
				else if(ctype.contains("image/gif")){
					imagegif++;
				}
				else if(ctype.contains("image/jpeg")){
					imagejpeg++;
				}
				else if(ctype.contains("image/png")){
					imagepng++;
				}
				else if(ctype.contains("application/pdf")){
					pdf++;
				}
				
			}
			private static void  classifyFileSize(int filesize) {
				// TODO Auto-generated method stub
				if(filesize < 1024){
					groupA++;
				}
				else if(filesize>=1024 && filesize<10*1024){
					groupB++;
				}
				else if(filesize>=1024*10 && filesize<100*1024){
					groupC++;
				}
				else if(filesize>=100*1024 && filesize<1000*1024){
					groupD++;
				}
				else if(filesize>=1000*1024 ){
					groupE++;
				}
				
				
				
			}
			
			@Override
			public void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
				// Do nothing by default
				// Sub-classed can override this to add their custom functionality
				statusCodeClassification(statusCode);
				FileWriter pw;
				try {
					File f=new File("/Users/mahima/Desktop/CrawlerResults3/fetch_latimes.csv");
					if(f.exists()){
						pw = new FileWriter(f.getAbsoluteFile(),true);
						StringBuilder sb = new StringBuilder();
						sb.append(webUrl.getURL().replace(',', '_'));
				        sb.append(',');
				        sb.append(statusCode);
				        sb.append('\n');
				        pw.write(sb.toString());
				        pw.flush();
				        pw.close();
					}
					else{
						pw = new FileWriter(f);
						StringBuilder sb = new StringBuilder();
						 sb.append("URL");
					     sb.append(',');
					     sb.append("Status Code");
					     sb.append('\n');
					     	sb.append(webUrl.getURL().replace(',', '_'));
					        sb.append(',');
					        sb.append(statusCode);
					        sb.append('\n');
					     pw.write(sb.toString());
					     pw.flush();
					     pw.close();
					}
					
					
			       

			        
			        
			      

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.handlePageStatusCode(webUrl, statusCode, statusDescription);
			}
			
			private static void statusCodeClassification(int code) {
				// TODO Auto-generated method stub
				visited++;
				if(statusHS.containsKey(code)){
					statusHS.put(code, statusHS.get(code)+1);
				}
				else{
					statusHS.put(code, 1);
				}
				if(code>=200 && code<300){
					succeeded++;
				}
				if(code>=300 && code<600){
					aborted++;
				}
				
			}
			  
			  //public void countFile
}