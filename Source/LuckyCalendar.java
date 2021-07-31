/**
 *	@(#)LuckyCalendar.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/31
**/

import java.net.URL;

public class LuckyCalendar {
	public static final String INPUT_FILE = "input.txt";

	public static void main(String[] args) throws Exception {
		CalendarModel model = new CalendarModel();

		URL url = LuckyCalendar.class.getResource(INPUT_FILE);
		if (url == null) {
			System.err.println("Failed to load from file: " + INPUT_FILE);
		} else {
			model.addFromFile(url.toURI());
		}

		FrameView frame = new FrameView();

		Controller controller = new Controller(model, frame);
		model.attach(controller);
		frame.attach(controller);

		frame.display();
	}
}
