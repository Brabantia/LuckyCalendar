/**
 *	@(#)LuckyCalendar.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

public class LuckyCalendar {
	public static final String DATA_PATH = "";

	public static void main(String[] args) {
		CalendarModel model = new CalendarModel();
		model.addFromFile(DATA_PATH);

		FrameView frame = new FrameView();

		Controller controller = new Controller(model, frame);
		model.attach(controller);
		frame.attach(controller);

		frame.display();
	}
}
