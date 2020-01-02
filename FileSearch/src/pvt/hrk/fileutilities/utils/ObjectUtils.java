package pvt.hrk.fileutilities.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;

public final class ObjectUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(ConfigHolderSingleton.class);

	public static <T> boolean isNullOrEmpty(final T[] arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isNullOrEmpty(final String str) {
		return str == null || str.length() == 0;
	}

	public static <T> boolean isNullOrEmpty(final Collection<T> col) {
		return col == null || col.size() == 0;
	}

	public static <K, V> boolean isNullOrEmpty(final Map<K, V> map) {
		return map == null || map.size() == 0;
	}

	public static final Predicate<String> isZipFile = filePath -> {
		return Stream.of(new String[] { "zip", "jar" }).parallel()
				.anyMatch(s -> StringUtilities.endsWithIgnoreCase.test(filePath, s));
	};

	public static final void handleException(File f, Throwable t) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.warn("Error reading file:{} ,Error Message : {}",f.toPath(),t.getLocalizedMessage());
		}
	}
}
