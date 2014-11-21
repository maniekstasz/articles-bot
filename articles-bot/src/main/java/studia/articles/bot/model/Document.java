package studia.articles.bot.model;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

	private String title;
	@JacksonXmlProperty(localName = "authors")
	private String author;
	@JacksonXmlProperty(localName = "py")
	private String year;
	@JacksonXmlProperty(localName = "pdf")
	private String pdfUrl;
	@JacksonXmlProperty(localName = "abstract")
	private String abstr;

	@JacksonXmlElementWrapper
	@JacksonXmlProperty(localName = "controlledterms")
	private List<String> terms;
	@JacksonXmlProperty(localName = "pubtitle")
	private String publisher;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public String getAbstr() {
		return abstr;
	}

	public void setAbstr(String abstr) {
		this.abstr = abstr;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(prepare(title))
				.append(prepare(author)).build();
	}

	@Override
	public boolean equals(Object obj) {
		Document doc = (Document) obj;
		return new EqualsBuilder()
				.append(prepare(doc.getTitle()), prepare(this.getTitle()))
				.append(prepare(doc.getAuthor()), prepare(this.getAuthor()))
				.build();
	}

	private String prepare(String str) {
		return str.toLowerCase().replaceAll(" ", "");
	}
}
