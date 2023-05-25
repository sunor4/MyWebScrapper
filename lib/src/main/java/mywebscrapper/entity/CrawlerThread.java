package mywebscrapper.entity;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

import org.jsoup.nodes.Document;

public class CrawlerThread implements Runnable {
	private final String url;
	private final int maxNumOfUrls;
	private final Set<String> cacheSet;
	private int currentDepth;
	private final int maxDepth;
	private final boolean isUnique;
	private final ExecutorService executorService;
	private final DocumentFetcher documentFetcher = new DocumentFetcher();
	private final UrlExtractor urlExtractor = new UrlExtractor();
	private final FileManager fileManager = new FileManager();
	private final Phaser phaser;

	public CrawlerThread(String url, int maxNumOfUrls, int currentDepth, int maxDepth, boolean isUnique,
			Set<String> cacheSet, ExecutorService executorService, Phaser phaser) {
		this.url = url;
		this.maxNumOfUrls = maxNumOfUrls;
		this.cacheSet = cacheSet;
		this.currentDepth = currentDepth;
		this.maxDepth = maxDepth;
		this.isUnique = isUnique;
		this.executorService = executorService;
		this.phaser = phaser;
		this.phaser.register();
	}

	@Override
	public void run() {
		System.out.println(String.format("%s - %s", currentDepth, url));
		Document document = documentFetcher.getDocument(url, currentDepth);
		if (document != null) {
			fileManager.saveDocument(url, currentDepth, document.html());
			if (currentDepth < maxDepth) {
				Set<String> urls = urlExtractor.extractUrlsFromDocument(document, maxNumOfUrls, cacheSet, isUnique);
				int numOfThreads = Math.min(urls.size(), maxNumOfUrls);
				int i = 0;
				for (String newUrl : urls) {
					if (i < numOfThreads) {
						cacheSet.add(newUrl);
						Thread thread = new Thread(new CrawlerThread(newUrl, maxNumOfUrls, currentDepth + 1, maxDepth,
								isUnique, cacheSet, executorService, phaser));
						try {
							executorService.submit(thread);
							i++;
						} catch (Exception e) {
							System.out.println("Rejected");
						}
					} else {
						break;
					}
				}
			}
		}
		
		phaser.arrive();
	}
}
