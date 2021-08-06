/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Shyam Vyas
 *	@version 1.00 2021/7/27
**/

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.*;

public class MiniCalendarView extends JPanel {
	private Controller controller;
	private LocalDate date;

	public MiniCalendarView() {
		super(new BorderLayout());
		this.date = LocalDate.now();
		updateCalendar();
	}

	public JPanel getView() {
		return this;
	}

	private void updateCalendar() {
		super.removeAll();
		super.add(new JLabel(this.date.getMonth() + " " + this.date.getYear(), SwingConstants.CENTER), BorderLayout.CENTER);
		super.add(getChildPanel(), BorderLayout.PAGE_END);
		super.revalidate();
	}

	private JPanel getChildPanel() {
		JPanel panel= new JPanel();
		LocalDate day = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
		LocalDate now = LocalDate.now();
		int maxDay = this.date.lengthOfMonth();

		panel.setLayout(new GridLayout(7,7));
		panel.add(new JLabel("Su"));
		panel.add(new JLabel("Mo"));
		panel.add(new JLabel("Tu"));
		panel.add(new JLabel("We"));
		panel.add(new JLabel("Th"));
		panel.add(new JLabel("Fr"));
		panel.add(new JLabel("Sa"));

		int counter = 0;
		while (counter++ < day.getDayOfWeek().getValue() % 7) {
			panel.add(new JLabel());
		}

		for (int dayCounter = 1; dayCounter <= maxDay; ++dayCounter) {
			JButton button= new JButton(Integer.toString(dayCounter));
			button.addActionListener(event -> {
				controller.setDate(LocalDate.of(this.date.getYear(), this.date.getMonth(), Integer.parseInt(event.getActionCommand())));
			});

			if (day.isEqual(now)) {
				button.setBackground(Color.GRAY);
			} else if (day.isEqual(this.date)) {
				button.setBackground(Color.YELLOW);
			} else {
				button.setBackground(Color.WHITE);
			}
			day = day.plusDays(1);

			panel.add(button);
		}

		counter += maxDay;
		while(counter++ <= 42) {
			panel.add(new JLabel());
		}
		return panel;
	}

	public void setDate(LocalDate date) {
		this.date = date;
		updateCalendar();
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
}
