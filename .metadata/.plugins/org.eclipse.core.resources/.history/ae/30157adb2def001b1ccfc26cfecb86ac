/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;

public class MiniCalendarView extends JComponent {
	private final FrameView frame;
	private Controller controller;

	public MiniCalendarView(FrameView frame) {
		this.frame = frame;
	}

	public JComponent getView() {
		return new JLabel(new ImageIcon(getClass().getResource("MiniCal.png")));
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
