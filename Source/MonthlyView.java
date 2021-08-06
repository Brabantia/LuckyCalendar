/**
 *	@(#)MonthlyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/8/5
**/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonthlyView extends JPanel implements CalendarView {
	private Controller controller;
    private LocalDate date;

	public MonthlyView() {
		super();
		setPreferredSize(new Dimension(430, 300));
        setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	}

    private JPanel getChildPanel() {
        JPanel panel= new JPanel();
        LocalDate day = LocalDate.of(this.date.getYear(), this.date.getMonth(), 1);
        int maxDay = this.date.lengthOfMonth();

        panel.setLayout(new GridLayout(7, 7));
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
            button.setEnabled(false);

            if (controller.getDayEvents(day) != null && controller.getDayEvents(day).length>0 ) {
                button.setBackground(Color.gray);
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

    public void refreshData() {
        super.removeAll();
        super.setLayout(new BorderLayout());
        super.add(new JLabel(this.date.getMonth() + " " + this.date.getYear(), SwingConstants.CENTER), BorderLayout.PAGE_START);
        super.add(getChildPanel(), BorderLayout.CENTER);
        super.revalidate();
    }

    public void setDate(LocalDate date) {
        this.date = date;
        refreshData();
    }

    public void attach(Controller controller) {
        this.controller = controller;
    }

	public String getLabel() {
		return "Month";
	}

	public JComponent getView() {
		return this;
	}
}
