package mywebscrapper.businesslogic;

import java.util.HashMap;
import java.util.Map;

import mywebscrapper.entity.CrawlerThread;

public class MyWebScrapper {
	Map<String, Boolean> cacheMap = new HashMap<String, Boolean>();

	public void run(String... args) {
		final String url = args[0];
		final int maxNumOfUrls = Integer.parseInt(args[1]);
		final int depth = Integer.parseInt(args[2]);
		final boolean isUnique = Boolean.parseBoolean(args[3]);
		run(url, maxNumOfUrls, depth, isUnique);
	}

	public void run(String url, int maxNumOfUrls, int depth, boolean isUnique) {
		Thread thread = new Thread(new CrawlerThread(url, maxNumOfUrls, 0, depth, isUnique, cacheMap));
		thread.start();
	}
}
