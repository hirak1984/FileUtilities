package pvt.hrk.fileutilities.difffinder.ui.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextHelper {


	public enum MessageMode {
		ERROR(t -> t.setFill(Color.RED)),
		WARNING(t -> t.setFill(Color.YELLOW)),
		SUCCESS(t -> t.setFill(Color.GREEN)),
		NEUTRAL(t -> t.setFill(Color.BLACK)),
		BOLD_LABEL(t -> {
					t.setFill(Color.SADDLEBROWN);
					t.setStyle("-fx-font-weight: bold");
				});

		private Consumer<Text> textConsumer;

		private MessageMode(Consumer<Text> textConsumer) {
			this.textConsumer = textConsumer;
		}

		public Consumer<Text> getConsumer() {
			// any common styling first and then apply individual ones
			return new Consumer<Text>() {
				@Override
				public void accept(Text t) {
					t.setFont(new Font(15));
				}
			}.andThen(textConsumer);
		}

	}
private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH24:mm:ss");
	public static Text stylizeText(String value, Consumer<Text> textStyleConsumer) {
		Text text = new Text(value);
		textStyleConsumer.accept(text);
		return text;
	}
	public static Text stylizeTextAddTimestamp(String value, Consumer<Text> textStyleConsumer) {
		String val = formatter.format(new Date(System.currentTimeMillis()))+" "+value;
		Text text = new Text(val);
		textStyleConsumer.accept(text);
		return text;
	}
	
}
