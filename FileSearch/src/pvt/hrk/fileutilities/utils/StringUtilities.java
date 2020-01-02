package pvt.hrk.fileutilities.utils;

import java.util.function.BiPredicate;

public class StringUtilities {
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
}
