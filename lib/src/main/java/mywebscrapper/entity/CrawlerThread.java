package mywebscrapper.entity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mywebscrapper.utils.ScrapperUtils;

public class CrawlerThread implements Runnable {
	private final String url;
	private final int maxNumOfUrls;
	private final Map<String, Boolean> cacheMap;
	private int currentDepth;
	private final int maxDepth;
	private final boolean isUnique;
	private final ScrapperUtils scrapperUtils = new ScrapperUtils();

	public CrawlerThread(String url, int maxNumOfUrls, int currentDepth, int maxDepth, boolean isUnique,
			Map<String, Boolean> cacheMap) {
		this.url = url;
		this.maxNumOfUrls = maxNumOfUrls;
		this.currentDepth = currentDepth;
		this.maxDepth = maxDepth;
		this.isUnique = isUnique;
		this.cacheMap = cacheMap;
	}

	@Override
	public void run() {
		crawl(url, maxNumOfUrls, maxDepth, isUnique);
	}

	private void crawl(String url, int maxNumOfUrls, int maxDepth, boolean isUnique) {
		Document document = getDocument(url, currentDepth);
		if (currentDepth < maxDepth) {
			if (document != null) {
				List<Element> links = document.select("a[href]");
				for (int i = 0, currentNumOfUrls = 0; i < links.size() && currentNumOfUrls < maxNumOfUrls; i++) {
					String linkUrl = links.get(i).absUrl("href");
					if (scrapperUtils.isUrlValid(linkUrl, isUnique, cacheMap)) {
						this.cacheMap.putIfAbsent(linkUrl, true);
						currentNumOfUrls++;
						Thread thread = new Thread(new CrawlerThread(linkUrl, maxNumOfUrls, currentDepth + 1, maxDepth,
								isUnique, cacheMap));
						thread.start();
					}
				}
			}
		}
	}

	private Document getDocument(String url, int depth) {
		Connection connection = Jsoup.connect(url);
		Document document = null;
		try {
			document = connection.get();
			if (connection.response().statusCode() == Response.Status.OK.getStatusCode()) {
				this.cacheMap.putIfAbsent(url, true);
				saveDocument(url, depth, document.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document;
	}

	private void saveDocument(String url, int depth, String fileContent) {
		url = scrapperUtils.getFormattedUrlStringForFileSave(url);
		final String fullDirPath = String.format("./target/%s/", depth);
		final String fullFilePath = String.format("%s/%s.html", fullDirPath, url);
		final Path pathToDir = Paths.get(fullDirPath);
		final Path pathToFile = Paths.get(fullFilePath);
		try {
			Files.createDirectories(pathToDir);
			File newFile = pathToFile.toFile();
			newFile.createNewFile();
			Files.write(pathToFile, Arrays.asList(fileContent), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
