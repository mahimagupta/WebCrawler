package crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String crawlStorageFolder="/Users/mahima/Desktop/CrawlerResults3/data/crawl";
		int numberOfCrawlers=7;
	

	CrawlConfig config = new CrawlConfig();
	config.setIncludeBinaryContentInCrawling(true);
	config.setCrawlStorageFolder(crawlStorageFolder);
	config.setMaxPagesToFetch(20000);
	config.setMaxDepthOfCrawling(16);
    config.setPolitenessDelay(300);
    config.setUserAgentString("Hello");
	
	
	 PageFetcher pageFetcher = new PageFetcher(config);
     RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
     RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
     CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

     controller.addSeed("http://www.latimes.com/");
     controller.start(MyCrawler.class, numberOfCrawlers);
	}
}