package mywebscrapper.businesslogic;

import java.util.HashMap;
import java.util.Map;

import mywebscrapper.entity.CrawlerThread;
import mywebscrapper.utils.ScrapperUtils;

public class MyWebScrapper {
	Map<String, Boolean> cacheMap = new HashMap<String, Boolean>();
	private String url = "";
	private int maxNumOfUrls = 0;
	private int depth = 0;
	private boolean isUnique = false;

	public void run(String... args) {
		validateAndSetInput(args);
		run(url, maxNumOfUrls, depth, isUnique);
	}

	public void run(String url, int maxNumOfUrls, int depth, boolean isUnique) {
		Thread thread = new Thread(new CrawlerThread(url, maxNumOfUrls, 0, depth, isUnique, cacheMap));
		thread.start();
	}
	
	private void validateAndSetInput(String... args) throws IllegalArgumentException {
		ScrapperUtils scrapperUtils = new ScrapperUtils();
		final String url = args[0];
		final String maxNumOfUrlsStr = args[1];
		final String depthStr = args[2];
		final String isUniqueStr = args[3];
		if (scrapperUtils.isUrlValid(url)) {
			throw new IllegalArgumentException(String.format("%s is not a valid url", url));
		} else {
			this.url = url;
		}
		
		try {
			this.maxNumOfUrls = Integer.parseInt(maxNumOfUrlsStr);
			this.depth = Integer.parseInt(depthStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		if (this.maxNumOfUrls <= 0) {
			throw new IllegalArgumentException("maxNumOfUrls must be greater than 0");
		}
		
		if (this.depth < 0) {
			throw new IllegalArgumentException("depth must be greater or equal to 0");
		}
		
		if (Boolean.TRUE.toString().equalsIgnoreCase(isUniqueStr) || Boolean.FALSE.toString().equalsIgnoreCase(isUniqueStr)) {
			this.isUnique = Boolean.parseBoolean(isUniqueStr);
		} else {
			throw new IllegalArgumentException(String.format("%s is not valid for \"isUnique\" flag", isUniqueStr));
		}
	}
	
}
