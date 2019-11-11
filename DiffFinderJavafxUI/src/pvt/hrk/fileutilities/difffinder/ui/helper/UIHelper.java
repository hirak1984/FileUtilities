package pvt.hrk.fileutilities.difffinder.ui.helper;

import java.io.File;

import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import pvt.hrk.fileutilities.difffinder.ui.localization.DisplayKeySupplier;

public class UIHelper {

	public static void addSelectedFilePath(TextFlow flow, File selectedFile) {
		if (selectedFile != null) {
			flow.getChildren().add(TextHelper.stylizeText(selectedFile.getAbsolutePath(), TextHelper.MessageMode.NEUTRAL.getConsumer()));
		}
	}

	public static DirectoryChooser configureDirectoryChooser(String initialDirectory) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle(DisplayKeySupplier.INSTANCE.valueFor("SELECT_DIR"));
		File defaultDirectory = new File(initialDirectory);
		chooser.setInitialDirectory(defaultDirectory);
		return chooser;

	}

	public static TextFlow configureLabel() {
		TextFlow selectedDirectory = new TextFlow();
		selectedDirectory.getChildren().add(TextHelper.stylizeText(DisplayKeySupplier.INSTANCE.valueFor("SELECTED_DIR"),
				TextHelper.MessageMode.BOLD_LABEL.getConsumer()));
		return selectedDirectory;
	}

}
