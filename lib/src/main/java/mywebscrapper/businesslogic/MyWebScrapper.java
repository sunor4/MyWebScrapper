package mywebscrapper.businesslogic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import mywebscrapper.utils.ScrapperUtils;

public class MyWebScrapper {
	private String url = "";
	private int maxNumOfUrls = 0;
	private int depth = 0;
	private boolean isUnique = false;

	public MyWebScrapper(String url, int maxNumOfUrls, int depth, boolean isUnique) {
		this.url = url;
		this.maxNumOfUrls = maxNumOfUrls;
		this.depth = depth;
		this.isUnique = isUnique;
	}

	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(maxNumOfUrls);
		Map<String, Boolean> cacheMap = Collections.synchronizedMap(new HashMap<String, Boolean>());
    }
}
