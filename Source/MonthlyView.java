/**
 *	@(#)MonthlyView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;
import java.awt.*;

public class MonthlyView implements CalendarView {
	private final FrameView frame;
	private Controller controller;
	JTextPane textPane = new JTextPane();

	public MonthlyView(FrameView frame) {
		this.frame = frame;
		textPane.setPreferredSize(new Dimension(430,300));

	}

	public String getLabel() {
		return "Month";
	}

	public JTextPane getView() {
		return textPane;
	}
	public void attach(Controller controller) {
		this.controller = controller;
	}
}
