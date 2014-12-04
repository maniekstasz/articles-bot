package studia.articles.bot.searcher;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studia.articles.bot.parser.ResponseParser;

public class ConnectorFactory {

	private String socksAddress;
	private int socksPort = -1;
	private int throughPort = -1;

	public ConnectorFactory(String socksAddress, int socksPort, int throughPort) {
		this.socksAddress = socksAddress;
		this.socksPort = socksPort;
		this.throughPort = throughPort;
	}
	
	public ConnectorFactory() {

	}

	public IeeSearcher getIeeSearcher(SearchQueryBuilder builder,
			ResponseParser parser) throws JsonParseException,
			JsonMappingException, IOException {
		if (socksAddress != null && socksPort != -1 && throughPort != -1)
			return new IeeSearcher(builder, parser, socksAddress, socksPort,
					throughPort);
		return new IeeSearcher(builder, parser);

	}

	public PDFDownloader getPdfDownloader(String path) {
		if (socksAddress != null && socksPort != -1 && throughPort != -1)
			return new PDFDownloader(path, socksAddress, socksPort, throughPort);
		return new PDFDownloader(path);
	}
}
