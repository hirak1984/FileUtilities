package pvt.hrk.fileutilities.difffinder.ui.localization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public enum DisplayKeySupplier {
	INSTANCE;

	private final static String RESOURCE_BUNDLE = "pvt.hrk.fileutilities.difffinder.resources.display";
	private Locale locale = getLocale();
	private ResourceBundle bundle = getBundle();

	private Locale getLocale() {
		String languageTagProperty = System.getProperty("language");
		if (languageTagProperty != null) {
			return Locale.forLanguageTag(languageTagProperty);
		} else {
			return Locale.getDefault();
		}
	}

	private ResourceBundle getBundle() {
			return ResourceBundle.getBundle(RESOURCE_BUNDLE, locale, this.getClass().getClassLoader());			
	}

	public String valueFor(String key, Object... params) {
		String retVal = null;
		if (bundle.keySet().contains(key)) {
			retVal =  bundle.getString(key);
			if(params!=null) {
			retVal = MessageFormat.format(retVal, params);	
			}
		} else {
			retVal =  "%"+key+"%";
		}
		return retVal;
	}
	
}
