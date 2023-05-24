package mywebscrapper;

import mywebscrapper.businesslogic.MyWebScrapper;
import mywebscrapper.utils.ScrapperUtils;

public class Main {

	public static void main(String[] args) {
		validateInput(args);
		final String url = args[0];
		final int maxNumOfUrls = Integer.parseInt(args[1]);
		final int depth = Integer.parseInt(args[2]);
		final boolean isUnique = Boolean.parseBoolean(args[3]);
		MyWebScrapper myWebScrapper = new MyWebScrapper(url, maxNumOfUrls, depth, isUnique);
		myWebScrapper.run();
	}

	private static void validateInput(String... args) throws IllegalArgumentException {
		ScrapperUtils scrapperUtils = new ScrapperUtils();
		final String url = args[0];
		final String maxNumOfUrlsStr = args[1];
		final String depthStr = args[2];
		final String isUniqueStr = args[3];
		if (!scrapperUtils.isUrlValid(url)) {
			throw new IllegalArgumentException(String.format("%s is not a valid url", url));
		}

		int maxNumOfUrls, depth;
		try {
			maxNumOfUrls = Integer.parseInt(maxNumOfUrlsStr);
			depth = Integer.parseInt(depthStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		if (maxNumOfUrls <= 0) {
			throw new IllegalArgumentException("maxNumOfUrls must be greater than 0");
		}

		if (depth < 0) {
			throw new IllegalArgumentException("depth must be greater or equal to 0");
		}

		if (!Boolean.TRUE.toString().equalsIgnoreCase(isUniqueStr)
				&& !Boolean.FALSE.toString().equalsIgnoreCase(isUniqueStr)) {
			throw new IllegalArgumentException(String.format("%s is not valid for \"isUnique\" flag", isUniqueStr));
		}
	}
}
