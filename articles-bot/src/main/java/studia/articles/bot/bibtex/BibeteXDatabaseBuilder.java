package studia.articles.bot.bibtex;

import java.util.Collection;
import java.util.List;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;
import org.jbibtex.StringValue.Style;

import studia.articles.bot.model.Document;

public class BibeteXDatabaseBuilder {

	private static void append(Document doc, BibTeXDatabase database) {
		BibTeXEntry bte = new BibTeXEntry(BibTeXEntry.TYPE_ARTICLE, new Key(
				BibtexUtil.getBibTexKey(doc)));
		bte.addField(BibTeXEntry.KEY_YEAR, new StringValue(doc.getYear(),
				Style.BRACED));
		bte.addField(BibTeXEntry.KEY_AUTHOR, new StringValue(doc.getAuthor(),
				Style.BRACED));
		bte.addField(BibTeXEntry.KEY_TITLE, new StringValue(doc.getTitle(),
				Style.BRACED));
		bte.addField(new Key("abstract"), new StringValue(doc.getAbstr(),
				Style.BRACED));
		bte.addField(BibTeXEntry.KEY_JOURNAL,
				new StringValue(doc.getPublisher(), Style.BRACED));
		bte.addField(new Key("pdfUrl"), new StringValue(doc.getPdfUrl(),
				Style.BRACED));
		String keywords = "";
		if (doc.getTerms() != null)
			keywords = String.join(", ", doc.getTerms());
		bte.addField(new Key("Keywords"), new StringValue(keywords,
				Style.BRACED));
		database.addObject(bte);
	}

	public static BibTeXDatabase build(Collection<Document> documents) {
		BibTeXDatabase database = new BibTeXDatabase();
		for (Document doc : documents) {
			append(doc, database);
		}
		return database;
	}

}
