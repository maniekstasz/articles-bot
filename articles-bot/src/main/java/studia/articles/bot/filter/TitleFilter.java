package studia.articles.bot.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class TitleFilter {

	private final Set<String> reservedTitles = new HashSet<String>();

	public TitleFilter(String path) {
		String line;
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path),
				Charset.forName("UTF-8"))) {
			while ((line = reader.readLine()) != null) {
				reservedTitles.add(prepareString(line));
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public boolean add(String title) {
		return !reservedTitles.contains(prepareString(title));
	}

	private String prepareString(String str) {
		return str.toLowerCase().replaceAll("\\P{Alnum}", "");
	}
}
