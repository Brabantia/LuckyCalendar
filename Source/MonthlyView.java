/**
 *	@(#)MonthlyView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;

public class MonthlyView implements CalendarView {
	private final FrameView frame;
	private Controller controller;

	public MonthlyView(FrameView frame) {
		this.frame = frame;
	}

	public String getLabel() {
		return "Month";
	}

	public JComponent getView() {
		return new JLabel(new ImageIcon(getClass().getResource("Monthly.png")));
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
