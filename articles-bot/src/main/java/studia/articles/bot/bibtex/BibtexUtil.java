package studia.articles.bot.bibtex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import studia.articles.bot.model.Document;

public class BibtexUtil {
	private static final Pattern abbr = Pattern.compile("\\((.*)\\)");

	public static String getBibTexKey(Document doc) {
		String[] parts = new String[5];
		parts[0] = removeNonAlphaNumeric(getJournalAbbr(doc.getPublisher()));
		parts[1] = removeNonAlphaNumeric(doc.getYear());
		parts[2] = removeNonAlphaNumeric(doc.getAuthor().split(";")[0]);
		parts[3] = removeNonAlphaNumeric(doc.getTitle());
		String result;
		if (doc.getTerms() != null) {
			String terms[] = new String[doc.getTerms().size()];
			parts[4] = removeNonAlphaNumeric(String.join(",", doc.getTerms()
					.toArray(terms)));
			result = String.join("-", parts);
		} else {
			parts[4] = "";
			String res = String.join("-", parts);
			result = res.substring(0, res.length() - 1);
		}
		if(result.length()>70)
			return result.substring(0, 70);
		return result;
	}

	private static String removeNonAlphaNumeric(String str) {
		return str.replaceAll("\\P{Alnum}", "");
	}

	private static String getJournalAbbr(String journal) {
		Matcher match = abbr.matcher(journal);
		if (match.find())
			return match.group(1);
		String initial = "";
		String[] split = journal.split(" ");
		for (String value : split) {
			initial += value.substring(0, 1).toUpperCase();
		}
		return initial;
	}

}
