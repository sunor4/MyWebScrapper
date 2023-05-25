package mywebscrapper.utils;

public class ScrapperUtils {

	public boolean isUrlValid(String url) {
		return url.matches("^(http|https)://.+") && !url.isBlank();
	}
}
