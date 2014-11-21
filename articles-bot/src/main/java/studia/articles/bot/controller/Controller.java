package studia.articles.bot.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ObjectResolutionException;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studia.articles.bot.bibtex.BibeteXDatabaseBuilder;
import studia.articles.bot.model.Document;
import studia.articles.bot.parser.ResponseParser;
import studia.articles.bot.searcher.ConnectorFactory;
import studia.articles.bot.searcher.IeeSearcher;
import studia.articles.bot.searcher.PDFDownloader;
import studia.articles.bot.searcher.SearchQueryBuilder;

public class Controller {

	private IeeSearcher searcher;
	private ResponseParser parser = new ResponseParser();

	private Set<Document> savedDocuments = new HashSet<Document>();
	private final ControllerListener listener;
	private ConnectorFactory connectorFactory;

	public Controller(ControllerListener listener,String socksAddress, int socksPort,
			int throughPort) {
		this.listener = listener;
		connectorFactory = new ConnectorFactory(socksAddress, socksPort, throughPort);
	}
	
	public Controller(ControllerListener listener){
		this(listener, "127.0.0.1", 80, 8080);
	}

	public int search(SearchQueryBuilder builder) {
		try {
			searcher = connectorFactory.getIeeSearcher(builder, parser);
			return searcher.getTotal();
		} catch (JsonParseException | JsonMappingException e) {
			listener.onOtherException(e);
		} catch (IOException e) {
			listener.onConnectionException(e);
		}
		return -1;
	}

	public boolean hasNext() {
		return searcher.hasNext();
	}

	public boolean hasPrev() {
		return searcher.hasNext();
	}

	public List<Document> next() {
		try {
			return searcher.next();
		} catch (IOException e) {
			listener.onConnectionException(e);
		}
		return null;
	}

	public List<Document> prev() {
		try {
			return searcher.prev();
		} catch (IOException e) {
			listener.onConnectionException(e);
		}
		return null;
	}

	public boolean isSaved(Document document) {
		return savedDocuments.contains(document);
	}

	public void save(Document document) {
		if (!isSaved(document))
			savedDocuments.add(document);
	}

	public void delete(Document document) {
		savedDocuments.remove(document);
	}

	public void saveToBibetex(String path) throws IOException {
		BibTeXDatabase database = BibeteXDatabaseBuilder.build(savedDocuments);
		new BibTeXFormatter().format(database, new FileWriter(path));
	}

	public void downloadFiles(final String bibetexDatabasePath,
			final String filesPath) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BibTeXParser bibtexParser;
				try {
					bibtexParser = new BibTeXParser();

					BibTeXDatabase database = bibtexParser
							.parse(new FileReader(bibetexDatabasePath));
					PDFDownloader downloader = connectorFactory.getPdfDownloader(filesPath);
					int i = 0;
					for (BibTeXEntry en : database.getEntries().values()) {
						String fileName = en.getKey().getValue();
						listener.onFileDownloadingStart(
								en.getField(BibTeXEntry.KEY_TITLE).toUserString(),
								database.getEntries().size(), i++);
						try {
							downloader.downloadAndSave(
									en.getField(new Key("pdfUrl"))
											.toUserString(), fileName);
						} catch (IOException e) {
							if (e instanceof ConnectionException) {
								listener.onConnectionException(e);
							} else {
								listener.onFileException(e);
							}
							if(downloader.fileExists(fileName))
								new File(downloader.getAbsolutePath(fileName)).delete();
							break;
						}
					}

				} catch (TokenMgrException | ParseException e) {
					listener.onOtherException(e);
				} catch (IOException e) {
					listener.onFileException(e);
				}
			}
		}).start();
	}
}
