/**
 *	@(#)WeeklyView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;

public class WeeklyView implements CalendarView {
	private final FrameView frame;
	private Controller controller;

	public WeeklyView(FrameView frame) {
		this.frame = frame;
	}

	public String getLabel() {
		return "Week";
	}

	public JComponent getView() {
		return new JLabel(new ImageIcon(getClass().getResource("Weekly.png")));
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
