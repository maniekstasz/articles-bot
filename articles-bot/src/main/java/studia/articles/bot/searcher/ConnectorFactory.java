package studia.articles.bot.searcher;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studia.articles.bot.parser.ResponseParser;

public class ConnectorFactory {

	private String socksAddress;
	private int socksPort;
	private int throughPort;

	public ConnectorFactory(String socksAddress, int socksPort,
			int throughPort) {
		this.socksAddress = socksAddress;
		this.socksPort = socksPort;
		this.throughPort = throughPort;
	}

	public IeeSearcher getIeeSearcher(SearchQueryBuilder builder,
			ResponseParser parser) throws JsonParseException, JsonMappingException, IOException {
		return new IeeSearcher(builder, parser,socksAddress, socksPort,throughPort);
	}

	public PDFDownloader getPdfDownloader(String path) {
		return new PDFDownloader(path,socksAddress, socksPort,throughPort);
	}
}
