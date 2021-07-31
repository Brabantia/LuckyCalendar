/**
 *	@(#)DailyView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;

public class DailyView implements CalendarView {
	private final FrameView frame;
	private Controller controller;

	public DailyView(FrameView frame) {
		this.frame = frame;
	}

	public String getLabel() {
		return "Day";
	}

	public JComponent getView() {
		return new JLabel(new ImageIcon(getClass().getResource("Daily.png")));
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
