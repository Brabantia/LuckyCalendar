/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import javax.swing.*;

public class MiniCalendarView extends JComponent {
	private final FrameView frame;
	private Controller controller;
	MiniCalendarJPanel miniCalendarJPanel;

	public MiniCalendarView(FrameView frame) {
		this.frame = frame;
	}

	public JComponent getView() {
		if (miniCalendarJPanel == null){
			 miniCalendarJPanel = new MiniCalendarJPanel(controller);
		}
		return miniCalendarJPanel;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
