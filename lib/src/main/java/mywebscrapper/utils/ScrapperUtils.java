package mywebscrapper.utils;

import java.net.URL;
import java.util.Map;

public class ScrapperUtils {

	public boolean isUrlValid(String url) {
		try {
			new URL(url).toURI();
		} catch (Exception e) {
			return false;
		}
		
		return !url.isBlank();
	}
	
	public boolean isUrlValidAndUnique(String url, boolean isUnique, Map<String, Boolean> cacheMap) {
		boolean isUniqueAndNotInMap = isUnique && !cacheMap.containsKey(url);
		return !isUrlValid(url) && (!isUnique || isUniqueAndNotInMap);
	}
	
	public String getFormattedUrlStringForFileSave(String url) {
		return url.replaceAll("http(s)?://|www\\.", "").replaceAll("[\\\\/:*?\"<>|]", "_");
	}
}
