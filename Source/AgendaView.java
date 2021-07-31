/**
 *	@(#)AgendaView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import javax.swing.*;
import java.awt.*;

public class AgendaView implements CalendarView {
	private final FrameView frame;
	private Controller controller;
	JTextPane textPane = new JTextPane();


	public AgendaView(FrameView frame) {
		this.frame = frame;
		textPane.setPreferredSize(new Dimension(430,300));

	}

	public String getLabel() {
		return "Agenda";
	}

	public JComponent getView() {
		return new JLabel(new ImageIcon(getClass().getResource("Agenda.png")));
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setFilters(String... filters) {
	}
}
