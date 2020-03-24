package pvt.hrk.fileutilities.filesearch.core.searchhandlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pvt.hrk.fileutilities.filesearch.config.ConfigHolderSingleton;
import pvt.hrk.fileutilities.utils.ObjectUtils;
import pvt.hrk.fileutilities.utils.StringUtilities;

public class SearchHandlerFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(SearchHandlerFactory.class);

	public static SearchHandler Handler(File file) {
		ContentTypes contentType = probeContentType(file);
		switch (contentType) {
		case DIRECTORY:
			return new DirectorySearchHandler(file);
		case PDF:
			return new PDFFileSearchHandler(file);
		case TEXT:
			return new FileSearchHandler(file);
		case COMPRESSED:
			return ConfigHolderSingleton.INSTANCE.searchInZip() ? new ZipFileSearchHandler(file)
					: new NoOpSearchHandler(file);
		default:
			return new NoOpSearchHandler(file);
		}
	}

	private static enum ContentTypes {
		UNKNOWN, DIRECTORY, COMPRESSED, BINARY, TEXT, PDF;
	}

	private static ContentTypes probeContentType(File file) {
		if (file == null) {
			return ContentTypes.UNKNOWN;
		}
		if (file.isDirectory()) {
			return ContentTypes.DIRECTORY;
		}
		String contentType = null;
		try {
			contentType = Files.probeContentType(file.toPath());
			LOGGER.trace("Content type for file {} is {}", file.getName(), contentType);
		} catch (IOException e) {
			ObjectUtils.handleException(file, e);
		}
		if (contentType == null) {
			if (StringUtilities.containsAnyIgnoreCase.test(file.getAbsolutePath(), new String[] { "gwp" })) {
				return ContentTypes.TEXT;
			} else {
				return ContentTypes.UNKNOWN;
			}

		}
		if (StringUtilities.containsIgnoreCase.test(contentType, "compressed")) {
			return ContentTypes.COMPRESSED;
		}
		if (StringUtilities.containsIgnoreCase.test(contentType, "pdf")) {
			return ContentTypes.PDF;
		}

		if (StringUtilities.containsAnyIgnoreCase.test(contentType,
				new String[] { "text", "ms-excel", "msword", "css", "application/xml" })) {
			return ContentTypes.TEXT;
		}
		return ContentTypes.BINARY;
	}
}
