/**
 *	@(#)WeeklyView.java
 *
 *	@author Bingzhen Chen
 *	@version 1.00 2021/8/5
**/

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class WeeklyView extends JPanel implements CalendarView {
	private final JTextPane[] daysText = new JTextPane[7];
	private Controller controller;
	private LocalDate date = LocalDate.now();

	public WeeklyView() {
		super();
		setPreferredSize(new Dimension(430,300));
		setLayout(new GridLayout(7,1));
		add(new JLabel());
		setup();
	}

	private void setup() {
		super.removeAll();

        JPanel panels[] = new JPanel[7];
		String strs[] = {"Su","Mo","Tu","We","Th","Fr","Sa"};

		for (int i = 0; i < 7; i++){
			panels[i] = new JPanel();
			panels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel label = new JLabel(strs[i]);
			label.setPreferredSize(new Dimension(20,40));
			panels[i].add(label);

			super.add(panels[i]);
		}

        for (int i = 0; i < 7; i++) {
			daysText[i] = new JTextPane();
			daysText[i].setPreferredSize(new Dimension(380, 40));

			panels[i].add(new JScrollPane(daysText[i],
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        }
	}

	public String getLabel() {
		return "Week";
	}

	public JComponent getView() {
		return this;
	}

	public void attach(Controller controller) {
		this.controller = controller;
	}

	public void setDate(LocalDate date) {
		this.date = date;
		refreshData();
	}

	public void refreshData() {
        LocalDate weekday = this.date;
		weekday = weekday.minusDays(weekday.getDayOfWeek().getValue() % 7);
		
        for (int i = 0; i < 7; i++) {
            Event[] events = this.controller.getDayEvents(weekday);
			String text = "";
			for (int j = 0; j < events.length; j++) {
				text += events[j].getName()+"\n";
			}
			while (text.endsWith("\n")) {
				text = text.substring(0, text.length()-1);
			}
			daysText[i].setText(text);
			daysText[i].setCaretPosition(0);
			weekday = weekday.plusDays(1);
		}
    }
}
