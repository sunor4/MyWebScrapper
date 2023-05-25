package mywebscrapper.entity;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentFetcher {
	public Document getDocument(String url, int depth) {
		Connection connection = Jsoup.connect(url);
		Document document = null;
		try {
			document = connection.get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return document;
	}
}
