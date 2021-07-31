/**
 *	@(#)LuckyCalendar.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/30
**/

import java.io.IOException;

public class LuckyCalendar {
	public static final String DATA_PATH = "input.txt";

	public static void main(String[] args) throws IOException {
		CalendarModel model = new CalendarModel();
		if (!model.addFromFile(LuckyCalendar.class.getResource(DATA_PATH).getPath())) {
			System.err.println("Failed to load from file: " + DATA_PATH);
		}

		FrameView frame = new FrameView();

		Controller controller = new Controller(model, frame);
		model.attach(controller);
		frame.attach(controller);

		frame.display();
	}
}
