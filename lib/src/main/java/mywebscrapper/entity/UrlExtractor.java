package mywebscrapper.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mywebscrapper.utils.ScrapperUtils;

public class UrlExtractor {
	private final ScrapperUtils scrapperUtils = new ScrapperUtils();
	
	public Set<String> extractUrlsFromDocument(Document document, int maxNumOfUrls, boolean isUnique) {
		List<Element> extractedAnchors = document.select("a[href]");
		Set<String> urls = new HashSet<String>();
		
		for (int i = 0; i < extractedAnchors.size() && urls.size() < maxNumOfUrls; i++) {
			String anchorUrl = extractedAnchors.get(i).absUrl("href");
			if (scrapperUtils.isUrlValid(anchorUrl)) {
				urls.add(anchorUrl);
			}
		}
		
		return urls;
	}
}
