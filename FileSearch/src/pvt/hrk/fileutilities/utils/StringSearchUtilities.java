package pvt.hrk.fileutilities.utils;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class StringSearchUtilities {
	public static final BiPredicate<String, String> containsIgnoreCase = (str, contains) -> {
		if (str == null || contains == null) {
			return false;
		}
		return str.toLowerCase().contains(contains.toLowerCase());
	};

	public static final BiPredicate<String, String> endsWithIgnoreCase = (str, endsWith) -> {
		if (str == null || endsWith == null) {
			return false;
		}
		return str.toLowerCase().endsWith(endsWith.toLowerCase());
	};
	public static final BiPredicate<String, String> startsWithIgnoreCase = (str, startsWith) -> {
		if (str == null || startsWith == null) {
			return false;
		}
		return str.toLowerCase().startsWith(startsWith.toLowerCase());
	};
	public enum StringSearchModes {

		STARTSWITH(s -> s.substring(1, s.length()),StringSearchUtilities.startsWithIgnoreCase),
		ENDSWITH(s -> s.substring(0, s.length() - 1),StringSearchUtilities.endsWithIgnoreCase), 
		CONTAINS(s -> s.substring(1, s.length() - 1),StringSearchUtilities.containsIgnoreCase),
		NOTSURE(s -> s,StringSearchUtilities.containsIgnoreCase);

		private Function<String, String> prep;
		private BiPredicate<String, String> search;

		StringSearchModes(Function<String, String> prep, BiPredicate<String, String> search) {
			this.prep = prep;
			this.search = search;
		}

		public boolean doSearch(String line, String searchString) {
			if(line==null || searchString==null) {
				return false;
			}
			return search.test(line, prep.apply(searchString));
		}

		public static StringSearchModes evaluate(String searchString) {
			StringSearchModes retVal = null;
			boolean startsWith = false;
			boolean endsWith = false;
			if (searchString.charAt(0) == '%') {
				startsWith = true;
			}
			if (searchString.charAt(searchString.length() - 1) == '%') {
				endsWith = true;
			}
			if (startsWith) {
				retVal = endsWith ? StringSearchModes.CONTAINS : StringSearchModes.STARTSWITH;
			} else if (endsWith) {
				retVal = startsWith ? StringSearchModes.CONTAINS : StringSearchModes.ENDSWITH;
			} else {
				retVal = StringSearchModes.NOTSURE;
			}
			return retVal;
		}

	}
}
