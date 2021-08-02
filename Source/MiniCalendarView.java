/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Shyam Vyas
 *	@author Yorick van de Water
 *	@version 1.00 2021/8/2
**/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class MiniCalendarView extends JPanel {
	private Controller controller;
	private LocalDate date;

	public MiniCalendarView(LocalDate date) {
		super(new BorderLayout());
		this.date = date;
		updateCalendar();
	}

	public JPanel getView() {
		return this;
	}

	private void updateCalendar() {
		SpinnerNumberModel yearModel = new SpinnerNumberModel(this.date.getYear(), 0, 3000, 1);
		JComboBox<Month> months = new JComboBox<Month>(Month.values());
		JSpinner years = new JSpinner(yearModel);
		JButton today = new JButton("today");
		JButton left = new JButton("<");
		JButton right = new JButton(">");

		today.setBackground(Color.WHITE);
		left.setBackground(Color.WHITE);
		right.setBackground(Color.WHITE);
		months.setBackground(Color.WHITE);
		months.setSelectedItem(this.date.getMonth());
		years.setBackground(Color.WHITE);
		years.setEditor(new JSpinner.NumberEditor(years, "#"));

		today.addActionListener(event -> {
			this.controller.setDate(LocalDate.now());
		});
		left.addActionListener(event -> {
			this.controller.setDate(this.date.minusMonths(1));
		});
		months.addActionListener(event -> {
			this.controller.setDate(this.date.withMonth(months.getItemAt(months.getSelectedIndex()).getValue()));
		});
		right.addActionListener(event -> {
			this.controller.setDate(this.date.plusMonths(1));
		});
		years.addChangeListener(event -> {
			this.controller.setDate(this.date.withYear(yearModel.getNumber().intValue()));
		});

		super.removeAll();
		JPanel panel = new JPanel();
		panel.add(today);
		panel.add(left);
		panel.add(months);
		panel.add(right);
		panel.add(years);

		super.add(panel, BorderLayout.PAGE_START);
		super.add(getChildPanel(), BorderLayout.PAGE_END);
		super.revalidate();
	}

	private JPanel getChildPanel() {
		JPanel panel= new JPanel();
		LocalDate day = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
		LocalDate now = LocalDate.now();
		int maxDay = this.date.lengthOfMonth();

		panel.setLayout(new GridLayout(7,7));
		panel.add(new JLabel("Su", SwingConstants.CENTER));
		panel.add(new JLabel("Mo", SwingConstants.CENTER));
		panel.add(new JLabel("Tu", SwingConstants.CENTER));
		panel.add(new JLabel("We", SwingConstants.CENTER));
		panel.add(new JLabel("Th", SwingConstants.CENTER));
		panel.add(new JLabel("Fr", SwingConstants.CENTER));
		panel.add(new JLabel("Sa", SwingConstants.CENTER));

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
