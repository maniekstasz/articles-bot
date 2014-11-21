package studia.articles.bot.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import studia.articles.bot.model.Document;
import studia.articles.bot.model.Root;
import studia.articles.bot.model.RootTotal;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ResponseParser {

	private ObjectMapper xmlMapper;

	public ResponseParser() {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		xmlMapper = new XmlMapper(module);
	}

	public List<Document> parseRoot(InputStream stream) {
		Root root;
		try {
			root = xmlMapper.readValue(stream, Root.class);
			return root.getDocuments();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Integer parseTotal(InputStream stream) throws JsonParseException, JsonMappingException, IOException {
		RootTotal root = xmlMapper.readValue(stream, RootTotal.class);
		return root.getTotalFound();
	}
}
