package pvt.hrk.fileutilities.difffinder.ui.main;

import static pvt.hrk.fileutilities.difffinder.ui.helper.UIHelper.addSelectedFilePath;
import static pvt.hrk.fileutilities.difffinder.ui.helper.UIHelper.configureDirectoryChooser;
import static pvt.hrk.fileutilities.difffinder.ui.helper.UIHelper.configureLabel;

import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import pvt.hrk.fileutilities.difffinder.ui.helper.TextHelper;
import pvt.hrk.fileutilities.difffinder.ui.helper.TextHelper.MessageMode;
import pvt.hrk.fileutilities.difffinder.ui.localization.DisplayKeySupplier;
import pvt.hrk.fileutilities.difffinder.ui.service.BackgroundTask;

public class FileChooserMain extends Application {
	private static String lastVisitedDirectory = System.getProperty("user.home");
	private static final DisplayKeySupplier displayKey = DisplayKeySupplier.INSTANCE;
	File firstDirectory = null;
	File secondDirectory = null;
	File outputDirectory = null;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(displayKey.valueFor("APP_NAME"));
		GridPane root = new GridPane();



		ListView<Text> results = new ListView<>();

		TextFlow selectFirstDirectoryLabel = configureLabel();
		selectFirstDirectoryLabel.setVisible(false);

		Button selectFirstDirectoryButton = new Button(displayKey.valueFor("FIRST_DIR"));
		selectFirstDirectoryButton.setOnAction(e -> {
			DirectoryChooser chooser1 = configureDirectoryChooser(lastVisitedDirectory);
			File dir = chooser1.showDialog(primaryStage);
			if (dir != null) {
				firstDirectory = dir;
				lastVisitedDirectory = dir.getParent();
				addSelectedFilePath(selectFirstDirectoryLabel, dir);
				selectFirstDirectoryLabel.setVisible(true);
			}

		});

		TextFlow selectSecondDirectoryLabel = configureLabel();
		selectSecondDirectoryLabel.setVisible(false);
		Button selectSecondDirectoryButton = new Button(displayKey.valueFor("SECOND_DIR"));
		selectSecondDirectoryButton.setOnAction(e -> {
			DirectoryChooser chooser2 = configureDirectoryChooser(lastVisitedDirectory);
			File dir = chooser2.showDialog(primaryStage);
			if (dir != null) {
				secondDirectory = dir;
				lastVisitedDirectory = dir.getParent();
				addSelectedFilePath(selectSecondDirectoryLabel, dir);
				selectSecondDirectoryLabel.setVisible(true);
			}

		});

		TextFlow selectOutputDirectoryLabel = configureLabel();
		selectOutputDirectoryLabel.setVisible(false);
		Button selectOutputDirectoryButton = new Button(displayKey.valueFor("OUTPUT_DIR"));
		selectOutputDirectoryButton.setOnAction(e -> {
			DirectoryChooser chooser3 = configureDirectoryChooser(lastVisitedDirectory);
			File dir = chooser3.showDialog(primaryStage);
			if (dir != null) {
				outputDirectory = dir;
				lastVisitedDirectory = dir.getParent();
				addSelectedFilePath(selectOutputDirectoryLabel, dir);
				selectOutputDirectoryLabel.setVisible(true);
			}

		});

		Button startComparison = new Button(displayKey.valueFor("COMPARE"));
		startComparison.setOnAction(e -> {
			results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("INVOKING_MSG"), MessageMode.NEUTRAL.getConsumer()));
			results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("SELECTED_FIRST_DIR",new Object[] {firstDirectory}), MessageMode.NEUTRAL.getConsumer()));
			results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("SELECTED_SECOND_DIR",new Object[] {secondDirectory}), MessageMode.NEUTRAL.getConsumer()));
			results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("SELECTED_OUTPUT_DIR",new Object[] {outputDirectory}), MessageMode.NEUTRAL.getConsumer()));

			Task<Void> backgroundTask = new BackgroundTask(firstDirectory, secondDirectory, outputDirectory);
			final ProgressIndicator progressIndicator = new ProgressIndicator(0);
			progressIndicator.setProgress(0);
			progressIndicator.progressProperty().unbind();
			progressIndicator.progressProperty().bind(backgroundTask.progressProperty());
			root.getChildren().add(progressIndicator);
			backgroundTask.messageProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					results.getItems().add(TextHelper.stylizeTextAddTimestamp(newValue, MessageMode.SUCCESS.getConsumer()));
					
				}
			});
			backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
				@Override
				public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue,
						Worker.State newValue) {
					switch (newValue) {
					case RUNNING:
						results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("PROCESSING"), MessageMode.NEUTRAL.getConsumer()));
						return;
					case CANCELLED:
						results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("CANCELLED"), MessageMode.WARNING.getConsumer()));
						root.getChildren().remove(progressIndicator);
						return;
					case FAILED:
						results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("FAILED"), MessageMode.ERROR.getConsumer()));
						root.getChildren().remove(progressIndicator);
						return;
					case SUCCEEDED:
						results.getItems().add(TextHelper.stylizeTextAddTimestamp(displayKey.valueFor("SUCCESSFUL"), MessageMode.SUCCESS.getConsumer()));
						root.getChildren().remove(progressIndicator);
						return;
					default:
					}
				}

			});
			new Thread(backgroundTask).start();
		});

		VBox vBox = new VBox(selectFirstDirectoryButton, selectFirstDirectoryLabel, selectSecondDirectoryButton,
				selectSecondDirectoryLabel, selectOutputDirectoryButton, selectOutputDirectoryLabel);
		TitledPane titledPane = new TitledPane(displayKey.valueFor("BOX"), vBox);
		titledPane.autosize();
		titledPane.setCollapsible(false);

		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(0, 10, 0, 10));
		root.add(titledPane, 0, 0);

		root.add(startComparison, 0, 1);

		root.add(results, 0, 2);
		Scene scene = new Scene(root, 500, 600);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}