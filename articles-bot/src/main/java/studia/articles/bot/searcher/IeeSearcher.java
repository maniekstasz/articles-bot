package studia.articles.bot.searcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studia.articles.bot.model.Document;
import studia.articles.bot.parser.ResponseParser;

public class IeeSearcher extends IeeConnector {

	private static final String baseURL = "http://ieeexplore.ieee.org/gateway/ipsSearch.jsp";

	private SearchQueryBuilder queryBuilder;
	private ResponseParser parser;
	private int size;
	private int current;
	private int total;
	boolean initialized = false;

	public IeeSearcher(SearchQueryBuilder queryBuilder, ResponseParser parser,
			int size, String socksAddress, int socksPort, int throughPort) throws JsonParseException, JsonMappingException, IOException {
		super(socksAddress, socksPort, throughPort);
		this.queryBuilder = queryBuilder;
		this.parser = parser;
		this.size = size;
		init();
	}

	public IeeSearcher(SearchQueryBuilder queryBuilder, ResponseParser parser,
			String socksAddress, int socksPort, int throughPort) throws JsonParseException, JsonMappingException, IOException {
		this(queryBuilder, parser, 100, socksAddress, socksPort, throughPort);
	}

	private void init() throws JsonParseException, JsonMappingException, IOException {
		total = parser.parseTotal(search());
		initialized = true;
		if (total > 0)
			current = -99;
	}

	public boolean hasNext() {
		return (current + size) < total;
	}

	public boolean hasPrev() {
		return (current - size) > 0;
	}

	public List<Document> prev() throws IOException {
		current -= size;
		return parser.parseRoot(search());
	}

	public List<Document> next() throws IOException {
		current += size;
		return parser.parseRoot(search());
	}

	private InputStream search() throws IOException {
		queryBuilder.setSize(size);
		if (current != 0)
			queryBuilder.setStartingFrom(current);
		return getInputStream(baseURL + queryBuilder.build(), true);
	}

	public int getTotal() {
		return total;
	}

}
