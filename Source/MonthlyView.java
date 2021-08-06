/**
 *	@(#)MonthlyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/8/5
**/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class MonthlyView extends JPanel implements CalendarView {
	private Controller controller;
    private LocalDate date;

	public MonthlyView() {
		super();
		setPreferredSize(new Dimension(FrameView.PREFERRED_WIDTH, FrameView.PREFERRED_HEIGHT));
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
            Event[] events = controller.getDayEvents(day);
            day = day.plusDays(1);
            JLabel label= new JLabel(Integer.toString(dayCounter), SwingConstants.CENTER);
            label.setVerticalAlignment(JLabel.TOP);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setOpaque(true);
            label.setBorder(new BevelBorder(BevelBorder.RAISED));

            if (events != null && events.length > 0) {
                label.setText("<html><div style=\"text-align:center;\">"+dayCounter +"<br>"+events.length+" events</div></html>");
                label.setBackground(Color.LIGHT_GRAY);
            } else {
                label.setText("<html><div style=\"text-align:center;\">"+dayCounter +"<br>&nbsp;</div></html>");
                label.setBackground(Color.WHITE);
            }

            panel.add(label);
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
