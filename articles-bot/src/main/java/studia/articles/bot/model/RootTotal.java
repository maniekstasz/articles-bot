package studia.articles.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class RootTotal {

	@JacksonXmlProperty(localName="totalfound")
	private Integer totalFound;

	public Integer getTotalFound() {
		return totalFound;
	}

	public void setTotalFound(Integer totalFound) {
		this.totalFound = totalFound;
	}

}
