package mywebscrapper.entity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileManager {
	private final String TARGET_FOLDER_NAME = "target";
	
	public void saveDocument(String url, int depth, String fileContent) {
		url = getFormattedUrlStringForFileSave(url);
		final String fullDirPath = String.format("./%s/%s/", TARGET_FOLDER_NAME, depth);
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
	
	private String getFormattedUrlStringForFileSave(String url) {
		return url.replaceAll("http(s)?://|www\\.", "").replaceAll("[^a-zA-Z0-9-_\\.]", "_");
	}
}
