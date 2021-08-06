/**
 *	@(#)DailyView.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/5
**/

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DailyView implements CalendarView, Icon {
	public static final int LINE_HEIGHT = 30;
	private Controller controller;
	private LocalDate date = LocalDate.now();
	private Event[] events = new Event[0];

	public DailyView() {
		super();
	}

	public String getLabel() {
		return "Day";
	}

	public JComponent getView() {
		JPanel panel = new JPanel();
		panel.add(new JLabel(this));
		JScrollPane scrollPane = new JScrollPane(panel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(FrameView.PREFERRED_WIDTH, FrameView.PREFERRED_HEIGHT));
		return scrollPane;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setDate(LocalDate date) {
		this.date = date;
		refreshData();
	}

	public void refreshData() {
        this.events = this.controller.getDayEvents(this.date);
    }

	@Override
	public int getIconHeight() {
		return 24*LINE_HEIGHT;
	}

	@Override
	public int getIconWidth() {
		return FrameView.PREFERRED_WIDTH;
	}

	@Override
	public void paintIcon(Component c, Graphics graphics, int x, int y) {
		Graphics2D g = (Graphics2D)graphics;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getIconWidth(), getIconHeight());

		g.setColor(Color.BLACK);
		for (int a = 0; a < 24; ++a) {
			g.fillRect(0, a*LINE_HEIGHT, FrameView.PREFERRED_WIDTH, 2);
			g.drawString(a+":00", 2, (a+1)*LINE_HEIGHT-10);
		}

		for (Event event : this.events) {
			int start = (int)(getIconHeight()*DailyView.percentOfDay(event.getTime().getStart()));
			int end = (int)(getIconHeight()*DailyView.percentOfDay(event.getTime().getEnd()));
			g.setColor(Color.CYAN);
			g.fillRect(50, start, FrameView.PREFERRED_WIDTH-50, end-start);
			g.setColor(Color.BLACK);
			g.drawRect(51, start+1, FrameView.PREFERRED_WIDTH-52, end-start-2);
			g.drawRect(50, start, FrameView.PREFERRED_WIDTH-50, end-start);

			g.drawString(event.getName(), 54, start+15);
		}
	}

	private static double percentOfDay(LocalTime time) {
		double minute = time.getMinute() / 60.0;
		double hour = (time.getHour() + minute) / 24.0;

		return hour;
	}
}
