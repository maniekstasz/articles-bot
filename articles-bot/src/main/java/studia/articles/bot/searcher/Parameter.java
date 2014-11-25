package studia.articles.bot.searcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public enum Parameter {

	AUTHOR("au", null, false), TITLE("ti", null, false), ISBN("isbn", null,
			false), DOI("doi", null, false), YEAR_FROM("pys",
			createYearList(), false), YEAR_TO("pye", createYearList(), false), EVERYWHERE(
			"md", null, false), CONTENT_TYPE("ctype", createContentType(), true), OPEN_ACCESSS(
			"oa", createOpenAccessList(), true), SORT_FIELD("sortfield",
			createSortFieldList(), false), SORT_ORDER("sortorder",
			createSortOrderList(), false);

	private static final int FIRST_YEAR = 1890;

	private final String value;

	private List<String> list;

	private boolean multipleSelect = false;

	Parameter(String value, List<String> list, boolean multipleSelect) {
		this.value = value;
		this.list = list;
		this.multipleSelect = multipleSelect;
	}

	public String value() {
		return value;
	}

	public List<String> list() {
		return list;
	}

	public boolean isMultipleSelect() {
		return multipleSelect;
	}

	private static List<String> createYearList() {
		List<String> result = new ArrayList<String>();
		for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= FIRST_YEAR; i--) {
			result.add(new Integer(i).toString());
		}
		return result;
	}

	private static List<String> createContentType() {
		return new ArrayList<String>(Arrays.asList(new String[] {
				"Conferences", "Journals", "Books", "Early Access",
				"Standards/Educational", "Courses" }));
	}

	private static List<String> createOpenAccessList() {
		return new ArrayList<String>(Arrays.asList(new String[] { "1", "0" }));
	}

	private static List<String> createSortFieldList() {
		return new ArrayList<String>(Arrays.asList(new String[] { "au", "ti",
				"cs", "jn", "an", "py" }));
	}

	private static List<String> createSortOrderList() {
		return new ArrayList<String>(
				Arrays.asList(new String[] { "asc", "desc" }));
	}
}
