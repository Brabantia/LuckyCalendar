/**
 *	@(#)MiniCalendarView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/7/28
**/

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class MiniCalendarView extends JPanel {
	private final static String week[] = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	private JLabel dayLabel[][] = new JLabel[6][7];
	private JLabel monthLabel;
	private Controller controller;
	private LocalDate date;
	private LocalDate selectDay;

	public MiniCalendarView() {
		date = LocalDate.now();
		selectDay = LocalDate.of(date.getYear(),date.getMonth(),date.getDayOfMonth());

		setLayout(new BorderLayout());

		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		monthLabel = new JLabel();
		northPanel.add(monthLabel);

		JLabel preMonth = new JLabel("  <  ");
		preMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				calendarAdd(-1);
			}
		});
		northPanel.add(preMonth);
		JLabel nextMonth = new JLabel("  >  ");
		nextMonth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				calendarAdd(1);
			}
		});
		northPanel.add(nextMonth);
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridLayout(7, 7, 1, 1));
		centerPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		for (String s : week) {
			JLabel weekButton = new JLabel(s);
			weekButton.setBackground(Color.WHITE);
			weekButton.setHorizontalAlignment(JLabel.CENTER);
			centerPanel.add(weekButton);
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				JPanel tempJpanel = new JPanel();
				dayLabel[i][j] = new JLabel();
				tempJpanel.setBackground(Color.white);
				dayLabel[i][j].setHorizontalAlignment(JLabel.CENTER);

				centerPanel.add(tempJpanel);
				tempJpanel.add(dayLabel[i][j]);

				dayLabel[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						for (int i = 0; i < 6; i++) {
							for (int j = 0; j < 7; j++) {
								dayLabel[i][j].getParent().setBackground(Color.white);
							}
						}
						((JLabel)e.getSource()).getParent().setBackground(Color.gray);

						selectDay = LocalDate.of(date.getYear(),date.getMonth().getValue(),Integer.parseInt(((JLabel)e.getSource()).getText()));
						System.out.println(Integer.parseInt(((JLabel)e.getSource()).getText()));
						System.out.println(selectDay);
						controller.setDate(selectDay);

						setVisible(true);
					}
				});
			}
		}
		add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		add(southPanel, BorderLayout.SOUTH);
		updateCalendar();

		setVisible(true);
	}

	private void updateCalendar() {
		int i = 0, j = (date.getDayOfWeek().getValue()-1) % 7;
		int maxDay = date.getMonth().maxLength();
		for (int k = 0; k < j; k++) dayLabel[0][k].setText("");
		for (int k = 1; k <= maxDay; k++) {
			dayLabel[i][j].setText(Integer.toString(k));
			if (j == 6) {
				i++;
				j = 0;
			} else j++;
		}
		while (i < 6) {
			dayLabel[i][j].setText("");
			if (j == 6) {
				i++;
				j = 0;
			} else j++;
		}
		monthLabel.setText(date.getMonth() + "  " + date.getYear());
	}
	
	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}
	
	public LocalDate getSelectDay() {
		return selectDay;
	}

	public void calendarAdd(int delta) {
		date = date.plusMonths(delta);
		updateCalendar();
	}

	public void updateDate(LocalDate date){
		this.date = date;
		updateCalendar();
	}
}
