package studia.articles.bot.searcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class SearchQueryBuilder {

	private final String RECORD_NUMBER = "hc";
	private final String STARTING_FROM = "rs";

	private Map<String, Object> parameters = new HashMap<String, Object>();

	public SearchQueryBuilder append(Parameter parameter, Object value) {
		parameters.put(parameter.value(), value);
		return this;
	}

	public SearchQueryBuilder setSize(Integer size) {
		parameters.put(RECORD_NUMBER, size);
		return this;
	}

	public SearchQueryBuilder setStartingFrom(Integer startingFrom) {
		parameters.put(STARTING_FROM, startingFrom);
		return this;
	}

	public String build() {
		int i = 0;
		StringBuilder urlBuilder = new StringBuilder();
		for (String key : parameters.keySet()) {
			if (i++ == 0)
				urlBuilder.append("?");
			else
				urlBuilder.append("&");
			String value;
			try {
				value = URLEncoder.encode(parameters.get(key).toString(),
						"UTF-8");
				urlBuilder.append(key).append("=").append(value);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return urlBuilder.toString();
	}
}
