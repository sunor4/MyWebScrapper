package mywebscrapper.businesslogic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import mywebscrapper.entity.CrawlerThread;

public class MyWebScrapper {
	private String url = "";
	private int maxNumOfUrls = 0;
	private int maxDepth = 0;
	private boolean isUnique = false;

	public MyWebScrapper(String url, int maxNumOfUrls, int maxDepth, boolean isUnique) {
		this.url = url;
		this.maxNumOfUrls = maxNumOfUrls;
		this.maxDepth = maxDepth;
		this.isUnique = isUnique;
	}

	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(maxNumOfUrls);
		Set<String> cacheSet = Collections.synchronizedSet(new HashSet<String>());
		cacheSet.add(this.url);
		Phaser phaser = new Phaser();
		CrawlerThread thread = new CrawlerThread(url, maxNumOfUrls, 1, maxDepth, isUnique, cacheSet, executorService, phaser);
		executorService.execute(thread);
		int phase = phaser.getPhase();
	    phaser.awaitAdvance(phase);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Finished.");
	}
}
