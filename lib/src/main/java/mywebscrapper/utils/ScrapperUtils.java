package mywebscrapper.utils;

import java.net.URL;
import java.util.Map;

public class ScrapperUtils {

	public boolean isUrlValid(String url, boolean isUnique, Map<String, Boolean> cacheMap) {
		try {
			new URL(url).toURI();
		} catch (Exception e) {
			return false;
		}
		
		boolean isUniqueAndNotInMap = isUnique && !cacheMap.containsKey(url);
		return !url.isBlank() && (!isUnique || isUniqueAndNotInMap);
	}
	
	public String getFormattedUrlStringForFileSave(String url) {
		return url.replaceAll("http(s)?://|www\\.", "").replaceAll("[\\\\/:*?\"<>|]", "_");
	}
}
